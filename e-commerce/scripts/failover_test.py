#!/usr/bin/env python3
"""
MySQL Replication Failover Test

Verifies that:
  1. Data written to the primary is immediately replicated to the replica
  2. When the primary container is stopped the replica remains readable
  3. Zero data loss occurs during primary failure

Usage:
    MYSQL_ROOT_PASSWORD=changeme123 python3 scripts/failover_test.py

The script requires Docker to be running with the ecommerce compose stack
started via:  docker compose up -d
"""

import os
import subprocess
import sys
import time

PRIMARY   = "ecommerce-mysql-primary"
REPLICA   = "ecommerce-mysql-replica"
DB        = "ecommerce"
TABLE     = "replication_test"
ROW_COUNT = 100
PASSWORD  = os.environ.get("MYSQL_ROOT_PASSWORD", "changeme123")

# ── helpers ──────────────────────────────────────────────────────────────────

def docker(*args):
    return subprocess.run(["docker", *args], capture_output=True, text=True)

def mysql(container, sql, database=DB):
    result = docker(
        "exec", container,
        "mysql", "-u", "root", f"-p{PASSWORD}", "-se", sql, database
    )
    return result.stdout.strip(), result.returncode

def container_running(name):
    r = docker("inspect", "-f", "{{.State.Running}}", name)
    return r.stdout.strip() == "true"

def count_rows(container):
    out, rc = mysql(container, f"SELECT COUNT(*) FROM {TABLE};")
    if rc != 0:
        return None
    try:
        return int(out.splitlines()[-1])
    except (ValueError, IndexError):
        return None

def replication_lag(replica):
    out, _ = mysql(replica, "SHOW REPLICA STATUS\\G", "")
    for line in out.splitlines():
        if "Seconds_Behind_Source" in line:
            val = line.split(":")[-1].strip()
            return int(val) if val.isdigit() else None
    return None

def separator():
    print("=" * 56)

def step(n, total, label):
    print(f"\n[{n}/{total}] {label}")

# ── pre-flight ────────────────────────────────────────────────────────────────

def preflight():
    for name in (PRIMARY, REPLICA):
        if not container_running(name):
            print(f"  ERROR: container '{name}' is not running.")
            print("  Start the stack first:  docker compose up -d")
            sys.exit(1)

# ── main test ─────────────────────────────────────────────────────────────────

def main():
    separator()
    print("  MySQL Replication Failover Test")
    separator()

    preflight()

    # ── 1. Replication status ────────────────────────────────────────────────
    step(1, 5, "Replication status")
    print(f"  PRIMARY   {PRIMARY}  (localhost:3306)")
    print(f"  REPLICA   {REPLICA}  (localhost:3307)")

    lag = replication_lag(REPLICA)
    lag_str = f"{lag}s" if lag is not None else "unknown"
    print(f"  Replica lag: {lag_str}")

    # ── 2. Write rows to primary ─────────────────────────────────────────────
    step(2, 5, f"Writing {ROW_COUNT} rows to primary")
    mysql(PRIMARY, f"TRUNCATE TABLE {TABLE};")
    values = ", ".join(f"('row_{i}')" for i in range(1, ROW_COUNT + 1))
    t_write_start = time.monotonic()
    _, rc = mysql(PRIMARY, f"INSERT INTO {TABLE} (value) VALUES {values};")
    write_ms = int((time.monotonic() - t_write_start) * 1000)

    if rc != 0:
        print("  ERROR: insert failed")
        sys.exit(1)

    print(f"  Inserted  : {ROW_COUNT} rows")
    print(f"  Write time: {write_ms} ms")

    # wait for replication to catch up (usually sub-millisecond)
    time.sleep(0.5)

    # ── 3. Pre-failover read check on replica ────────────────────────────────
    step(3, 5, "Pre-failover read check (replica)")
    before = count_rows(REPLICA)
    print(f"  Rows readable on replica before failover: {before}")

    # ── 4. Stop primary ──────────────────────────────────────────────────────
    step(4, 5, f"Stopping primary: {PRIMARY}")
    t_stop = time.monotonic()
    docker("stop", PRIMARY)
    stop_ms = int((time.monotonic() - t_stop) * 1000)
    print(f"  {PRIMARY} stopped  ({stop_ms} ms)")

    # ── 5. Post-failover read from replica ───────────────────────────────────
    step(5, 5, "Post-failover read check (replica)")
    t_read = time.monotonic()
    after = count_rows(REPLICA)
    read_ms = int((time.monotonic() - t_read) * 1000)

    print(f"  Rows readable on replica after failover : {after}")
    print(f"  Read time after failover                : {read_ms} ms")
    print(f"\n  Restarting {PRIMARY} (rejoins as replication primary)...")
    docker("start", PRIMARY)
    print(f"  {PRIMARY} restarted")

    # ── results ──────────────────────────────────────────────────────────────
    lost       = (before or 0) - (after or 0)
    passed     = (before == ROW_COUNT) and (after == ROW_COUNT) and (lost == 0)
    result_str = "PASS — zero data loss, replica survived primary failure" if passed \
                 else f"FAIL — data loss detected: {lost} rows missing"

    print()
    separator()
    print("  RESULTS")
    separator()
    print(f"  Primary container  : {PRIMARY} (localhost:3306)")
    print(f"  Replica container  : {REPLICA} (localhost:3307)")
    print(f"  Rows written       : {ROW_COUNT}")
    print(f"  Write time         : {write_ms} ms")
    print(f"  Rows before failover: {before}")
    print(f"  Rows after failover : {after}")
    print(f"  Data loss          : {lost} rows")
    print(f"  Total test duration: {int((time.monotonic() - t_write_start) * 1000)} ms")
    print()
    print(f"  RESULT: {result_str}")
    print()
    separator()

    sys.exit(0 if passed else 1)


if __name__ == "__main__":
    main()

#!/bin/bash
# Runs once (as the mysql-setup container) after both mysql-primary and
# mysql-replica are healthy.  Configures GTID-based replication so the
# replica streams every change from the primary automatically.
set -euo pipefail

PRIMARY_HOST="mysql-primary"
REPLICA_HOST="mysql-replica"
ROOT_PASSWORD="${MYSQL_ROOT_PASSWORD}"

mysql_exec() {
    mysql -h "$1" -u root -p"${ROOT_PASSWORD}" -e "$2" 2>/dev/null
}

echo "[setup] Waiting for primary..."
until mysql_exec "$PRIMARY_HOST" "SELECT 1" &>/dev/null; do sleep 2; done

echo "[setup] Waiting for replica..."
until mysql_exec "$REPLICA_HOST" "SELECT 1" &>/dev/null; do sleep 2; done

echo "[setup] Configuring replication on replica..."
mysql_exec "$REPLICA_HOST" "
STOP REPLICA;
CHANGE REPLICATION SOURCE TO
    SOURCE_HOST    = 'mysql-primary',
    SOURCE_PORT    = 3306,
    SOURCE_USER    = 'replicator',
    SOURCE_PASSWORD= 'repl_secret_123',
    SOURCE_AUTO_POSITION = 1;
START REPLICA;
"

echo "[setup] Verifying replica threads..."
mysql_exec "$REPLICA_HOST" "SHOW REPLICA STATUS\G" \
    | grep -E "Replica_IO_Running|Replica_SQL_Running"

echo "[setup] Replication configured successfully."

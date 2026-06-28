-- Create a dedicated replication user the replica will authenticate as
CREATE USER IF NOT EXISTS 'replicator'@'%' IDENTIFIED WITH mysql_native_password BY 'repl_secret_123';
GRANT REPLICATION SLAVE ON *.* TO 'replicator'@'%';

-- Scratch table used by the failover test (not a JPA entity; Hibernate ignores unknown tables)
CREATE TABLE IF NOT EXISTS replication_test (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    value      VARCHAR(255) NOT NULL,
    created_at TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);

FLUSH PRIVILEGES;

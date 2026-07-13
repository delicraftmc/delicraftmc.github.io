CREATE TABLE IF NOT EXISTS players (
    uuid VARCHAR(36) PRIMARY KEY,
    username VARCHAR(16) NOT NULL,
    first_joined BIGINT NOT NULL,
    last_seen BIGINT NOT NULL,
    playtime BIGINT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS economy_accounts (
    uuid VARCHAR(36) PRIMARY KEY,
    balance BIGINT DEFAULT 0,
    created_at BIGINT NOT NULL,
    FOREIGN KEY (uuid) REFERENCES players(uuid)
);

CREATE INDEX IF NOT EXISTS idx_players_username ON players(username);

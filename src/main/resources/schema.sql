DROP TABLE IF EXISTS ws.users;
DROP SEQUENCE IF EXISTS ws.global_seq;

CREATE SEQUENCE ws.global_seq START WITH 1000;

CREATE TABLE ws.users
(
    id        INTEGER PRIMARY KEY DEFAULT nextval('ws.global_seq'),
    tg_id     INTEGER                          NOT NULL,
    name      VARCHAR                          NOT NULL,
    last_name VARCHAR,
    username  VARCHAR,
    enabled   BOOL                DEFAULT TRUE NOT NULL
);
CREATE UNIQUE INDEX users_tg_id_idx ON ws.users(tg_id);
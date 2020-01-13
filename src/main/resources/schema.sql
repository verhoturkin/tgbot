DROP TABLE IF EXISTS shifts;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 1000;

CREATE TABLE users
(
    id        INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    tg_id     INTEGER NOT NULL,
    name      VARCHAR NOT NULL,
    last_name VARCHAR,
    username  VARCHAR,
    enabled   BOOL                DEFAULT TRUE NOT NULL,
    state     INTEGER             DEFAULT 1    NOT NULL
);
CREATE UNIQUE INDEX users_tg_id_idx ON users (tg_id);

CREATE TABLE shifts
(
    id      INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    user_id INTEGER                           NOT NULL,
    date    DATE                DEFAULT now() NOT NULL,
    start   TIME                DEFAULT now() NOT NULL,
    stop    TIME                              NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
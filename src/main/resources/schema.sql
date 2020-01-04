DROP TABLE IF EXISTS ws.shifts;
DROP TABLE IF EXISTS ws.users;
DROP SEQUENCE IF EXISTS ws.global_seq;

CREATE SEQUENCE ws.global_seq START WITH 1000;

CREATE TABLE ws.users
(
    id        INTEGER PRIMARY KEY DEFAULT nextval('ws.global_seq'),
    tg_id     INTEGER NOT NULL,
    name      VARCHAR NOT NULL,
    last_name VARCHAR,
    username  VARCHAR,
    enabled   BOOL                DEFAULT TRUE NOT NULL,
    state     INTEGER NOT NULL    DEFAULT 1
);
CREATE UNIQUE INDEX users_tg_id_idx ON ws.users (tg_id);

CREATE TABLE ws.shifts
(
    id      INTEGER PRIMARY KEY DEFAULT nextval('ws.global_seq'),
    user_id INTEGER                           NOT NULL,
    date    DATE                DEFAULT now() NOT NULL,
    start   TIME                DEFAULT now() NOT NULL,
    stop    TIME                              NOT NULL,
    FOREIGN KEY (user_id) REFERENCES ws.users (id) ON DELETE CASCADE
)
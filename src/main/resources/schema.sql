DROP TABLE IF EXISTS votes;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS menus;
DROP TABLE IF EXISTS restaurants;

DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE GLOBAL_SEQ MINVALUE 100000;

CREATE TABLE users
(
    id               INTEGER DEFAULT GLOBAL_SEQ.nextval PRIMARY KEY,
    name             VARCHAR                NOT NULL,
    email            VARCHAR                NOT NULL,
    password         VARCHAR                NOT NULL,
    enabled          BOOLEAN DEFAULT TRUE   NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx
    ON users (email);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR(255),
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE restaurants
(
    id              INTEGER DEFAULT GLOBAL_SEQ.nextval PRIMARY KEY ,
    name            VARCHAR            NOT NULL
);
create UNIQUE INDEX restaurants_unique_name_idx
    ON restaurants (name);

CREATE TABLE menus
(
    id              INTEGER DEFAULT GLOBAL_SEQ.nextval PRIMARY KEY,
    restaurant_id   INTEGER NOT NULL,
    menu_date       DATE NOT NULL,
    dish            VARCHAR NOT NULL,
    price           INTEGER DEFAULT 0,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);
create UNIQUE INDEX menu_unique_idx
    ON menus (menu_date, restaurant_id, dish);

CREATE TABLE votes
(
    id              INTEGER DEFAULT GLOBAL_SEQ.nextval PRIMARY KEY,
    restaurant_id   INTEGER NOT NULL,
    user_id         INTEGER NOT NULL,
    vote_date       DATE NOT NULL default now(),
    voted_at        DATETIME NOT NULL default now(),
    constraint user_votes_unique_idx unique (user_id, restaurant_id, vote_date),
    foreign key (user_id) references users (id) on delete cascade,
    foreign key (restaurant_id) references restaurants (id) on delete cascade
);
create UNIQUE INDEX votes_unique_idx
    ON votes (vote_date, restaurant_id, user_id);

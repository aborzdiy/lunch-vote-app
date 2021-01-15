DELETE FROM USER_ROLES;
DELETE FROM USERS;
DELETE FROM MENUS;
DELETE FROM RESTAURANTS;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO USERS (NAME, EMAIL, PASSWORD, ENABLED)
VALUES ('User', 'user@yandex.ru', '{noop}password', true),    -- 100000
       ('Admin', 'admin@gmail.com', '{noop}admin', true);     -- 100001

INSERT INTO USER_ROLES (ROLE, USER_ID)
VALUES ('USER', 100000),
       ('ADMIN', 100001),
       ('USER', 100001);

INSERT INTO RESTAURANTS(NAME)
VALUES ( 'Гуси-лебеди' ),                               -- 100002
       ( 'Бургерная' ),                                 -- 100003
       ( 'МакДональдс' ),                               -- 100004
       ( 'KFC' ),                                       -- 100005
       ( 'Мафия' );                                     -- 100006

INSERT INTO MENUS(MENU_DATE, RESTAURANT_ID, DISH, PRICE)
VALUES ( now(),         100002, 'Борщ',     100 ),      -- 100007
       ( '2020-01-14',  100002, 'Борщ',     90 ),       -- 100008
       ( now(),         100003, 'Бургер',   300 ),      -- 100009
       ( '2020-01-14',  100003, 'Бургер',   250 ),      -- 100010
       ( now(),         100004, 'Хот дог',  200 ),      -- 100010
       ( now(),         100005, 'Оливье',   250 ),      -- 100012
       ( '2020-01-14',  100006, 'Хот дог',  100 );      -- 100011

insert into votes (USER_ID, RESTAURANT_ID, VOTE_DATE)
values ('100000', '100002', now()),
       ('100001', '100002', now()),
       ('100000', '100002', '2020-01-14'),
       ('100000', '100003', '2020-01-13'),
       ('100001', '100004', '2020-01-14'),
       ('100001', '100005', now());
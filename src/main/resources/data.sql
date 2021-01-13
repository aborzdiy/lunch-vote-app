DELETE FROM USER_ROLES;
DELETE FROM USERS;
DELETE FROM MENUS;
DELETE FROM RESTAURANTS;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO USERS (NAME, EMAIL, PASSWORD)
VALUES ('User', 'user@yandex.ru', '{noop}password'),    -- 100000
       ('Admin', 'admin@gmail.com', '{noop}admin');     -- 100001

INSERT INTO USER_ROLES (ROLE, USER_ID)
VALUES ('USER', 100000),
       ('ADMIN', 100001),
       ('USER', 100001);

INSERT INTO RESTAURANTS(NAME)
VALUES ( 'Гуси-лебеди' ),
       ( 'Украинское бистро' ),
       ( 'МакДональдс' ),
       ( 'KFC' ),
       ( 'Мафия' );

INSERT INTO MENUS(MENU_DATE, RESTAURANT_ID, DISH, PRICE)
VALUES ( now(),         100002, 'Борщ',     100 ),
       ( '2020-01-04',  100002, 'Борщ',     90 ),
       ( now(),         100003, 'Борщ',     105 ),
       ( now(),         100004, 'Хот дог',  200 ),
       ( '2020-01-04',  100006, 'Хот дог',  100 ),
       ( now(),         100005, 'Оливье',   250 );

## lunch-vote-app
Graduation project Topjava
Author - [Borzdyi Aleksandr](https://github.com/aborzdiy)

Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) **without frontend**.

##Task:
Build a voting system for deciding where to have lunch.

* 2 types of users: admin and regular users
* Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
* Menu changes each day (admins do the updates)
* Users can vote on which restaurant they want to have lunch at
* Only one vote counted per user
* If user votes again the same day:
    - If it is before 11:00 we asume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides new menu each day.

As a result, provide a link to github repository. It should contain the code, README.md with API documentation and couple curl commands to test it.

### Used tools and technologies
* Maven
* SpringBoot
* Spring Fata JPA
* HSQLDB
* Hibernate
* Java 8

### Application description
A voting system for deciding where to have lunch with REST API.\
Admins manage restaurants and upload its' menus daily. Users vote for a restaurant to have lunch at until 11:00.\
Voting results can be viewed after the voting process is finished. Historical results can be requested on date.
* There are 2 user roles: admins and regular users. Unauthorized access is prohibited.
* Admin section is accessible by `/rest/admin` path and user section under `/rest/` path.
* The following resources are accessible:
    - restaurants;
    - restaurant menu;
    - users;
    - user votes

* Admins have the following functions:
  - viewing, adding, updating, enabling/disabling and deleting users.
  - viewing, adding, updating, deleting restaurants.
  - viewing, adding, updating, deleting restaraunt's menu.
  - view and modify any votes.

* Users have the following abilities:
  - viewing restaurants and it's menu.
  - voting for a restaurant before 11:00. If vote is posted repeatedly previous vote is replaced.
  - viewing own votes.
    
Time is defined by default server system timezone.

## cURL examples:
> For windows use `Git Bash`

**Admin**
#### GET all restaurants:
`curl -s http://localhost:8080/graduation/rest/admin/restaurants --user admin@gmail.com:admin`

#### GET restaurant with id 100003:
`curl -s http://localhost:8080/graduation/rest/admin/restaurants/100003 --user admin@gmail.com:admin`

#### DELETE restaurant with id 100003:
`curl -s -X DELETE http://localhost:8080/graduation/rest/admin/restaurants/100003 --user admin@gmail.com:admin`

#### CREATE restaurant:
`curl -s -X POST -d '{"name":"Gerkules"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/graduation/rest/admin/restaurants --user admin@gmail.com:admin`

#### UPDATE restaurant:
`curl -s -X PUT -d '{"id": 100004, "name": "Zevs"}' -H 'Content-Type: application/json' http://localhost:8080/graduation/rest/admin/restaurants/100004 --user admin@gmail.com:admin`

#### GET current day menu at the restaurant (with id 100002):
`curl -s http://localhost:8080/graduation/rest/admin/restaurants/100002/menu --user admin@gmail.com:admin`

#### GET a historical menu at the restaurant with id 100002 - date 2020-01-14:
`curl -s http://localhost:8080/graduation/rest/admin/restaurants/100002/menu?date=2020-01-14 --user admin@gmail.com:admin`

#### DELETE menu with id 100007 at the restaurant with id 100002:
`curl -s -X DELETE http://localhost:8080/graduation/rest/admin/restaurants/100002/menu/100007 --user admin@gmail.com:admin`

#### CREATE menu at the restaurant with id 100002:
`curl -s -X POST -d '{"menuDate":"2020-01-16", "dish": "Octopus", "price": 800}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/graduation/rest/admin/restaurants/100002/menu --user admin@gmail.com:admin`

#### UPDATE menu with id 100007 at the restaurant with id 100002:
`curl -s -X PUT -d '{"id": 100007, "menuDate":"2020-01-16", "dish":"Fish", "price": 555}' -H 'Content-Type: application/json' http://localhost:8080/graduation/rest/admin/restaurants/100002/menu/100007 --user admin@gmail.com:admin`

#### GET all votes at current date - restaurant with id 100002:
`curl -s http://localhost:8080/graduation/rest/admin/restaurants/100002/vote --user admin@gmail.com:admin`

#### GET all historical votes at date - restaurant with id 100002:
`curl -s http://localhost:8080/graduation/rest/admin/restaurants/100002/vote?date=2021-01-16 --user admin@gmail.com:admin`

#### DELETE vote with id 100014 at the restaurant with id 100002:
`curl -s -X DELETE http://localhost:8080/graduation/rest/admin/restaurants/100002/vote/100014 --user admin@gmail.com:admin`

#### CREATE vote at the restaurant with id 100002 (user with id 100000):
`curl -s -X POST -d '{"vote_date":"2021-01-17", "user_id": "100000"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/graduation/rest/admin/restaurants/100002/vote/ --user admin@gmail.com:admin`

**User**

#### GET all restaurants:
`curl -s http://localhost:8080/graduation/rest/restaurants --user user@yandex.ru:password`

#### GET current day menu at the restaurant (with id 100002):
`curl -s http://localhost:8080/graduation/rest/restaurants/100002/menu --user user@yandex.ru:password`

#### CREATE vote:
`curl -s -X POST -d '{"vote_date":"2021-01-17", "user_id": "100000"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/graduation/rest/restaurants/100002/vote/ --user user@yandex.ru:password`

#### CREATE vote and update it:
`curl -s -X POST -d '{"voted_at":"2021-01-15T23:38:02"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/graduation/rest/restaurants/100003/vote/ --user user@yandex.ru:password`

`curl -s -X POST -d '{"voted_at":"2021-01-15T23:38:02"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/graduation/rest/restaurants/100004/vote/ --user user@yandex.ru:password`

### Ideas for futher development
 * Add new role - restaurant manager. Users with this role can apply changes to menu at the restaurant (which is managed)
 * Add new catalog - Dish.
 * Restrict changes in restaurant menu, if a restaurant have at least one vote at this day.
 * Hide restaurants, that have not any items in today menu.
 * Add dish rate, when user vote for a restaurant (which dishes are more attracted him)
 * Add restaurant's guestbook, where users can write something about their's visits to cafe
 * User can be able to find restaurant, with some assortiment (add search by dish)
 * Search dishes in today's menu - and sort it by price (for example)
 * Add contact phone to restaurant/user
 * It would be great, if user can reserve table
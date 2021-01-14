package ru.borzdiy.lunchvote;

import ru.borzdiy.lunchvote.model.Restaurant;

import java.time.Month;

import static java.time.LocalDateTime.of;

public class RestaurantTestData {
    public static final TestMatcher<Restaurant> RESTAURANT_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Restaurant.class, "menu");

    public static final int RESTAURANT_1_ID = 100002;

    public static final Restaurant RESTAURANT1 = new Restaurant(RESTAURANT_1_ID, "Гуси-лебеди");

    public static Restaurant getNew() {
        return new Restaurant(null, "Бургер кинг");
    }
}

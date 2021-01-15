package ru.borzdiy.lunchvote;

import ru.borzdiy.lunchvote.model.Restaurant;

public class RestaurantTestData {
    public static final TestMatcher<Restaurant> RESTAURANT_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Restaurant.class, "menu");

    public static final int RESTAURANT_1_ID = 100002;
    public static final int RESTAURANT_2_ID = 100003;

    public static final Restaurant RESTAURANT1 = new Restaurant(RESTAURANT_1_ID, "Гуси-лебеди", null);
    public static final Restaurant RESTAURANT2 = new Restaurant(RESTAURANT_2_ID, "Бургерная", null);

    public static Restaurant getNew() {
        return new Restaurant(null, "Бургер кинг", null);
    }

    public static Restaurant getUpdated() {
        Restaurant updated = new Restaurant(RESTAURANT1);
        updated.setName("UpdatedName");
        return updated;
    }
}

package ru.borzdiy.lunchvote.util;

import ru.borzdiy.lunchvote.model.Restaurant;
import ru.borzdiy.lunchvote.to.RestaurantTo;

public class RestarauntUtil {

    public static Restaurant createNewFromTo(RestaurantTo userTo) {
        return new Restaurant(null, userTo.getName(), userTo.getMenu());
    }

    public static RestaurantTo asTo(Restaurant restaurant) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), restaurant.getMenu());
    }

    public static Restaurant updateFromTo(Restaurant restaurant, RestaurantTo restaurantTo) {
        restaurant.setName(restaurantTo.getName() == null ? restaurant.getName() : restaurantTo.getName());
        restaurant.setMenu(restaurantTo.getMenu() == null ? restaurant.getMenu() : restaurantTo.getMenu());
        return restaurant;
    }

}
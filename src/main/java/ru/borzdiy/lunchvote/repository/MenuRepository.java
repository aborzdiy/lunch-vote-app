package ru.borzdiy.lunchvote.repository;

import ru.borzdiy.lunchvote.model.Menu;

import java.util.List;

public interface MenuRepository {

    // null if not found, when updated
    Menu save(Menu menu);

    // false if not found
    boolean delete(int restaurantId, int id);

    // null if not found
    Menu get(int id);

    // null if not found
    Menu getWithRestaurant(int id);

    List<Menu> getAll();

    List<Menu> getRestaurantMenu(int restaurantId);

}

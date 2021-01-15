package ru.borzdiy.lunchvote.repository;

import ru.borzdiy.lunchvote.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

public interface RestaurantRepository {

    // null if not found, when updated
    Restaurant save(Restaurant restaurant);

    // false if not found
    boolean delete(int id);

    // null if not found
    Restaurant get(int id);

    Restaurant getByName(String name);

    Restaurant getWithMenu(int id, LocalDate localDate);

    Restaurant getOne(int id);

    List<Restaurant> getAll();

}

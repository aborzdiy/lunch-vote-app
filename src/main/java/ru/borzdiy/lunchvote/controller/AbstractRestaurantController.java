package ru.borzdiy.lunchvote.controller;

import org.springframework.beans.factory.annotation.Autowired;
import ru.borzdiy.lunchvote.model.Restaurant;
import ru.borzdiy.lunchvote.service.RestaurantService;

import java.util.List;

public class AbstractRestaurantController extends AbstractController {

    @Autowired
    RestaurantService restaurantService;

    public List<Restaurant> getAll() {
        log.info("get all");
        return restaurantService.getAll();
    }

    public Restaurant get(int id) {
        log.info("get with id={}", id);
        return restaurantService.get(id);
    }

}

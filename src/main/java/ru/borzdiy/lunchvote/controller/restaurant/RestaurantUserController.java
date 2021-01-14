package ru.borzdiy.lunchvote.controller.restaurant;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.borzdiy.lunchvote.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

import static ru.borzdiy.lunchvote.controller.restaurant.RestaurantUserController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantUserController extends AbstractRestaurantController {
    public static final String REST_URL = "/rest/restaurants";

    @GetMapping
    public List<Restaurant> getAll() {
        return super.getAll();
    }

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        return super.get(id);
    }

    @GetMapping("/{id}/menu")
    public Restaurant getWithMenu(@PathVariable int id, @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Nullable LocalDate localDate) {
        return super.getWithMenu(id, localDate);
    }
}

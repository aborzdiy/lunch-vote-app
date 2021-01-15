package ru.borzdiy.lunchvote.controller.restaurant;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.borzdiy.lunchvote.model.Restaurant;
import ru.borzdiy.lunchvote.to.MenuTo;
import ru.borzdiy.lunchvote.to.RestaurantTo;

import java.time.LocalDate;
import java.util.List;

import static ru.borzdiy.lunchvote.controller.restaurant.RestaurantUserController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantUserController extends AbstractRestaurantController {
    public static final String ID_PARAM_NAME = "id";
    public static final String REST_URL = "/rest/restaurants";
    public static final String REST_WITH_ID = "/{" + ID_PARAM_NAME + "}";
    public static final String REST_WITH_ID_MENU = REST_WITH_ID + "/menu";

    @GetMapping
    public List<RestaurantTo> getAll() {
        return super.getAll();
    }

    @GetMapping(REST_WITH_ID)
    public RestaurantTo get(@PathVariable int id) {
        return super.get(id);
    }

    @GetMapping(REST_WITH_ID_MENU)
    public List<MenuTo> getRestaurantMenu(@PathVariable(ID_PARAM_NAME) int rid, @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Nullable LocalDate localDate) {
        return super.getRestaurantMenu(rid, localDate);
    }

}

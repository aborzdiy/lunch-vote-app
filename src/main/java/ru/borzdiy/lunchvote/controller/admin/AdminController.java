package ru.borzdiy.lunchvote.controller.admin;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.borzdiy.lunchvote.model.Menu;
import ru.borzdiy.lunchvote.model.Restaurant;
import ru.borzdiy.lunchvote.to.MenuTo;
import ru.borzdiy.lunchvote.to.RestaurantTo;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = AdminController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController extends AbstractAdminController {
    public static final String REST_URL = "/rest/admin";
    public static final String RESTAURANTS = "/restaurants";
    public static final String RESTAURANT_ID_PARAM_NAME = "rid";
    public static final String RESTAURANTS_WITH_ID = RESTAURANTS + "/{" + RESTAURANT_ID_PARAM_NAME + "}";
    public static final String RESTAURANTS_WITH_ID_MENU = RESTAURANTS_WITH_ID + "/menu";

    // WORK WITH RESTAURANTS

    @GetMapping(RESTAURANTS)
    public List<Restaurant> getAllRestaurants() {
        return super.getAllRestaurants();
    }

    @GetMapping(RESTAURANTS_WITH_ID)
    public RestaurantTo get(@PathVariable(RESTAURANT_ID_PARAM_NAME) int rid) {
        return super.getRestarauntTo(rid);
    }

    @PostMapping(value = RESTAURANTS, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createRestaurantWithLocation(@Validated @RequestBody Restaurant restaurant) {
        Restaurant created = super.createRestaurant(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(RESTAURANTS_WITH_ID)
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping(RESTAURANTS_WITH_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(RESTAURANT_ID_PARAM_NAME) int id) {
        super.deleteRestaurant(id);
    }

    @PutMapping(RESTAURANTS_WITH_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody RestaurantTo restaurantTo, @PathVariable(RESTAURANT_ID_PARAM_NAME) int id) throws BindException {
        super.updateRestaurant(restaurantTo, id);
    }

    // WORK WITH RESTAURANT MENU

    @GetMapping(RESTAURANTS_WITH_ID_MENU)
    public RestaurantTo getRestaurantWithMenu(@PathVariable(RESTAURANT_ID_PARAM_NAME) int rid, @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Nullable LocalDate localDate) {
        return super.getRestaurantWithMenu(rid, localDate);
    }

    @PostMapping(value = RESTAURANTS_WITH_ID_MENU, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createMenuWithLocation(@Validated @RequestBody Menu menu, @PathVariable(RESTAURANT_ID_PARAM_NAME) int rId) {
        Menu created = super.createMenu(menu, rId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(RESTAURANTS_WITH_ID_MENU + "/{mid}")
                .buildAndExpand(rId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping(RESTAURANTS_WITH_ID_MENU+"/{mid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(RESTAURANT_ID_PARAM_NAME) int rId, @PathVariable("mid") int mId) {
        super.deleteMenu(rId, mId);
    }

    @PutMapping(RESTAURANTS_WITH_ID_MENU+"/{mid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody MenuTo menuTo, @PathVariable(RESTAURANT_ID_PARAM_NAME) int rId, @PathVariable("mid") int mId) throws BindException {
        super.updateMenu(menuTo, rId, mId);
    }

}
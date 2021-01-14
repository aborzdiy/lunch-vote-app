package ru.borzdiy.lunchvote.controller.restaurant;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.borzdiy.lunchvote.model.Restaurant;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.borzdiy.lunchvote.controller.restaurant.RestaurantAdminController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantAdminController extends AbstractRestaurantController {
    public static final String REST_URL = "/rest/admin/restaurants";
    public static final String REST_URL_WITH_ID = "/rest/admin/restaurants/{id}";

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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@RequestBody Restaurant restaurant) {
        Restaurant created = super.create(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL_WITH_ID)
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Restaurant restaurant, @PathVariable int id) throws BindException {
        super.update(restaurant, id);
    }

}

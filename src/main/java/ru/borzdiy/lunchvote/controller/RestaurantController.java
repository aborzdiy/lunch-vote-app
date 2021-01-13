package ru.borzdiy.lunchvote.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindException;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.borzdiy.lunchvote.model.Restaurant;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.borzdiy.lunchvote.util.ValidationUtil.assureIdConsistent;
import static ru.borzdiy.lunchvote.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController extends AbstractRestaurantController {
    static final String ADMIN_REST_URL = "/rest/admin/restaurants";
    static final String ADMIN_REST_URL_WITH_ID = "/rest/admin/restaurants/{id}";
    static final String ADMIN_REST_URL_WITH_ID_MENU = "/rest/admin/restaurants/{id}/menu";

    static final String USER_REST_URL = "/rest/restaurants";
    static final String USER_REST_URL_WITH_ID = "/rest/restaurants/{id}";
    static final String USER_REST_URL_WITH_ID_MENU = "/rest/restaurants/{id}/menu";

    @GetMapping({ADMIN_REST_URL, USER_REST_URL})
    public List<Restaurant> getAll() {
        return super.getAll();
    }

    @GetMapping({ADMIN_REST_URL_WITH_ID, USER_REST_URL_WITH_ID})
    public Restaurant get(@PathVariable int id) {
        return super.get(id);
    }

    @GetMapping({ADMIN_REST_URL_WITH_ID_MENU, USER_REST_URL_WITH_ID_MENU})
    public Restaurant getWithMenu(@PathVariable int id, @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Nullable LocalDate localDate) {
        return super.getWithMenu(id, localDate);
    }

    @PostMapping(path = ADMIN_REST_URL, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@RequestBody Restaurant restaurant) {
        log.info("create {}", restaurant);
        checkNew(restaurant);
        Restaurant created = restaurantService.create(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(ADMIN_REST_URL_WITH_ID)
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping(ADMIN_REST_URL_WITH_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        restaurantService.delete(id);
    }

    @PutMapping(ADMIN_REST_URL_WITH_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Restaurant restaurant, @PathVariable int id) throws BindException {
        validateBeforeUpdate(restaurant, id);
        log.info("update {} with id={}", restaurant, id);
        restaurantService.update(restaurant);
    }

    protected void validateBeforeUpdate(Restaurant restaurant, int id) throws BindException {
        assureIdConsistent(restaurant, id);
        DataBinder binder = new DataBinder(restaurant);
//        binder.addValidators(emailValidator, validator);
//        binder.validate(View.Web.class);
        if (binder.getBindingResult().hasErrors()) {
            throw new BindException(binder.getBindingResult());
        }
    }

}

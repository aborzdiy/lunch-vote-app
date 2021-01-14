package ru.borzdiy.lunchvote.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.PathVariable;
import ru.borzdiy.lunchvote.model.Restaurant;
import ru.borzdiy.lunchvote.service.RestaurantService;

import java.time.LocalDate;
import java.util.List;

import static ru.borzdiy.lunchvote.util.ValidationUtil.assureIdConsistent;

public class AbstractRestaurantController extends AbstractController {

    @Autowired
    RestaurantService restaurantService;

    protected List<Restaurant> getAll() {
        log.info("get all");
        return restaurantService.getAll();
    }

    protected Restaurant get(int id) {
        log.info("get with id={}", id);
        return restaurantService.get(id);
    }

    protected Restaurant getWithMenu(int id, LocalDate localDate) {
        log.info("get with menu id={}, date={}", id, localDate);
        return restaurantService.getWithMenu(id, localDate);
    }

    protected void delete(@PathVariable int id) {
        log.info("delete {}", id);
        restaurantService.delete(id);
    }

    protected void update(Restaurant restaurant, int id) throws BindException{
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

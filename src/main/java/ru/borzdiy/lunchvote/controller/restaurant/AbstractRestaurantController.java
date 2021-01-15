package ru.borzdiy.lunchvote.controller.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindException;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PathVariable;
import ru.borzdiy.lunchvote.controller.AbstractController;
import ru.borzdiy.lunchvote.model.AbstractNamedEntity;
import ru.borzdiy.lunchvote.model.Restaurant;
import ru.borzdiy.lunchvote.service.RestaurantService;
import ru.borzdiy.lunchvote.to.RestaurantTo;
import ru.borzdiy.lunchvote.util.RestarauntUtil;
import ru.borzdiy.lunchvote.validators.UniqueRestorauntNameValidator;

import java.time.LocalDate;
import java.util.List;

import static ru.borzdiy.lunchvote.util.ValidationUtil.assureIdConsistent;
import static ru.borzdiy.lunchvote.util.ValidationUtil.checkNew;

public class AbstractRestaurantController extends AbstractController {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    private UniqueRestorauntNameValidator nameValidator;

    @Autowired
    @Qualifier("defaultValidator")
    private Validator validator;

    protected List<Restaurant> getAll() {
        log.info("get all");
        return restaurantService.getAll();
    }

    protected RestaurantTo get(int id) {
        log.info("get with id={}", id);
        return RestarauntUtil.asTo(restaurantService.get(id));
    }

    protected RestaurantTo getWithMenu(int id, LocalDate localDate) {
        log.info("get with menu id={}, date={}", id, localDate);
        return RestarauntUtil.asTo(restaurantService.getWithMenu(id, localDate));
    }

    public Restaurant create(RestaurantTo restaurantTo) {
        checkNew(restaurantTo);
        log.info("create from TO {}", restaurantTo);
        Restaurant restaurant = RestarauntUtil.createNewFromTo(restaurantTo);
        return restaurantService.create(restaurant);
    }

    protected void delete(@PathVariable int id) {
        log.info("delete {}", id);
        restaurantService.delete(id);
    }

    protected void update(RestaurantTo restaurantTo, int id) throws BindException {
        Restaurant restaurant = restaurantService.get(id);
        RestarauntUtil.updateFromTo(restaurant, restaurantTo);

        validateBeforeUpdate(restaurant, id);
        log.info("update {} with id={}", restaurantTo, id);
        restaurantService.update(restaurant);
    }

    protected void validateBeforeUpdate(AbstractNamedEntity restaurant, int id) throws BindException {
        assureIdConsistent(restaurant, id);
        DataBinder binder = new DataBinder(restaurant);
        binder.addValidators(nameValidator, validator);
        binder.validate();
        if (binder.getBindingResult().hasErrors()) {
            throw new BindException(binder.getBindingResult());
        }
    }
}

package ru.borzdiy.lunchvote.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindException;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PathVariable;
import ru.borzdiy.lunchvote.controller.AbstractBaseController;
import ru.borzdiy.lunchvote.model.AbstractNamedEntity;
import ru.borzdiy.lunchvote.model.Menu;
import ru.borzdiy.lunchvote.model.Restaurant;
import ru.borzdiy.lunchvote.service.MenuService;
import ru.borzdiy.lunchvote.service.RestaurantService;
import ru.borzdiy.lunchvote.to.MenuTo;
import ru.borzdiy.lunchvote.to.RestaurantTo;
import ru.borzdiy.lunchvote.util.MenuUtil;
import ru.borzdiy.lunchvote.util.RestarauntUtil;
import ru.borzdiy.lunchvote.validators.UniqueRestorauntNameValidator;

import java.time.LocalDate;
import java.util.List;

import static ru.borzdiy.lunchvote.util.ValidationUtil.assureIdConsistent;
import static ru.borzdiy.lunchvote.util.ValidationUtil.checkNew;

public class AbstractAdminController extends AbstractBaseController {
    @Autowired
    RestaurantService restaurantService;

    @Autowired
    MenuService menuService;

    @Autowired
    private UniqueRestorauntNameValidator restorauntNameValidator;

    @Autowired
    @Qualifier("defaultValidator")
    private Validator validator;

    protected List<Restaurant> getAllRestaurants() {
        log.info("get all restaurants");
        return restaurantService.getAll();
    }

    protected RestaurantTo getRestarauntTo(int id) {
        log.info("get restaurant with id={}", id);
        return RestarauntUtil.asTo(restaurantService.get(id));
    }

    protected RestaurantTo getRestaurantWithMenu(int id, LocalDate localDate) {
        log.info("get restaurant with menu id={}, date={}", id, localDate);
        return RestarauntUtil.asTo(restaurantService.getWithMenu(id, localDate));
    }

    public Restaurant createRestaurant(Restaurant restaurant) {
        checkNew(restaurant);
        log.info("create restaurant {}", restaurant);
        return restaurantService.create(restaurant);
    }

    protected void deleteRestaurant(@PathVariable int id) {
        log.info("delete restaurant id={}", id);
        restaurantService.delete(id);
    }

    protected void updateRestaurant(RestaurantTo restaurantTo, int id) throws BindException {
        Restaurant restaurant = RestarauntUtil.updateFromTo(restaurantService.get(id), restaurantTo);

        validateRestaurantBeforeUpdate(restaurant, id);
        log.info("update restaurant {} with id={}", restaurantTo, id);
        restaurantService.update(restaurant);
    }

    protected void validateRestaurantBeforeUpdate(Restaurant restaurant, int id) throws BindException {
        assureIdConsistent(restaurant, id);
        DataBinder binder = new DataBinder(restaurant);
        binder.addValidators(restorauntNameValidator, validator);
        binder.validate();
        if (binder.getBindingResult().hasErrors()) {
            throw new BindException(binder.getBindingResult());
        }
    }

    protected Menu createMenu(Menu menu, int restaurantId) {
        checkNew(menu);
        menu.setRestaurant(restaurantService.getOne(restaurantId));
        log.info("create menu {} in restaurant id={}", menu, restaurantId);
        return menuService.create(menu);
    }

    protected void deleteMenu(int restaurantId, int menuId) {
        log.info("delete menu id={}, restaurant id={}", menuId, restaurantId);
        menuService.delete(restaurantId, menuId);
    }

    protected void updateMenu(MenuTo menuTo, int restaurantId, int menuId) throws BindException {
        Menu menu = MenuUtil.updateFromTo(menuService.get(menuId), menuTo);
        menu.setRestaurant(restaurantService.getOne(restaurantId));
        validateMenuBeforeUpdate(menu, menuId);
        log.info("update menu {} with id={}", menu, menuId);
        menuService.update(menu, menuId);
    }

    private void validateMenuBeforeUpdate(Menu menu, int menuId) throws BindException {
        assureIdConsistent(menu, menuId);
        DataBinder binder = new DataBinder(menu);
        binder.addValidators(validator);
        binder.validate();
        if (binder.getBindingResult().hasErrors()) {
            throw new BindException(binder.getBindingResult());
        }
    }
}

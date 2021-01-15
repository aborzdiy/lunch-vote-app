package ru.borzdiy.lunchvote.controller.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindException;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PathVariable;
import ru.borzdiy.lunchvote.controller.AbstractController;
import ru.borzdiy.lunchvote.model.Menu;
import ru.borzdiy.lunchvote.model.Restaurant;
import ru.borzdiy.lunchvote.repository.RestaurantRepository;
import ru.borzdiy.lunchvote.service.MenuService;
import ru.borzdiy.lunchvote.service.RestaurantService;
import ru.borzdiy.lunchvote.to.MenuTo;
import ru.borzdiy.lunchvote.util.MenuUtil;

import java.util.List;

import static ru.borzdiy.lunchvote.util.ValidationUtil.assureIdConsistent;
import static ru.borzdiy.lunchvote.util.ValidationUtil.checkNew;

public abstract class AbstractMenuController extends AbstractController {

    @Autowired
    MenuService menuService;

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    @Qualifier("defaultValidator")
    private Validator validator;

    public List<Menu> getAll() {
        log.info("get all");
        return menuService.getAll();
    }

    public List<Menu> getRestaurantMenu(int restaurantId) {
        log.info("get restaurant menu, id {}", restaurantId);
        return menuService.getRestaurantMenu(restaurantId);
    }

    public MenuTo get(int id) {
        log.info("get with id={}", id);
        return MenuUtil.asTo(menuService.get(id));
    }

    public MenuTo getWithRestaurant(int id) {
        log.info("get with id={}", id);
        return MenuUtil.asTo(menuService.getWithRestaurant(id));
    }

    public Menu create(MenuTo menuTo) {
        checkNew(menuTo);
        log.info("create from TO {}", menuTo);
        Menu menu = MenuUtil.createNewFromTo(menuTo);
        return menuService.create(menu);
    }

    public Menu create(MenuTo menuTo, int restaurantId) {
        checkNew(menuTo);
        log.info("create from TO {}, restaurantId {}", menuTo, restaurantId);
        Menu menu = MenuUtil.createNewFromTo(menuTo);
        Restaurant restaraunt = restaurantService.getOne(restaurantId);
        menu.setRestaurant(restaraunt);
        return menuService.create(menu);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        menuService.delete(id);
    }

    public void update(MenuTo menuTo, int id) throws BindException {
        Menu menu = MenuUtil.updateFromTo(menuService.get(id), menuTo);

        validateBeforeUpdate(menu, id);
        log.info("update {}", menuTo);
        menuService.update(menu, id);
    }

    protected void validateBeforeUpdate(Menu menu, int id) throws BindException {
        assureIdConsistent(menu, id);
        DataBinder binder = new DataBinder(menu);
        binder.addValidators(validator);
        binder.validate();
        if (binder.getBindingResult().hasErrors()) {
            throw new BindException(binder.getBindingResult());
        }
    }
}

package ru.borzdiy.lunchvote.controller.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindException;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
import ru.borzdiy.lunchvote.controller.AbstractBaseController;
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
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.borzdiy.lunchvote.util.ValidationUtil.assureIdConsistent;

public class AbstractRestaurantController extends AbstractBaseController {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    MenuService menuService;

    @Autowired
    private UniqueRestorauntNameValidator nameValidator;

    @Autowired
    @Qualifier("defaultValidator")
    private Validator validator;

    protected List<RestaurantTo> getAll() {
        log.info("get all");
        return restaurantService.getAll()
                .stream()
                .map(RestarauntUtil::asTo)
                .collect(Collectors.toList());
    }

    protected RestaurantTo get(int id) {
        log.info("get with id={}", id);
        return RestarauntUtil.asTo(restaurantService.get(id));
    }

    protected List<MenuTo> getRestaurantMenu(int id, LocalDate localDate) {
        log.info("get restaurant menu id={}, date={}", id, localDate);
        return menuService.getRestaurantMenu(id, Objects.requireNonNullElse(localDate, LocalDate.now()))
                .stream()
                .map(MenuUtil::asTo)
                .collect(Collectors.toList());
    }

    protected void validateBeforeUpdate(Restaurant restaurant, int id) throws BindException {
        assureIdConsistent(restaurant, id);
        DataBinder binder = new DataBinder(restaurant);
        binder.addValidators(nameValidator, validator);
        binder.validate();
        if (binder.getBindingResult().hasErrors()) {
            throw new BindException(binder.getBindingResult());
        }
    }
}

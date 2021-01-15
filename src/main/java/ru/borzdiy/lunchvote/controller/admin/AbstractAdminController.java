package ru.borzdiy.lunchvote.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindException;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PathVariable;
import ru.borzdiy.lunchvote.controller.AbstractBaseController;
import ru.borzdiy.lunchvote.model.Menu;
import ru.borzdiy.lunchvote.model.Restaurant;
import ru.borzdiy.lunchvote.model.Vote;
import ru.borzdiy.lunchvote.repository.UserRepository;
import ru.borzdiy.lunchvote.service.MenuService;
import ru.borzdiy.lunchvote.service.RestaurantService;
import ru.borzdiy.lunchvote.service.VoteService;
import ru.borzdiy.lunchvote.to.MenuTo;
import ru.borzdiy.lunchvote.to.RestaurantTo;
import ru.borzdiy.lunchvote.to.VoteTo;
import ru.borzdiy.lunchvote.util.MenuUtil;
import ru.borzdiy.lunchvote.util.RestarauntUtil;
import ru.borzdiy.lunchvote.util.VoteUtil;
import ru.borzdiy.lunchvote.validators.UniqueRestorauntNameValidator;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.borzdiy.lunchvote.util.ValidationUtil.assureIdConsistent;
import static ru.borzdiy.lunchvote.util.ValidationUtil.checkNew;

public class AbstractAdminController extends AbstractBaseController {
    @Autowired
    RestaurantService restaurantService;

    @Autowired
    MenuService menuService;

    @Autowired
    VoteService voteService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private UniqueRestorauntNameValidator restorauntNameValidator;

    @Autowired
    @Qualifier("defaultValidator")
    private Validator validator;

    // work with restaurant
    protected List<RestaurantTo> getAllRestaurants() {
        log.info("get all restaurants");
        return restaurantService.getAll()
                .stream()
                .map(RestarauntUtil::asTo)
                .collect(Collectors.toList());
    }

    protected RestaurantTo getRestaraunt(int id) {
        log.info("get restaurant with id={}", id);
        return RestarauntUtil.asTo(restaurantService.get(id));
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

    // work with menu

    protected List<MenuTo> getRestaurantMenu(int id, LocalDate localDate) {
        log.info("get restaurant menu id={}, date={}", id, localDate);

        return menuService.getRestaurantMenu(id, Objects.requireNonNullElse(localDate, LocalDate.now()))
                .stream()
                .map(MenuUtil::asTo)
                .collect(Collectors.toList());
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

    // work with votes
    protected List<VoteTo> getRestaurantVotes(int rid, LocalDate localDate) {
        log.info("get restaurant votes id={}, date={}", rid, localDate);
        List<Vote> votes = voteService.getRestaurantVotes(rid, Objects.requireNonNullElse(localDate, LocalDate.now()));
        return votes.stream()
                .map(VoteUtil::asTo)
                .collect(Collectors.toList());
    }

    protected Vote createVote(Vote vote, int restaurantId, int userId) {
        checkNew(vote);
        vote.setVote_date(LocalDate.now());
        vote.setRestaurant(restaurantService.getOne(restaurantId));
        vote.setUser(userRepository.getOne(userId));
        log.info("create vote {} in restaurant id={}, user id={}", vote, restaurantId, userId);
        return voteService.create(vote);
    }

    protected void deleteVote(int vote_id) {
        log.info("delete vote id={}", vote_id);
        voteService.delete(vote_id);
    }

    protected void updateVote(VoteTo voteTo, int voteId) throws BindException {
        Vote vote = voteService.get(voteId);
        vote.setRestaurant(voteTo.getRestaurant_id() == null ? vote.getRestaurant(): restaurantService.getOne(voteTo.getRestaurant_id()));
        vote.setUser(voteTo.getUser_id() == null ? vote.getUser() : userRepository.getOne(voteTo.getUser_id()));
        validateVoteBeforeUpdate(vote, voteId);
        log.info("update vote {} with id={}", vote, voteId);
        voteService.update(vote, voteId);
    }

    private void validateVoteBeforeUpdate(Vote vote, int voteId) throws BindException {
        assureIdConsistent(vote, voteId);
        DataBinder binder = new DataBinder(vote);
        binder.addValidators(validator);
        binder.validate();
        if (binder.getBindingResult().hasErrors()) {
            throw new BindException(binder.getBindingResult());
        }
    }


}

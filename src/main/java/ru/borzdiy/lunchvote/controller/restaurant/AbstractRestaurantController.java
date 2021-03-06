package ru.borzdiy.lunchvote.controller.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.Validator;
import ru.borzdiy.lunchvote.controller.AbstractBaseController;
import ru.borzdiy.lunchvote.model.Restaurant;
import ru.borzdiy.lunchvote.model.User;
import ru.borzdiy.lunchvote.model.Vote;
import ru.borzdiy.lunchvote.service.MenuService;
import ru.borzdiy.lunchvote.service.RestaurantService;
import ru.borzdiy.lunchvote.service.UserService;
import ru.borzdiy.lunchvote.service.VoteService;
import ru.borzdiy.lunchvote.to.MenuTo;
import ru.borzdiy.lunchvote.to.RestaurantTo;
import ru.borzdiy.lunchvote.to.VoteTo;
import ru.borzdiy.lunchvote.util.MenuUtil;
import ru.borzdiy.lunchvote.util.RestarauntUtil;
import ru.borzdiy.lunchvote.util.exception.IllegalRequestDataException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AbstractRestaurantController extends AbstractBaseController {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    MenuService menuService;

    @Autowired
    UserService userService;

    @Autowired
    VoteService voteService;

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

    protected Vote processVote(VoteTo voteTo, int restaurantId, int userId) {
        LocalDate vote_date = LocalDate.now();
        Restaurant restaurant = restaurantService.getOne(restaurantId);
        Vote current = voteService.getUserVoteOnDate(userId, vote_date);
        if (current == null) {
            User user = userService.getOne(userId);
            current = new Vote(null, vote_date, user, restaurant);
            return voteService.create(current);
        }

        if (LocalDateTime.now().toLocalTime().isAfter(LocalTime.of(11, 0))) {
            throw new IllegalRequestDataException("You can not vote twice after 11:00 AM, try again tomorrow!");
        }

        current.setRestaurant(restaurant);
        current.setVotedAt(LocalDateTime.now());
        return voteService.save(current);
    }
}

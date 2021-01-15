package ru.borzdiy.lunchvote.controller.restaurant;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.borzdiy.lunchvote.AuthorizedUser;
import ru.borzdiy.lunchvote.model.User;
import ru.borzdiy.lunchvote.model.Vote;
import ru.borzdiy.lunchvote.to.MenuTo;
import ru.borzdiy.lunchvote.to.RestaurantTo;
import ru.borzdiy.lunchvote.to.VoteTo;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.borzdiy.lunchvote.controller.restaurant.RestaurantUserController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantUserController extends AbstractRestaurantController {
    public static final String RESTAURANT_ID_PARAM_NAME = "rid";
    public static final String REST_URL = "/rest/restaurants";
    public static final String REST_WITH_ID = "/{" + RESTAURANT_ID_PARAM_NAME + "}";
    public static final String REST_WITH_ID_MENU = REST_WITH_ID + "/menu";
    public static final String REST_WITH_ID_VOTE = REST_WITH_ID + "/vote";

    @GetMapping
    public List<RestaurantTo> getAll() {
        return super.getAll();
    }

    @GetMapping(REST_WITH_ID)
    public RestaurantTo get(@PathVariable(RESTAURANT_ID_PARAM_NAME) int id) {
        return super.get(id);
    }

    @GetMapping(REST_WITH_ID_MENU)
    public List<MenuTo> getRestaurantMenu(@PathVariable(RESTAURANT_ID_PARAM_NAME) int rid, @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Nullable LocalDate localDate) {
        return super.getRestaurantMenu(rid, localDate);
    }

    @PostMapping(value = REST_WITH_ID_VOTE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createVoteWithLocation(@Validated @RequestBody VoteTo vote, @PathVariable(RESTAURANT_ID_PARAM_NAME) int rId, @AuthenticationPrincipal AuthorizedUser user) {
        Vote created = super.processVote(vote, rId, user.getId());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_WITH_ID_VOTE + "/{vid}")
                .buildAndExpand(rId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}

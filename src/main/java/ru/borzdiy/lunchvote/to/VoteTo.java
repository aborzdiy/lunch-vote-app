package ru.borzdiy.lunchvote.to;

import ru.borzdiy.lunchvote.model.Restaurant;
import ru.borzdiy.lunchvote.model.User;

import java.time.LocalDate;

public class VoteTo extends BaseTo {
    private LocalDate vote_date;
    private Integer user_id;
    private Integer restaurant_id;

    public VoteTo() {
    }

    public VoteTo(Integer id, LocalDate vote_date, User user, Restaurant restaurant) {
        super(id);
        this.vote_date = vote_date;
        this.user_id = user == null ? null : user.getId();
        this.restaurant_id = restaurant == null ? null : restaurant.getId();
    }

    public LocalDate getVote_date() {
        return vote_date;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public Integer getRestaurant_id() {
        return restaurant_id;
    }

    @Override
    public String toString() {
        return "VoteTo{" +
                "id=" + id +
                ", vote_date=" + vote_date +
                ", user_id=" + user_id +
                ", restaurant_id=" + restaurant_id +
                '}';
    }
}

package ru.borzdiy.lunchvote.to;

import ru.borzdiy.lunchvote.model.Restaurant;
import ru.borzdiy.lunchvote.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class VoteTo extends BaseTo {
    private LocalDate voteDate;
    private Integer userId;
    private Integer restaurantId;
    private LocalDateTime votedAt;

    public VoteTo() {
    }

    public VoteTo(Integer id, LocalDate voteDate, User user, Restaurant restaurant, LocalDateTime votedAt) {
        super(id);
        this.voteDate = voteDate;
        this.userId = user == null ? null : user.getId();
        this.restaurantId = restaurant == null ? null : restaurant.getId();
        this.votedAt = votedAt;
    }

    public LocalDate getVoteDate() {
        return voteDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    @Override
    public String toString() {
        return "VoteTo{" +
                "id=" + id +
                ", voteDate=" + voteDate +
                ", userId=" + userId +
                ", restaurantId=" + restaurantId +
                '}';
    }
}

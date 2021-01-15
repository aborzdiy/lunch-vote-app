package ru.borzdiy.lunchvote.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "restaurant_id", "vote_date"}, name = "user_votes_unique_idx")})
public class Vote extends AbstractBaseEntity {

    @NotNull
    @Column(name = "vote_date")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    LocalDate vote_date = LocalDate.now();

    @NotNull
    @Column(name = "voted_at")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    LocalDateTime voted_at = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference("vote-user")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Restaurant.class)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference("vote-restaurant")
    private Restaurant restaurant;

    public Vote() {
    }

    public Vote(Vote vote) {
        this(vote.getId(), vote.getVote_date(), vote.getUser(), vote.restaurant);
    }

    public Vote(Integer id, LocalDate vote_date, User user, Restaurant restaurant) {
        super(id);
        this.vote_date = vote_date;
        this.user = user;
        this.restaurant = restaurant;
    }

    public LocalDate getVote_date() {
        return vote_date;
    }

    public void setVote_date(LocalDate vote_date) {
        this.vote_date = vote_date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDateTime getVoted_at() {
        return voted_at;
    }

    public void setVoted_at(LocalDateTime voted_at) {
        this.voted_at = voted_at;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", vote_date=" + vote_date +
                ", user=" + user +
                ", restaurant=" + restaurant +
                '}';
    }
}

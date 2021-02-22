package ru.borzdiy.lunchvote.repository;

import ru.borzdiy.lunchvote.model.Vote;

import java.time.LocalDate;
import java.util.List;

public interface VoteRepository {

    // null if not found, when updated
    Vote save(Vote vote);

    // false if not found
    boolean delete(int id);

    // null if not found
    Vote get(int id);

    // null if not found
    Vote getOne(int id);

    // null if not found
    Vote getUserVoteOnDate(int userId, LocalDate localDate);

    List<Vote> getAll();

    List<Vote> getRestaurantVotes(int restaurantId, LocalDate date);

    List<Vote> getUserVotesHistory(int userId);

}

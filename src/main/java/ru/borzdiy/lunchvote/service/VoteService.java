package ru.borzdiy.lunchvote.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.borzdiy.lunchvote.model.Vote;
import ru.borzdiy.lunchvote.repository.VoteRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.borzdiy.lunchvote.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteService {

    private final VoteRepository voteRepository;

    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public Vote create(Vote vote) {
        Assert.notNull(vote, "menu must not be null");
        return voteRepository.save(vote);
    }

    public Vote save(Vote vote) {
        return voteRepository.save(vote);
    }

    public boolean delete(int id) {
        return voteRepository.delete(id);
    }

    public Vote get(int id) {
        return checkNotFoundWithId(voteRepository.get(id), id);
    }

    public Vote getOne(int id) {
        return voteRepository.getOne(id);
    }

    public Vote getUserVoteOnDate(int user_id, LocalDate localDate) {
        return voteRepository.getUserVoteOnDate(user_id, localDate);
    }

    public List<Vote> getAll() {
        return voteRepository.getAll();
    }

    public List<Vote> getRestaurantVotes(int restaurantId, LocalDate date) {
        return voteRepository.getRestaurantVotes(restaurantId, date);
    }

    public List<Vote> getUserVotesHistory(int user_id) {
        return voteRepository.getUserVotesHistory(user_id);
    }

    public void update(Vote vote) {
        Assert.notNull(vote, "menu must not be null");
        voteRepository.save(vote);
    }
}

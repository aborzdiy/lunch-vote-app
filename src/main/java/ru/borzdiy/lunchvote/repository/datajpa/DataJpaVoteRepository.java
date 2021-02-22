package ru.borzdiy.lunchvote.repository.datajpa;

import org.springframework.stereotype.Repository;
import ru.borzdiy.lunchvote.model.Vote;
import ru.borzdiy.lunchvote.repository.VoteRepository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DataJpaVoteRepository implements VoteRepository {

    private final CrudVoteRepository voteRepository;

    public DataJpaVoteRepository(CrudVoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @Override
    public Vote save(Vote restaurant) {
        return voteRepository.save(restaurant);
    }

    @Override
    public boolean delete(int id) {
        return voteRepository.delete(id) != 0;
    }

    @Override
    public Vote get(int id) {
        return voteRepository.findById(id).orElse(null);
    }

    @Override
    public Vote getOne(int id) {
        return voteRepository.getOne(id);
    }

    @Override
    public List<Vote> getAll() {
        return voteRepository.findAll();
    }

    @Override
    public Vote getUserVoteOnDate(int userId, LocalDate localDate) {
        return voteRepository.getUserVoteOnDate(userId, localDate);
    }

    @Override
    public List<Vote> getRestaurantVotes(int restaurantId, LocalDate date) {
        return voteRepository.getRestaurantVotes(restaurantId, date);
    }

    @Override
    public List<Vote> getUserVotesHistory(int userId) {
        return voteRepository.getUserVotesHistory(userId);
    }
}

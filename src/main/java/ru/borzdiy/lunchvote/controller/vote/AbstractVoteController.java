package ru.borzdiy.lunchvote.controller.vote;

import org.springframework.beans.factory.annotation.Autowired;
import ru.borzdiy.lunchvote.controller.AbstractBaseController;
import ru.borzdiy.lunchvote.model.Vote;
import ru.borzdiy.lunchvote.service.VoteService;

import java.util.List;

public abstract class AbstractVoteController extends AbstractBaseController {

    @Autowired
    VoteService voteService;

    protected List<Vote> getAll(int user_id) {
        return voteService.getUserVotesHistory(user_id);
    }

}

package ru.borzdiy.lunchvote.util;

import ru.borzdiy.lunchvote.model.Vote;
import ru.borzdiy.lunchvote.to.VoteTo;

public class VoteUtil {

    public static VoteTo asTo(Vote vote) {
        return new VoteTo(vote.getId(), vote.getVote_date(), vote.getUser(), vote.getRestaurant());
    }

}

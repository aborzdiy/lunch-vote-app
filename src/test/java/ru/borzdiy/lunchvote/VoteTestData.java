package ru.borzdiy.lunchvote;

import ru.borzdiy.lunchvote.model.Vote;
import ru.borzdiy.lunchvote.to.VoteTo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static ru.borzdiy.lunchvote.RestaurantTestData.*;
import static ru.borzdiy.lunchvote.UserTestData.user;

public class VoteTestData {
    public static final TestMatcher<Vote> VOTE_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Vote.class, "voted_at");

    public static final int VOTE_1_ID = 100014;
    public static final int VOTE_2_ID = 100017;

    public static final Vote VOTE1 = new Vote(VOTE_1_ID, LocalDate.now(), user, RESTAURANT1);
    public static final Vote VOTE2 = new Vote(VOTE_2_ID, LocalDate.now(), user, RESTAURANT2);

    public static VoteTo getNewVote() {
        return new VoteTo(null, LocalDate.of(2021, 1, 13), user, RESTAURANT1, LocalDateTime.now());
//        return new Vote(null, LocalDate.of(2021, 1, 13), user, RESTAURANT3);
    }

    public static VoteTo getUpdateVote() {
        return new VoteTo(null, LocalDate.now(), user, RESTAURANT3, LocalDateTime.now());
    }
}

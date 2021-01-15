package ru.borzdiy.lunchvote.controller.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import ru.borzdiy.lunchvote.TestUtil;
import ru.borzdiy.lunchvote.controller.AbstractControllerTest;
import ru.borzdiy.lunchvote.service.RestaurantService;
import ru.borzdiy.lunchvote.service.VoteService;

class UserVoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestaurantUserController.REST_URL + '/';

    @Autowired
    VoteService voteService;

    @Autowired
    TestUtil testUtil;


}
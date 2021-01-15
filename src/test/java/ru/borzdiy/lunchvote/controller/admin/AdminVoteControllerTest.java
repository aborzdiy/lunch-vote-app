package ru.borzdiy.lunchvote.controller.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.borzdiy.lunchvote.TestUtil;
import ru.borzdiy.lunchvote.controller.AbstractControllerTest;
import ru.borzdiy.lunchvote.model.Menu;
import ru.borzdiy.lunchvote.model.Restaurant;
import ru.borzdiy.lunchvote.model.Vote;
import ru.borzdiy.lunchvote.service.MenuService;
import ru.borzdiy.lunchvote.service.RestaurantService;
import ru.borzdiy.lunchvote.service.VoteService;
import ru.borzdiy.lunchvote.to.MenuTo;
import ru.borzdiy.lunchvote.to.VoteTo;
import ru.borzdiy.lunchvote.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.borzdiy.lunchvote.MenuTestData.*;
import static ru.borzdiy.lunchvote.RestaurantTestData.*;
import static ru.borzdiy.lunchvote.TestUtil.userHttpBasic;
import static ru.borzdiy.lunchvote.UserTestData.admin;
import static ru.borzdiy.lunchvote.UserTestData.user;
import static ru.borzdiy.lunchvote.VoteTestData.*;

class AdminVoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminController.REST_URL;
    private static final String RESTAURANTS = REST_URL + AdminController.RESTAURANTS + '/';

    @Autowired
    VoteService voteService;

    @Test
    void getRestaurantVotes() throws Exception {
        MvcResult mvcResult = perform(
                MockMvcRequestBuilders.get(RESTAURANTS + RESTAURANT_1_ID + "/vote").with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        VoteTo[] voteTos = mapFromJson(content, VoteTo[].class);
        assertEquals(voteTos.length, 2);
    }

    @Test
    void createVote() throws Exception {
        MvcResult mvcResult = perform(
                MockMvcRequestBuilders.post(RESTAURANTS + RESTAURANT_1_ID + "/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
                .content(mapToJson(getNewVote())))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Vote vote = mapFromJson(content, Vote.class);

        mvcResult = perform(
                MockMvcRequestBuilders.get(RESTAURANTS + RESTAURANT_1_ID + "/vote?date=2021-01-13").with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        content = mvcResult.getResponse().getContentAsString();
        VoteTo[] voteTos = mapFromJson(content, VoteTo[].class);
        assertEquals(voteTos.length, 1);
    }

    @Test
    void updateVote() throws Exception {
        MvcResult mvcResult = perform(
                MockMvcRequestBuilders.post(RESTAURANTS + RESTAURANT_1_ID + "/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
                .content(mapToJson(getUpdateVote())))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        VoteTo vote = mapFromJson(content, VoteTo.class);
    }

    @Test
    void deleteVote() throws Exception {
        perform(MockMvcRequestBuilders.delete(RESTAURANTS + RESTAURANT_1_ID + "/vote/" + VOTE_1_ID)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> voteService.get(VOTE_1_ID));
    }

}
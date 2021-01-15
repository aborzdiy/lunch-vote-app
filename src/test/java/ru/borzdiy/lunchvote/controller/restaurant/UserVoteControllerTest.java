package ru.borzdiy.lunchvote.controller.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.borzdiy.lunchvote.controller.AbstractControllerTest;
import ru.borzdiy.lunchvote.controller.vote.VoteUserController;
import ru.borzdiy.lunchvote.model.Vote;
import ru.borzdiy.lunchvote.service.VoteService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.borzdiy.lunchvote.TestUtil.userHttpBasic;
import static ru.borzdiy.lunchvote.UserTestData.user;

class UserVoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL = VoteUserController.REST_URL + '/';

    @Autowired
    VoteService voteService;

    @Test
    void getAll() throws Exception {
        MvcResult mvcResult = perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Vote[] votes = mapFromJson(content, Vote[].class);
        assertEquals(votes.length, 3);
    }
}
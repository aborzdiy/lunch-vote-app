package ru.borzdiy.lunchvote.controller.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.borzdiy.lunchvote.TestUtil;
import ru.borzdiy.lunchvote.controller.AbstractControllerTest;
import ru.borzdiy.lunchvote.model.Restaurant;
import ru.borzdiy.lunchvote.service.RestaurantService;
import ru.borzdiy.lunchvote.to.MenuTo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.borzdiy.lunchvote.RestaurantTestData.*;
import static ru.borzdiy.lunchvote.TestUtil.userHttpBasic;
import static ru.borzdiy.lunchvote.UserTestData.user;
import static ru.borzdiy.lunchvote.UserTestData.wrong_user;

class UserRestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestaurantUserController.REST_URL + '/';

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    TestUtil testUtil;

    @Test
    void getAll() throws Exception {
        MvcResult mvcResult = perform(
                MockMvcRequestBuilders.get(REST_URL).with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Restaurant[] restaurants = mapFromJson(content, Restaurant[].class);
        assertEquals(restaurants.length, 5);
    }

    @Test
    void getAll_WrongAuth() throws Exception {
        perform(
                MockMvcRequestBuilders.get(REST_URL).with(userHttpBasic(wrong_user)))
                .andExpect(status().isUnauthorized())
                .andDo(print())
                .andReturn();
    }


    @Test
    void getRestaurant() throws Exception {
        MvcResult mvcResult = perform(
                MockMvcRequestBuilders.get(REST_URL + RESTAURANT_1_ID)
                        .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Restaurant restaurant = mapFromJson(content, Restaurant.class);
        RESTAURANT_MATCHER.assertMatch(restaurant, RESTAURANT1);
    }

}
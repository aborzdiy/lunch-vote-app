package ru.borzdiy.lunchvote.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.borzdiy.lunchvote.TestUtil;
import ru.borzdiy.lunchvote.model.Restaurant;
import ru.borzdiy.lunchvote.service.RestaurantService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.borzdiy.lunchvote.RestaurantTestData.*;

class AdminRestaurantControllerTest extends AbstractControllerTest {

    private static final String ADMIN_REST_URL = RestaurantController.ADMIN_REST_URL + '/';
    private static final String USER_REST_URL = RestaurantController.USER_REST_URL + '/';

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    TestUtil testUtil;

    @Test
    void getAllAdmin() throws Exception {
        MvcResult mvcResult = perform(
                MockMvcRequestBuilders.get(ADMIN_REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Restaurant[] restaurants = mapFromJson(content, Restaurant[].class);
        assertEquals(restaurants.length, 5);
    }

    @Test
    void getAllUser() throws Exception {
        MvcResult mvcResult = perform(
                MockMvcRequestBuilders.get(USER_REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Restaurant[] restaurants = mapFromJson(content, Restaurant[].class);
        assertEquals(restaurants.length, 5);
    }

    @Test
    void getRestaurantAdmin() throws Exception {
        MvcResult mvcResult = perform(
                MockMvcRequestBuilders.get(ADMIN_REST_URL + RESTAURANT_1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Restaurant restaurant = mapFromJson(content, Restaurant.class);
        RESTAURANT_MATCHER.assertMatch(restaurant, RESTAURANT1);
    }

    @Test
    void getRestaurantUser() throws Exception {
        MvcResult mvcResult = perform(
                MockMvcRequestBuilders.get(USER_REST_URL + RESTAURANT_1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Restaurant restaurant = mapFromJson(content, Restaurant.class);
        RESTAURANT_MATCHER.assertMatch(restaurant, RESTAURANT1);
    }

    @Test
    void getWithMenuUser()throws Exception {
        MvcResult mvcResult = perform(
                MockMvcRequestBuilders.get(USER_REST_URL + RESTAURANT_1_ID + "/menu"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Restaurant restaurant = mapFromJson(content, Restaurant.class);

        RESTAURANT_MATCHER.assertMatch(restaurant, RESTAURANT1);
    }

    @Test
    void createWithLocation() throws Exception {
        Restaurant newRestaurant = getNew();
        MvcResult mvcResult = perform(MockMvcRequestBuilders.post(ADMIN_REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(newRestaurant)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Restaurant created = mapFromJson(content, Restaurant.class);

        int newId = created.id();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(restaurantService.get(newId), newRestaurant);
    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }
}
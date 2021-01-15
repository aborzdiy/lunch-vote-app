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
import ru.borzdiy.lunchvote.model.Restaurant;
import ru.borzdiy.lunchvote.service.MenuService;
import ru.borzdiy.lunchvote.service.RestaurantService;
import ru.borzdiy.lunchvote.service.VoteService;
import ru.borzdiy.lunchvote.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.borzdiy.lunchvote.RestaurantTestData.*;
import static ru.borzdiy.lunchvote.TestUtil.userHttpBasic;
import static ru.borzdiy.lunchvote.UserTestData.admin;
import static ru.borzdiy.lunchvote.UserTestData.user;

class AdminRestarauntControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminController.REST_URL;
    private static final String RESTAURANTS = REST_URL + AdminController.RESTAURANTS + '/';

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    MenuService menuService;

    @Autowired
    VoteService voteService;

    @Autowired
    TestUtil testUtil;

    @Test
    void getAllRestaurants() throws Exception {
        MvcResult mvcResult = perform(
                MockMvcRequestBuilders.get(RESTAURANTS).with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Restaurant[] restaurants = mapFromJson(content, Restaurant[].class);
        assertEquals(restaurants.length, 5);
    }

    @Test
    void getAllRestaurants_WrongAuth() throws Exception {
        perform(
                MockMvcRequestBuilders.get(RESTAURANTS).with(userHttpBasic(user)))
                .andExpect(status().isForbidden())
                .andDo(print())
                .andReturn();
    }

    @Test
    void getRestaurant() throws Exception {
        MvcResult mvcResult = perform(
                MockMvcRequestBuilders.get(RESTAURANTS + RESTAURANT_1_ID).with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Restaurant restaurant = mapFromJson(content, Restaurant.class);
        RESTAURANT_MATCHER.assertMatch(restaurant, RESTAURANT1);
    }

    @Test
    void createRestaurantWithLocation_AdminAccess() throws Exception {
        Restaurant newRestaurant = getNew();
        MvcResult mvcResult = perform(MockMvcRequestBuilders.post(RESTAURANTS).with(userHttpBasic(admin))
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
    void createRestaurantWithLocation_WrongAccess() throws Exception {
        perform(MockMvcRequestBuilders.post(RESTAURANTS).with(userHttpBasic(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(getNew())))
                .andExpect(status().isForbidden())
                .andDo(print())
                .andReturn();
    }

    @Test
    void deleteRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.delete(RESTAURANTS + RESTAURANT_1_ID)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> restaurantService.get(RESTAURANT_1_ID));
    }

    @Test
    void deleteRestaurantNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(RESTAURANTS + 100100)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateRestaurant() throws Exception {
        Restaurant updated = getUpdated();
        updated.setId(null);
        perform(MockMvcRequestBuilders.put(RESTAURANTS + RESTAURANT_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
                .content(mapToJson(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        RESTAURANT_MATCHER.assertMatch(restaurantService.get(RESTAURANT_1_ID), getUpdated());
    }

    @Test
    void updateRestaurantInvalid() throws Exception {
        Restaurant invalid = new Restaurant(RESTAURANT1);
        invalid.setName("");
        perform(MockMvcRequestBuilders.put(RESTAURANTS + RESTAURANT_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
                .content(mapToJson(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andReturn();
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateRestaurantDuplicate() throws Exception {
        Restaurant updated = new Restaurant(RESTAURANT2);
        updated.setName("KFC");
        perform(MockMvcRequestBuilders.put(RESTAURANTS + RESTAURANT_2_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
                .content(mapToJson(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andReturn();
    }
}
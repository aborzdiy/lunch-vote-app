package ru.borzdiy.lunchvote.controller.menu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.borzdiy.lunchvote.TestUtil;
import ru.borzdiy.lunchvote.controller.AbstractControllerTest;
import ru.borzdiy.lunchvote.model.Menu;
import ru.borzdiy.lunchvote.service.MenuService;
import ru.borzdiy.lunchvote.to.MenuTo;
import ru.borzdiy.lunchvote.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.borzdiy.lunchvote.MenuTestData.MENU_1_ID;
import static ru.borzdiy.lunchvote.RestaurantTestData.RESTAURANT_1_ID;
import static ru.borzdiy.lunchvote.TestUtil.userHttpBasic;
import static ru.borzdiy.lunchvote.UserTestData.admin;

public class MenuControllerTest extends AbstractControllerTest {
    private static final String REST_URL = MenuController.ADMIN_REST_URL + '/';
    private static final String REST_URL_RESTAURANT = REST_URL + "/restaurant" + '/';

    @Autowired
    MenuService menuService;

    @Autowired
    TestUtil testUtil;

    @Test
    void getAll() throws Exception {
        MvcResult mvcResult = perform(
                MockMvcRequestBuilders.get(REST_URL).with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Menu[] menus = mapFromJson(content, Menu[].class);
        assertEquals(menus.length, 6);
    }

    @Test
    void getMenu() throws Exception {
        MvcResult mvcResult = perform(
                MockMvcRequestBuilders.get(REST_URL + MENU_1_ID).with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        MenuTo menuTo = mapFromJson(content, MenuTo.class);
    }

    @Test
    void getRestaurantMenu() throws Exception {
        MvcResult mvcResult = perform(
                MockMvcRequestBuilders.get(REST_URL_RESTAURANT + RESTAURANT_1_ID).with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Menu[] menus = mapFromJson(content, Menu[].class);
        assertEquals(menus.length, 2);
    }

    @Test
    void createRestaurantMenu() {
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + MENU_1_ID)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> menuService.get(MENU_1_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + 100100)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void update() {
    }
}

package ru.borzdiy.lunchvote.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.borzdiy.lunchvote.TestUtil;
import ru.borzdiy.lunchvote.model.Menu;
import ru.borzdiy.lunchvote.service.MenuService;
import ru.borzdiy.lunchvote.to.MenuTo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.borzdiy.lunchvote.MenuTestData.MENU_1_ID;
import static ru.borzdiy.lunchvote.TestUtil.userHttpBasic;
import static ru.borzdiy.lunchvote.UserTestData.admin;

public class MenuControllerTest extends AbstractControllerTest {
    private static final String ADMIN_REST_URL = MenuController.ADMIN_REST_URL + '/';

    @Autowired
    MenuService menuService;

    @Autowired
    TestUtil testUtil;

    @Test
    void getAllUrl() throws Exception {
        MvcResult mvcResult = perform(
                MockMvcRequestBuilders.get(ADMIN_REST_URL).with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Menu[] menus = mapFromJson(content, Menu[].class);
        assertEquals(menus.length, 6);
    }

    @Test
    void getMenuTo() throws Exception {
        MvcResult mvcResult = perform(
                MockMvcRequestBuilders.get(ADMIN_REST_URL + MENU_1_ID).with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        MenuTo menuTo = mapFromJson(content, MenuTo.class);
    }

}

package ru.borzdiy.lunchvote.controller.admin;

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
import static ru.borzdiy.lunchvote.MenuTestData.*;
import static ru.borzdiy.lunchvote.RestaurantTestData.RESTAURANT_1_ID;
import static ru.borzdiy.lunchvote.TestUtil.userHttpBasic;
import static ru.borzdiy.lunchvote.UserTestData.admin;
import static ru.borzdiy.lunchvote.UserTestData.user;

class AdminMenuControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminController.REST_URL;
    private static final String RESTAURANTS = REST_URL + AdminController.RESTAURANTS + '/';

    @Autowired
    MenuService menuService;

    @Autowired
    TestUtil testUtil;

    @Test
    void getRestaurantMenu() throws Exception {
        MvcResult mvcResult = perform(
                MockMvcRequestBuilders.get(RESTAURANTS + RESTAURANT_1_ID + "/menu").with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        MenuTo[] menuTos = mapFromJson(content, MenuTo[].class);
        assertEquals(menuTos.length, 1);
    }

    @Test
    void getRestaurantMenu_Date() throws Exception {
        MvcResult mvcResult = perform(
                MockMvcRequestBuilders.get(RESTAURANTS + RESTAURANT_1_ID + "/menu?date=2020-01-14").with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        MenuTo[] menuTos = mapFromJson(content, MenuTo[].class);
        assertEquals(menuTos.length, 1);
    }

    @Test
    void createRestaurantMenu() throws Exception {
        Menu newMenu = getNewMenu();
        MvcResult mvcResult = perform(MockMvcRequestBuilders.post(RESTAURANTS + RESTAURANT_1_ID + "/menu").with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(newMenu)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Menu created = mapFromJson(content, Menu.class);

        int newId = created.id();
        newMenu.setId(newId);
        MENU_MATCHER.assertMatch(created, newMenu);
        MENU_MATCHER.assertMatch(menuService.get(newId), newMenu);
    }

    @Test
    void createRestaurantMenuWithLocation_WrongAccess() throws Exception {
        perform(MockMvcRequestBuilders.post(RESTAURANTS + RESTAURANT_1_ID + "/menu").with(userHttpBasic(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(getNewMenu())))
                .andExpect(status().isForbidden())
                .andDo(print())
                .andReturn();
    }

    @Test
    void deleteRestaurantMenu() throws Exception {
        perform(MockMvcRequestBuilders.delete(RESTAURANTS + RESTAURANT_1_ID + "/menu/" + MENU_1_ID)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> menuService.get(MENU_1_ID));
    }

    @Test
    void deleteRestaurantMenuNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(RESTAURANTS + RESTAURANT_1_ID + "/menu/" + 100100)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateRestaurantMenu() throws Exception {
        Menu updated = getUpdatedMenu();
        updated.setId(null);
        perform(MockMvcRequestBuilders.put(RESTAURANTS + RESTAURANT_1_ID + "/menu/" + MENU_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
                .content(mapToJson(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        MENU_MATCHER.assertMatch(menuService.get(MENU_1_ID), getUpdatedMenu());
    }

    @Test
    void updateMenuInvalid() throws Exception {
        Menu invalid = new Menu(MENU1);
        invalid.setDish("");
        invalid.setPrice(-14);
        invalid.setMenuDate(null);
        perform(MockMvcRequestBuilders.put(RESTAURANTS + RESTAURANT_1_ID + "/menu/" + MENU_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
                .content(mapToJson(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andReturn();
    }
}
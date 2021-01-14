package ru.borzdiy.lunchvote.controller;

import org.springframework.beans.factory.annotation.Autowired;
import ru.borzdiy.lunchvote.model.Menu;
import ru.borzdiy.lunchvote.service.MenuService;
import ru.borzdiy.lunchvote.to.MenuTo;

import java.util.List;

import static ru.borzdiy.lunchvote.util.ValidationUtil.assureIdConsistent;
import static ru.borzdiy.lunchvote.util.ValidationUtil.checkNew;

public abstract class AbstractMenuController extends AbstractController {

    @Autowired
    MenuService menuService;

    public List<Menu> getAll() {
        log.info("get all");
        return menuService.getAll();
    }

    public Menu get(int id) {
        log.info("get with id={}", id);
        return menuService.get(id);
    }

    public Menu getWithRestaurant(int id) {
        log.info("get with id={}", id);
        return menuService.getWithRestaurant(id);
    }

    public Menu create(MenuTo menuTo) {
        checkNew(menuTo);
        log.info("create from TO {}", menuTo);
        return menuService.create(menuTo);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        menuService.delete(id);
    }

    public void update(MenuTo menu, int id) {
        assureIdConsistent(menu, id);
        log.info("update {}", menu);
        menuService.update(menu, id);
    }
}

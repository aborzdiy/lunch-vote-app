package ru.borzdiy.lunchvote.util;

import ru.borzdiy.lunchvote.model.Menu;
import ru.borzdiy.lunchvote.to.MenuTo;

public class MenuUtil {
    public static Menu createNewFromTo(MenuTo menuTo) {
        return new Menu(null, menuTo.getMenuDate(), menuTo.getDish(), menuTo.getPrice(), menuTo.getRestaurant());
    }

    public static MenuTo asTo(Menu menu) {
        return new MenuTo(menu.getId(), menu.getMenuDate(), menu.getDish(), menu.getPrice(), menu.getRestaurant());
    }

    public static Menu updateFromTo(Menu menu, MenuTo menuTo) {
        menu.setMenuDate(menuTo.getMenuDate());
        menu.setDish(menuTo.getDish());
        menu.setPrice(menuTo.getPrice());
        menu.setRestaurant(menuTo.getRestaurant());
        return menu;
    }
}

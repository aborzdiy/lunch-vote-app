package ru.borzdiy.lunchvote;

import ru.borzdiy.lunchvote.model.Menu;
import ru.borzdiy.lunchvote.model.Restaurant;

import java.time.LocalDate;

import static ru.borzdiy.lunchvote.RestaurantTestData.RESTAURANT1;
import static ru.borzdiy.lunchvote.RestaurantTestData.RESTAURANT2;

public class MenuTestData {
    public static final TestMatcher<Menu> MENU_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Menu.class, "restaurant");

    public static final int MENU_1_ID = 100007;
    public static final int MENU_2_ID = 100009;

    public static final Menu MENU1 = new Menu(MENU_1_ID, LocalDate.now(), "Борщ", 100, RESTAURANT1);
    public static final Menu MENU2 = new Menu(MENU_2_ID, LocalDate.now(), "Борщ", 105, RESTAURANT2);

    public static Menu getNewMenu() { return new Menu(null, LocalDate.now(), "Холодец", 250, RESTAURANT1);}

    public static Menu getUpdatedMenu() {
        Menu updated = new Menu(MENU1);
        updated.setDish("UpdatedName");
        return updated;
    }
}

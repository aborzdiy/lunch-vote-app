package ru.borzdiy.lunchvote.repository;

import ru.borzdiy.lunchvote.model.Menu;

import java.util.List;

public interface MenuRepository {

    // null if not found, when updated
    Menu save(Menu menu);

    // false if not found
    boolean delete(int id);

    // null if not found
    Menu get(int id);

    List<Menu> getAll();

}

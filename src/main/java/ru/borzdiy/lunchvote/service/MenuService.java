package ru.borzdiy.lunchvote.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.borzdiy.lunchvote.model.Menu;
import ru.borzdiy.lunchvote.repository.MenuRepository;
import ru.borzdiy.lunchvote.to.MenuTo;
import ru.borzdiy.lunchvote.util.MenuUtil;
import ru.borzdiy.lunchvote.util.exception.UpdateRestrictionException;

import java.util.List;

import static ru.borzdiy.lunchvote.model.AbstractBaseEntity.START_SEQ;
import static ru.borzdiy.lunchvote.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<Menu> getAll() {
        return menuRepository.getAll();
    }

    public Menu get(int id) {
        return checkNotFoundWithId(menuRepository.get(id), id);
    }

    public Menu getWithRestaurant(int id) {
        return checkNotFoundWithId(menuRepository.get(id), id);
    }

    @CacheEvict(value = "menus", allEntries = true)
    public Menu create(MenuTo menuTo) {
        Assert.notNull(menuTo, "menu must not be null");
        Menu menu = new Menu();
        MenuUtil.updateFromTo(menu, menuTo);
        return menuRepository.save(menu);
    }

    @CacheEvict(value = "menus", allEntries = true)
    public void delete(int id) {
        checkModificationAllowed(id);
        checkNotFoundWithId(menuRepository.delete(id), id);
    }

    @CacheEvict(value = "menus", allEntries = true)
    public void update(MenuTo menuTo, int id) {
        Assert.notNull(menuTo, "menu must not be null");
        Menu menu = get(id);
        MenuUtil.updateFromTo(menu, menuTo);
        menuRepository.save(menu);
    }

    protected void checkModificationAllowed(int id) {
        if (id < START_SEQ + 7) {
            throw new UpdateRestrictionException();
        }
    }

}

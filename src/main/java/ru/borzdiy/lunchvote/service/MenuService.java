package ru.borzdiy.lunchvote.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.borzdiy.lunchvote.model.Menu;
import ru.borzdiy.lunchvote.repository.MenuRepository;
import ru.borzdiy.lunchvote.util.exception.UpdateRestrictionException;

import java.time.LocalDate;
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

    public List<Menu> getRestaurantMenu(int restaurantId, LocalDate localDate) {
        return menuRepository.getRestaurantMenu(restaurantId, localDate);
    }

    public Menu get(int id) {
        return checkNotFoundWithId(menuRepository.get(id), id);
    }

    public Menu getWithRestaurant(int id) {
        return checkNotFoundWithId(menuRepository.getWithRestaurant(id), id);
    }

    @CacheEvict(value = "menus", allEntries = true)
    public Menu create(Menu menu) {
        Assert.notNull(menu, "menu must not be null");
        return menuRepository.save(menu);
    }

    @CacheEvict(value = "menus", allEntries = true)
    public void delete(int restaurantId, int id) {
        checkModificationAllowed(id);
        checkNotFoundWithId(menuRepository.delete(restaurantId, id), id);
    }

    @CacheEvict(value = "menus", allEntries = true)
    public void update(Menu menu, int id) {
        Assert.notNull(menu, "menu must not be null");
        menuRepository.save(menu);
    }

    protected void checkModificationAllowed(int id) {
        if (id < START_SEQ + 7) {
            throw new UpdateRestrictionException();
        }
    }

}

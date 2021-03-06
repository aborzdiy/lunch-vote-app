package ru.borzdiy.lunchvote.repository.datajpa;

import org.springframework.stereotype.Repository;
import ru.borzdiy.lunchvote.model.Menu;
import ru.borzdiy.lunchvote.repository.MenuRepository;
import ru.borzdiy.lunchvote.to.MenuTo;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DataJpaMenuRepository implements MenuRepository {

    private final CrudMenuRepository menuRepository;

    public DataJpaMenuRepository(CrudMenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public Menu save(Menu menu) {
        return menuRepository.save(menu);
    }

    @Override
    public boolean delete(int restaurantId, int id) {
        return menuRepository.delete(restaurantId, id) != 0;
    }

    @Override
    public Menu get(int id) {
        return menuRepository.findById(id).orElse(null);
    }

    @Override
    public Menu getWithRestaurant(int id) {
        return menuRepository.getWithRestaurant(id);
    }

    @Override
    public List<Menu> getAll() {
        return menuRepository.findAll();
    }

    @Override
    public List<Menu> getRestaurantMenu(int restaurantId, LocalDate localDate) {
        return menuRepository.getRestaurantMenu(restaurantId, localDate);
    }
}

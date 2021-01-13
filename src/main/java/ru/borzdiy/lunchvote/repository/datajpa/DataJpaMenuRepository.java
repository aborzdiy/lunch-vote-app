package ru.borzdiy.lunchvote.repository.datajpa;

import org.springframework.stereotype.Repository;
import ru.borzdiy.lunchvote.model.Menu;
import ru.borzdiy.lunchvote.repository.MenuRepository;

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
    public boolean delete(int id) {
        return menuRepository.delete(id) != 0;
    }

    @Override
    public Menu get(int id) {
        return menuRepository.findById(id).orElse(null);
    }

    @Override
    public List<Menu> getAll() {
        return menuRepository.findAll();
    }
}

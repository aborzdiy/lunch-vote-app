package ru.borzdiy.lunchvote.repository.datajpa;

import org.springframework.stereotype.Repository;
import ru.borzdiy.lunchvote.model.Restaurant;
import ru.borzdiy.lunchvote.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DataJpaRestaurantRepository implements RestaurantRepository {

    private final CrudRestaurantRepository restaurantRepository;

    public DataJpaRestaurantRepository(CrudRestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public boolean delete(int id) {
        return restaurantRepository.delete(id) != 0;
    }

    @Override
    public Restaurant get(int id) {
        return restaurantRepository.findById(id).orElse(null);
    }

    @Override
    public Restaurant getByName(String name) {
        return restaurantRepository.findByName(name);
    }

    @Override
    public Restaurant getWithMenu(int id, LocalDate localDate) {
        return restaurantRepository.getWithMenu(id, localDate);
    }

    @Override
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }
}

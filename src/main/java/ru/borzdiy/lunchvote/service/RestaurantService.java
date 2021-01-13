package ru.borzdiy.lunchvote.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.borzdiy.lunchvote.model.Restaurant;
import ru.borzdiy.lunchvote.repository.RestaurantRepository;
import static ru.borzdiy.lunchvote.util.ValidationUtil.checkNotFoundWithId;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository dataRepository;

    public RestaurantService(RestaurantRepository dishRepository) {
        this.dataRepository = dishRepository;
    }

    public List<Restaurant> getAll(){
        return dataRepository.getAll();
    }

    public Restaurant get(int id) {
        return dataRepository.get(id);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return dataRepository.save(restaurant);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public void delete(int id) {
        // TODO: 30.12.2020 разрешено или нет удаление объектов  checkModificationAllowed(id);
        checkNotFoundWithId(dataRepository.delete(id), id);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        // TODO: 30.12.2020 разрешено или нет удаление объектов  checkModificationAllowed(user.id());
        dataRepository.save(restaurant);
    }

}

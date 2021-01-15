package ru.borzdiy.lunchvote.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.borzdiy.lunchvote.model.Restaurant;
import ru.borzdiy.lunchvote.repository.RestaurantRepository;
import ru.borzdiy.lunchvote.util.exception.UpdateRestrictionException;

import static ru.borzdiy.lunchvote.model.AbstractBaseEntity.START_SEQ;
import static ru.borzdiy.lunchvote.util.ValidationUtil.checkNotFoundWithId;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class RestaurantService {

    private final RestaurantRepository dataRepository;

    public RestaurantService(RestaurantRepository dishRepository) {
        this.dataRepository = dishRepository;
    }

    public List<Restaurant> getAll() {
        return dataRepository.getAll();
    }

    public Restaurant get(int id) {
        return checkNotFoundWithId(dataRepository.get(id), id);
    }

    public Restaurant getWithMenu(int id, LocalDate localDate) {
        return checkNotFoundWithId(dataRepository.getWithMenu(id, Objects.requireNonNullElseGet(localDate, LocalDate::now)), id);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return dataRepository.save(restaurant);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public void delete(int id) {
        checkModificationAllowed(id);
        checkNotFoundWithId(dataRepository.delete(id), id);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        dataRepository.save(restaurant);
    }

    protected void checkModificationAllowed(int id) {
        if (id < START_SEQ + 2) {
            throw new UpdateRestrictionException();
        }
    }

}

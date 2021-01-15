package ru.borzdiy.lunchvote.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.borzdiy.lunchvote.model.Menu;
import ru.borzdiy.lunchvote.to.MenuTo;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudMenuRepository extends JpaRepository<Menu, Integer> {

    @Modifying
    @Transactional
    @Query("delete FROM Menu m WHERE m.id=:id AND m.restaurant.id=:restaurantId")
    int delete(@Param("restaurantId") int restaurantId, @Param("id") int id);

    @Query("SELECT m FROM Menu m JOIN FETCH m.restaurant r WHERE m.id=:id")
    Menu getWithRestaurant(@Param("id") int id);

    @Query("SELECT m FROM Menu m WHERE m.restaurant.id=:restaurantId")
    List<Menu> getRestaurantMenu(@Param("restaurantId") int restaurantId);

}

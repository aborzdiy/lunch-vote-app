package ru.borzdiy.lunchvote.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.borzdiy.lunchvote.model.Restaurant;

import java.time.LocalDate;

@Transactional(readOnly = true)
public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int delete(@Param("id") int id);

    Restaurant findByName(String name);

    @Query("SELECT r FROM Restaurant r JOIN FETCH r.menu m where r.id=:id and m.menuDate=:local_date")
    Restaurant getWithMenu(@Param("id") int id, @Param("local_date") LocalDate localDate);
}

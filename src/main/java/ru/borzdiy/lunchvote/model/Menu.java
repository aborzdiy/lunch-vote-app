package ru.borzdiy.lunchvote.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Entity
@Table(name = "menus", uniqueConstraints = @UniqueConstraint(columnNames = {"MENU_DATE", "DISH", "RESTAURANT_ID"}, name = "menu_unique_idx"))
public class Menu extends AbstractBaseEntity {

    @NotNull
    @Column(name = "MENU_DATE", nullable = false)
    private LocalDate menuDate;

    @NotBlank
    @Column(name = "DISH", nullable = false)
    private String dish;

    @NotNull
    @Positive
    @Column(name = "PRICE", nullable = false)
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Restaurant.class)
    @JoinColumn(name = "RESTAURANT_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference("menu-restaurant")
    private Restaurant restaurant;

    public Menu() {
    }

    public Menu(Menu menu) {
        super(menu.getId());
        this.menuDate = menu.getMenuDate();
        this.dish = menu.getDish();
        this.price = menu.getPrice();
        this.restaurant = menu.getRestaurant();
    }

    public Menu(Integer id, LocalDate menuDate, String dish, Integer price, Restaurant restaurant) {
        super(id);
        this.menuDate = menuDate;
        this.dish = dish;
        this.price = price;
        this.restaurant = restaurant;
    }

    public LocalDate getMenuDate() {
        return menuDate;
    }

    public void setMenuDate(LocalDate menuDate) {
        this.menuDate = menuDate;
    }

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "menuDate=" + menuDate +
                ", dish='" + dish + '\'' +
                ", price=" + price +
                ", restaurant=" + restaurant +
                '}';
    }
}

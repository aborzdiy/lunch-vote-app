package ru.borzdiy.lunchvote.to;

import ru.borzdiy.lunchvote.model.Restaurant;

import java.beans.ConstructorProperties;
import java.time.LocalDate;
import java.util.Objects;

public class MenuTo extends BaseTo {

    private LocalDate menuDate;
    private String dish;
    private Integer price;
    private Restaurant restaurant;

    @ConstructorProperties({"id", "menuDate", "dish", "price", "restaurant"})
    public MenuTo(Integer id, LocalDate menuDate, String dish, Integer price, Restaurant restaurant) {
        super(id);
        this.menuDate = menuDate;
        this.dish = dish;
        this.price = price;
        this.restaurant = restaurant;
    }

    public LocalDate getMenuDate() {
        return menuDate;
    }

    public String getDish() {
        return dish;
    }

    public Integer getPrice() {
        return price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuTo menuTo = (MenuTo) o;
        return menuDate.equals(menuTo.menuDate)
                && dish.equals(menuTo.dish)
                && price.equals(menuTo.price)
                && restaurant.equals(menuTo.restaurant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, menuDate, dish, price, restaurant);
    }

    @Override
    public String toString() {
        return "MenuTo{" +
                "id=" + id +
                ", menuDate=" + menuDate +
                ", dish='" + dish + '\'' +
                ", price=" + price +
                ", restaurant=" + restaurant +
                '}';
    }
}

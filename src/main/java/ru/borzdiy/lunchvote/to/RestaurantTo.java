package ru.borzdiy.lunchvote.to;

import ru.borzdiy.lunchvote.model.Menu;

import java.util.List;
import java.util.Objects;

public class RestaurantTo extends BaseTo {
    private String name;
    private List<Menu> menu;

    public RestaurantTo() {
    }

    public RestaurantTo(Integer id, String name, List<Menu> menu) {
        super(id);
        this.name = name;
        this.menu = menu;
    }

    public String getName() {
        return name;
    }

    public List<Menu> getMenu() {
        return menu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantTo that = (RestaurantTo) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "RestaurantTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

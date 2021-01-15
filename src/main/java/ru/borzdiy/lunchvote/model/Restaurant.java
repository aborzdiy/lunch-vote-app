package ru.borzdiy.lunchvote.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurants_unique_name_idx")})
public class Restaurant extends AbstractNamedEntity {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @JsonManagedReference("menu-restaurant")
    private List<Menu> menu;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @JsonManagedReference("vote-restaurant")
    private List<Vote> vote;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public Restaurant(String name, List<Menu> menu, List<Vote> vote) {
        this(null, name, menu, vote);
    }

    public Restaurant(Restaurant restaurant) {
        this(restaurant.getId(), restaurant.getName(), restaurant.getMenu(), restaurant.getVote());
    }

    public Restaurant(Integer id, String name, List<Menu> menu, List<Vote> vote) {
        super(id, name);
        this.menu = menu;
        this.vote = vote;
    }

    public List<Menu> getMenu() {
        return menu;
    }

    public List<Vote> getVote() {
        return vote;
    }

    public void setMenu(List<Menu> menu) {
        this.menu = menu;
    }

    public void setVote(List<Vote> vote) {
        this.vote = vote;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

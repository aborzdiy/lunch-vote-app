package ru.borzdiy.lunchvote.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.borzdiy.lunchvote.model.Menu;

import java.net.URI;
import java.util.List;

import static ru.borzdiy.lunchvote.controller.MenuController.ADMIN_REST_URL;

@RestController
@RequestMapping(value = ADMIN_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuController extends AbstractMenuController {
    static final String ADMIN_REST_URL = "/rest/admin/menus";
    static final String ADMIN_REST_URL_WITH_ID = "/rest/admin/menus/{id}";

    @GetMapping
    public List<Menu> getAll() {
        return super.getAll();
    }

    @GetMapping("/{id}")
    public Menu get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createWithLocation(@Validated @RequestBody Menu menu) {
        Menu created = super.create(menu);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(ADMIN_REST_URL_WITH_ID)
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Validated Menu menu, @PathVariable("id") int id) {
        super.update(menu, id);
    }

}

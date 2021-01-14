package ru.borzdiy.lunchvote.validators;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import ru.borzdiy.lunchvote.ExceptionInfoHandler;
import ru.borzdiy.lunchvote.model.AbstractNamedEntity;
import ru.borzdiy.lunchvote.model.Restaurant;
import ru.borzdiy.lunchvote.repository.RestaurantRepository;

@Component
public class UniqueRestorauntNameValidator implements org.springframework.validation.Validator {

    private final RestaurantRepository repository;

    public UniqueRestorauntNameValidator(RestaurantRepository restaurantRepository) {
        this.repository = restaurantRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return AbstractNamedEntity.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AbstractNamedEntity entity = (AbstractNamedEntity) target;
        if (StringUtils.hasText(entity.getName())) {
            Restaurant dbRestaurant = repository.getByName(entity.getName());
            if (dbRestaurant != null && !dbRestaurant.getId().equals(entity.getId())) {
                errors.rejectValue("name", "unique_name", ExceptionInfoHandler.EXCEPTION_DUPLICATE_NAME);
            }
        }
    }
}

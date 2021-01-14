package ru.borzdiy.lunchvote.validators;


import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import ru.borzdiy.lunchvote.ExceptionInfoHandler;
import ru.borzdiy.lunchvote.model.HasIdAndEmail;
import ru.borzdiy.lunchvote.model.User;
import ru.borzdiy.lunchvote.repository.UserRepository;

@Component
public class UniqueMailValidator implements org.springframework.validation.Validator {

    private final UserRepository repository;

    public UniqueMailValidator(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return HasIdAndEmail.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        HasIdAndEmail user = ((HasIdAndEmail) target);
        if (StringUtils.hasText(user.getEmail())) {
            User dbUser = repository.getByEmail(user.getEmail().toLowerCase());
            if (dbUser != null && !dbUser.getId().equals(user.getId())) {
                errors.rejectValue("email", ExceptionInfoHandler.EXCEPTION_DUPLICATE_EMAIL);
            }
        }
    }
}

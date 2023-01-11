package Notes.Spring.boot.CRUD.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import springcourse.DAO.PersonDAO;
import springcourse.models.Person;

@Component
public class PersonValidator implements Validator {

    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        // Посмотреть, есть ли человек с таким же email в БД
        if (personDAO.show(person.getEmail()).isPresent()) {
            // параметры: 1 - какое поле вызвало ошибку, 2 - код ошибки, 3 - сообщение ошибки
            errors.rejectValue("email", "", "Пользователь с данным email уже зарегистрирован");
        }
    }
}

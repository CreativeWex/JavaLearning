## Содержание

* [Spring Boot](#springBoot)
* [Spring Validator](#validator)
---

<a name = "springBoot"></a>

# Spring Boot

`@SpringBootApplication` - указывает, что это приложение Spring Boot, составная аннотация, объединяющая три другие
аннотации:

- `@SpringBootConfiguration` - определяет класс как конфигурационный. (Специализированная форма аннотации `@Configuration`)
- `@EnableAutoConfiguration` - включает автоконфигурацию Spring Boot
- `@ComponentScan` - включает сканирование компонентов. Механизм сканирования позволяет объявлять другие классы с аннотациями,
такими как @Component, @Controller, @Service, чтобы Spring автоматически обнаруживал и регистрировал их как компоненты в
контексте приложения Spring.

## Spring Boot Dev Tools

Набор дополнительных инструментов, позволяющих:
- автоматически перезапускать приложение при изменении кода;
- автоматически обновлять окно браузера при изменении ресурсов, передаваемых браузеру;
- автоматически отключать кеширование шаблонов;
- открывать встроенную консоль Н2, при применении соответствующей БД.

<a name = "validator"></a>

## Spring Validator

Реализовывает интерфейс `Validator`

```java

  // Метод в DAO который проверяет, имеется ли уже пользователь с таким email       
  public Optional<Person> show(String email) {
      return jdbcTemplate.query("SELECT * FROM person WHERE email=?", new Object[]{email},
      new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
  }

```
```java
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
```

Добавляем валидатор в методы контроллера
```java
@PostMapping
    public String createNewPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        
        if (bindingResult.hasErrors()) {
            return "people/newPerson";
        }
        personDAO.save(person);
        return "redirect:/people";
    }



@PatchMapping("/{id}")
public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
@PathVariable("id") int id) {
        personValidator.validate(person, bindingResult); //!!!!!!!!!!!!!!!!
        if (bindingResult.hasErrors()) {
        return "people/edit";
        }
        personDAO.update(id, person);
        return "redirect:/people";
        }

```


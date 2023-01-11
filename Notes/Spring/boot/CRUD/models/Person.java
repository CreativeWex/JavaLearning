package Notes.Spring.boot.CRUD.models;

import jakarta.validation.constraints.*;
public class Person {
    private int id;

    @NotEmpty(message = "Пустое имя!")
    @Size(min = 2, max = 30, message = "Имя должно быть не меньше 2 и не больше 30 символов!")
    private String name;

    @Min(value = 1, message = "Возраст должен быть больше 1!")
    @Max(value = 120, message = "Возраст не должен превышать 120!")
    private int age;

    @NotEmpty(message = "Невалидный email!")
    @Email(message = "Невалидный email!")
    private String email;

    public Person() {
    }

    public Person(int id, String name, int age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }
}

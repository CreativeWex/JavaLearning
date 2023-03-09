# JPA Criteria Queries

Позволяет писать заросы без нативного SQL и предоставляет ООП контроль над запросами.

_Зависимость_
```java
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-core</artifactId>   
    <version>5.3.2.Final</version>
</dependency>
```

# Применение

Сущность Task

```java
public class Task {
    private Long id;
    private String name;
    private Date date;
    private String description;
    private String status;
    //getters, setters etc
}
```

## Получение всех строк сущности

1. Создание сущности `Session` из `SessionFactory`.
2. Создание сущности `CriteriaBuilder` с помощью метода `getCriteriaBuilder()`.
3. Создание сущности `CriteriaQuery` с помощью метода `CriteriaBuilder.createQuery()`.
4. Создание сущности `Query` с помощью метода `Session.createQuery()`.
5. Вызов `getResultList()` объекта `query` для получения результата.

```java
Session
```


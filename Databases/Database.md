# Java и базы данных

## Содержание
JDBC API
* [Пример создания соединения](#connection)
* [Statement - создание SQL-выражений](#statement)
* [PreparedStatement](#preparedStatement)
* [Выполнение SQL-выражений](#execute)

[JdbcTemplate](#JdbcTemplate)
* [JdbcTemplateSteps](#JdbcTemplateSteps)
* [RowMapper](#RowMapper)
* [JdbcTemplate Execution](#JdbcTemplateExecution)

# JDBC API

<a name ="connection"></a>

## Создание соединения на примере HikariCP

```java
HikariConfig config = new HikariConfig(); // Создание и заполнение конфига соединения
config.setJdbcUrl(DB_URL);
config.setUsername(DB_USERNAME);
config.setPassword(DB_PASSWORD);

HikariDataSource hikariDataSource = new HikariDataSource(config); // Создание пула соединений
if (hikariDataSource.getConnection() == null) {
    throw new SQLException("Database connection failed");
}
return hikariDataSource.getConnection(); // Возврат соединения типа Connection
```

<a name ="statement"></a>

## Statement - создание SQL-выражений

Для **выполнения команд** используется объект класса `Statement`.
- `Statement` - базовый, предназначен для выполнения простых запросов и извлечения результатов.
- `PreparedStatement`- позволяет добавлять в запросы входные параметры; добавлять методы управления входными папаметрами.
- `CallableStatement` -  используется для вызовов хранимых процедур; добавляет методы для манипуляции выходными параметрами.

### Пример

```java
Connection connection = DriverManager.getConnection(url, username, password);
Statement statement = connection.createStatement();
```

<a name ="execute"></a>

## Выполнение SQL-выражений

- `executeQuery` - используется в запросахв запросах, результатом которых является один единственный набор значений,
таких как запросов типа **SELECT**. <br><br>
- `executeUpdate` - следует использовать для  **INSERT**, **UPDATE**, **DELETE** - результат - количество измененных
строк таблицы. `CREATE TABLE`, `DROP TABLE` - возвращаемое значениие 0. <br><br>
- `execute` - редко используется, в случаях, когда операторы SQL возвращают более одного набора данных, более одного
счетчика обновлений или и то, и другое.

### Пример
```java
ResultSet resultSet = statement.executeQuery("SElECT * FROM chat.messages WHERE id = " + id);
if(resultSet.next()){
    long authorId=resultSet.getInt("author");
    long roomId=resultSet.getInt("chatroom");
    long messageId=resultSet.getInt("id");
}

// Проверить, если результате выполнения запроса не нашлось ни одной строки 
if (!resultSet.next()) {
    throw new NotSavedSubEntityException();
} else  {
    String name = resultSet.getString("name");
    if (!chatroom.getName().equals(name)) {
    throw new NotSavedSubEntityException();
    }
}
```

<a name ="preparedStatement"></a>

## Использование PreparedStatement

**PreparedStatement** предварительно компилирует запросы, которые могут содержать входные параметры обозначенные символом
`?`, вместо которых определяются значения.
> Нумерация `?` **начинается с 1**.

**Предотвращает уязвимость к SQL-инъекциям**

### Пример

```java
PreparedStatement pstmt = null;
//~~~ Чтение таблицы БД ~~~
pstmt = connection.prepareStatement(
        "SELECT * FROM GOODS where id > ? and id < ?");
// Определяем значения параметров
pstmt.setInt(1, 2);
pstmt.setInt(2, 10);
// Выполнение запроса
ResultSet rs = preparedStatement.executeQuery();

// Вывод результата запроса
while (rs.next()) {
   System.out.println("" + rs.getRow() + ". " +
                      "id = " + result2.getInt("id") + 
					  ", name = '" + rs.getString("name"));
}
//~~~ Запись в таблицу БД ~~~
pstmt = connection.prepareStatement(
       "INSERT INTO GOODS(name) values(?)");
pstmt.setString(1, "Кофе");
pstmt.executeUpdate();
```

---

<br>

<a name = "JdbcTemplate"></a>

# Jdbc Template

Зависимость `Spring jdbc`

Обертка вокруг JDBC API, которая предоставляется Spring Framework

1. Сокращает код за счет отсутствия создания `Connection`, `Statement`, работы с `ResultSet`.
2. Предотвращает дублирование кода
3. Отсутствие неинформативного `SQLException`

<a name = "JdbcTemplateSteps"></a>

## Алгоритм работы с JdbcTemplate

### 1. Создать бин для DataSource и JdbcTemplate
```java
@Bean
public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();

    dataSource.setDriverClassName("org.postgresql.Driver");
    dataSource.setUrl("jdbc:postgresql://localhost:5432/springcourse");
    dataSource.setUsername("postgres");
    dataSource.setPassword("1234");

    return  dataSource;
}

@Bean
public JdbcTemplate jdbcTemplate() {
    return new JdbcTemplate(dataSource());
}
```

### 2. Добавить поле и соответствующий конструктор в DAO-класс
Спринг внедрит зависимость с помощью аннотации `@Autowired`
```java
private JdbcTemplate jdbcTemplate;

@Autowired
public PersonDAO(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
}
```

<a name = "RowMapper"></a>

### 3. Создать RowMapper
**RowMapper** - объект, отображающий строки результата запроса в POJO-объекты указанного класса

```java
public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();
        person.setId(rs.getInt("id"));
        person.setName(rs.getString("name"));
        person.setEmail(rs.getString("email"));
        person.setAge(rs.getInt("age"));

        return person;
    }
}
```
Класс реализовывает интерфейс RowMapper. Создаем новый объект, помещаем значения из `resultSet`.

#### BeanPropertyRowMapper
В случае совпадения имени поля и имени ячейки таблицы можно использовать BeanPropertyRowMapper
```java
```java
public List<Person> index() {
    return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
}
```

<a name = "JdbcTemplateExecution"></a>

### 4. Выполнение запроса

**Показать всех пользователей из таблицы.**

```java
public List<Person> index() {
    return jdbcTemplate.query("SELECT * FROM Person", new PersonMapper());
}
```
<br>

**Показать пользователя c конкретным id.**

`new Object[]{id}` - массив значений, которые должны быть подставлены вместо `?` в `preparedStatement`.
(в данном примере - это id).

```java
public Person show(int id) {
   return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id}, new PersonMapper())
           .stream().findAny().orElse(null);
}
```

**Сохранение пользователя**

```java
public void save(Person person) {
    jdbcTemplate.update("INSERT INTO Person(name, age, email) VALUES(?, ?, ?)", person.getName(), person.getAge(), person.getEmail());
}
```

**Обновить пользователя**
```java
public void update(int id, Person updatedPerson) {
    jdbcTemplate.update("UPDATE Person SET name=?, age=?, email=? WHERE id=?", updatedPerson.getName(), updatedPerson.getAge(),
            updatedPerson.getEmail(), id);
}
```

**Удалить пользователя**
```java
public void delete(int id) {
    jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
}
```


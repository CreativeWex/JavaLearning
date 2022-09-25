# Java и базы данных

## Содержание

* [Пример создания соединения](#connection)
* [Statement - создание SQL-выражений](#statement)
* [PreparedStatement](#preparedStatement)
* [Выполнение SQL-выражений](#execute)


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


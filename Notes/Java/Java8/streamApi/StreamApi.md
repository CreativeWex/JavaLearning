# Stream API

Используются для обработки потока данных с помощью цепочки методов.

**Поток (Stream)**: Поток представляет последовательность элементов данных. Он не хранит данные, а лишь обрабатывает их на лету при выполнении операций.

### Преимущества Stream

- благодаря стримам **больше не нужно писать стереотипный код** каждый раз, когда приходится что-то делать с данными: сортировать, фильтровать, преобразовывать. Разработчики меньше думают о стандартной реализации и больше времени уделяют более сложным вещам;
<br><br>
- методы Stream API **не изменяют исходные коллекции**, уменьшая количество побочных эффектов.
<br><br>
- Распараллеливать проведений операций с коллекциями стало проще. Там, где раньше пришлось бы проходить циклом, **стримы значительно сокращают количество кода**.

# Основные методы

Выделяют два основных типов методов:

- **конвейерные**: операции применяются к потоку и возвращают новый поток. Они выполняются лениво, что означает, что они не изменяют исходный поток данных, а создают новый поток с примененными операциями.
- **терминальные**: применяются к потоку и возвращают результат. Они являются триггером для выполнения всех промежуточных операций и завершения обработки данных в потоке.


### filter(Predicate<T> predicate) - фильтр
Фильтрует элементы потока согласно условию, заданному предикатом.

Пример: 
Список пользователей, имя которых содержит "М"
```java
userList.stream().filter(user -> user.getFirstName().contains("M")).collect(Collectors.toSet()).forEach(System.out::println);
```

<br>

### map(Function<T, R> mapper) - преобразование элементов
Применяет функцию к каждому элементу потока и возвращает новый поток с преобразованными значениями.

Пример: создание списка из id-пользователей
```java
List<Integer> ids = userList.stream().map(User::getId).map(Long::intValue).collect(Collectors.toList());
ids.forEach(System.out::println);
```
Пример: получение нового списка пользователей с измененными полями
```java
userList.stream().map(user -> new User(user.getId(), "X", "Y", 0, "XY")).forEach(System.out::println);
```

Пример: замены имени пользователя
```java
userList.stream().map(user -> {
            if (user.getFirstName().equals("Michael")) {
                return new User(user.getId(), "Nikita", user.getLastName(), user.getAge(), user.getNationality());
            }
            return user;
        }).forEach(System.out::println);
```

<br>

### flatMap() - плоский список

Применение flatMap позволяет объединить несколько потоков в один и получить плоский список элементов.
Таким образом, flatMap часто применяется, когда у нас есть вложенные коллекции или списки, и мы хотим получить
один плоский список всех элементов.

Пример: есть список из объектов Person, и каждый Person имеет список его друзей. Необходимо получить список всех друзей
из всех Person.

```java
public class Person {
    private String name;
    private List<Person> friends;

    // Конструктор и геттеры

    public static void main(String[] args) {
        List<Person> persons = List.of(
            new Person("Alice", List.of(new Person("Bob"), new Person("Charlie"))),
            new Person("David", List.of(new Person("Eve"), new Person("Frank")))
        );

        List<Person> allFriends = persons.stream()
                                         .flatMap(person -> person.getFriends().stream())
                                         .toList();

        allFriends.forEach(friend -> System.out.println(friend.getName()));
    }
}

```

<br>

### sorted(Comparator<T> comparator) - сортировка
Сортирует элементы потока в соответствии с компаратором

Пример: **сортировка по возрастанию**
```java
userList.stream().sorted(Comparator.comparingInt(User::getAge))
```

Пример: **сортировка по убыванию**
```java
userList.stream().sorted(Comparator.comparingInt(User::getAge).reversed())
```

Пример: **множественная сортировка**
```java
userList.stream().sorted(Comparator.comparing(User::getLastName).thenComparing(User::getFirstName))
```
<br>

### min, max, average, count, sum
Предоставляет удобный способ получения статистической информации о числовом потоке, такой как количество элементов, сумму, минимальное и максимальное значение, среднее значение и т.д.

Пример:
```java
List<Integer> ages = userList.stream().map(User::getAge).toList();
        int minimalAge = ages.stream().min(Integer::compare).orElse(Integer.MAX_VALUE);
        int maximalAge = ages.stream().max(Integer::compare).orElse(Integer.MIN_VALUE);
        double averageAge = ages.stream().mapToInt(Integer::intValue).average().orElse(0);
        int sum = ages.stream().mapToInt(Integer::intValue).sum();
        long count = ages.stream().count();
```

<br>

### allMatch, anyMatch

Проверяют соответстиве элементов соответственно предикату.

Пример: проверка на то, что возраст всех пользователей больше 76
```java
boolean isAllAgesGreaterThan76 = userList.stream().allMatch(user -> user.getAge() > 76);
```

Пример: проверка на то, что возраст хотя бы одного пользователя больше 76
```java
boolean isAnyAgesGreaterThan76 = userList.stream().anyMatch(user -> user.getAge() > 76);
```


## Примеры

### Вычисление максимальной длины firstname

```java
int maxFirstnameLength = userList.stream().mapToInt(user -> user.getFirstName().length()).summaryStatistics().getMax();
```

### Конвертация коллекций

```java
Set<User> userSet = userList.stream().collect(Collectors.toSet());
LinkedList<User> userLinkedList = userList.stream().collect(Collectors.toCollection(LinkedList::new));
Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
```
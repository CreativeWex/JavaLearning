# Java 8
# Функциональные интерфейсы

Если интерфейс содержит только один абстрактный метод, то н назвается **функциональным**.

Примеры: 
1. Интерфейс `Runnable` ялвяется функциональным, потому что он содержит только один метод `run()`.
2. Функциональный интерфейс
```java
// Необязательная аннотация, помечающая, что интерфейс является функциональным
@FunctionalInterface
public interface MyInterface{
    // один абстрактный метод
    double getValue();
}
```
В приведенном выше примере, интерфейс MyInterface имеет только один абстрактный метод getValue(). Значит, этот интерфейс — функциональный.
3. Функциональный интерфейс с применением дженериков
```java
@FunctionalInterface
interface Function<T1, T2, R> {
    // один абстрактный метод
    R apply(T1 arg1, T2 arg2);
}
```
# Лямбда выражения

Представляют собой анонимный класс или метод. 

Свойства:
- не выполняется само по себе;
- в методы можно передавать параметры. Лямбда выражения позволяют передавать блок команд в качестве
параметра.

Синтаксис
```java
(параметры) -> {блок команд}
```

Виды:
1. Однострочные
```java
() -> System.out.println("Hello world");
(int a, int b) -> { return a + b; }
```
2. Блочные (многострочные)
Обязательно наличие `return`
```java
() -> {
    double pi = 3.1415;
    return pi;
};
```

## Пример

### Сортировка массива строк

Без лямбда-оператора
```java
List<String> names = Arrays.asList("peter", "anna", "bob", "mike", "ada");
Collections.sort(names, new Comparator<String>() {
    @Override
    public int compare(String o1, String o2) {
        // o1.compareTo(o2); // a-z
        // o2.compareTo(o1); // z-a
        return o1.compareTo(o2);
    }
});
```
С лямбла-оператором
```java
List<String> names = Arrays.asList("peter", "anna", "bob", "mike", "ada");
// b.compareTo(a) // z-a
// a.compareTo(b) // a-z
Collections.sort(names, (a, b) -> b.compareTo(a));
```
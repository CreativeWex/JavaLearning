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

Лямбда представляет собой набор инструкций, которые можно выделить в отдельную переменную и затем многократно вызвать в различных местах программы.

Основу лямбда-выражения составляет лямбда-оператор, который представляет стрелку ->. Этот оператор разделяет лямбда-выражение на две части: левая часть содержит список параметров выражения, а правая, собственно, представляет тело лямбда-выражения, где выполняются все действия.

Лямбда-выражение не выполняется само по себе, а образует реализацию метода, определенного в функциональном интерфейсе. При этом важно, что функциональный интерфейс должен содержать только один единственный метод без реализации.

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

Анонимный метод
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
Лямбда
```java
List<String> names = Arrays.asList("peter", "anna", "bob", "mike", "ada");
// b.compareTo(a) // z-a
// a.compareTo(b) // a-z
Collections.sort(names, (a, b) -> b.compareTo(a));
```

### Определение количества подходящих элементов в массиве

Функциональный интерфейс
```java
@FunctionalInterface
public interface ColorCheck {
    boolean check(String color);
}
```

Применение
```java
public class FunctionalInterfaceColorCheck {
    private int getColorNumber(String[] colors, ColorCheck checker) {
        int result = 0;
        for (String color : colors) {
            if (checker.check(color)) {
                result++;
            }
        }
        return  result;
    }

    public static void main(String[] args) {
        String[] colors = {"blue", "blue", "red", "blue"};
        FunctionalInterfaceColorCheck checker = new FunctionalInterfaceColorCheck();
        
        // Использование анонимных методов
        String[] moreColors = {"blue", "blue", "red", "blue", "yellow", "yellow", "green", "green", "green", "green"};
        System.out.println("yellow = " + checker.getColorNumber(moreColors, new ColorCheck() {
            @Override
            public boolean check(String color) {
                return color.equals("yellow");
            }
        }));

        System.out.println("green = " + checker.getColorNumber(moreColors, new ColorCheck() {
            @Override
            public boolean check(String color) {
                return color.equals("green");
            }
        }));

        //применение лямбда-выражения
        System.out.println("green = " + checker.getColorNumber(moreColors, color -> color.equals("green")));
        System.out.println("yellow = " + checker.getColorNumber(moreColors, color -> color.equals("yellow")));
        System.out.println("size less than 4 = " + checker.getColorNumber(moreColors, color -> color.length() < 4));

        //применение лямбда-блока
        System.out.println("got 2+ 'e' letters = " + checker.getColorNumber(moreColors, color -> {
            int counter = 0;
            for (int i = 0; i < color.length(); i++) {
                if (color.charAt(i) == 'e') {
                    counter++;
                }
            }
            return counter >= 2;
        }));
    }
}
```

## Лямбда vs анонимные классы
Лямбда выражения являются альтернативой анонимным классам. Но они не одинаковы.

Общее:

* Локальные **переменные могут быть использованы только** если они final или effective final.
* Разрешается доступ к переменным класса и статическим переменным класса.
* Они не должны выбрасывать больше исключений чем определено в throws метода функционального интерфейса.

Различия:

* Для анонимных классов ключевое слово this ссылается на сам класс. Для лямбды выражений на внешний класс.
* Анонимные классы компилируются во внутренние классы. Но лямбда выражения преобразуются в статические private
методы класса, в котором они используют invokedynamic инструкцию. **Лямбда более эффективны, так как не надо
загружать еще один класс**.

## Встроенные функциональные интерфейсы
В Java 8 добавлены встроенные функциональные интерфейсы в пакет _java.util.function_:

* Predicate (T -> boolean)
* Consumer
* Function
* Supplier
* UnaryOperator

# Интерфейс Predicate (T -> boolean)

Принимает на вход значение, проверяет состояние и возвращает `boolean` значение в качестве результата.

`Predicate` **подтверждает** какое-то значение как true или false.

Описание интерфейса Predicate:

```java
@FunctionalInterface
public interface Predicate<T> {
    boolean test(T t);
   }
```

### Пример
```java
public class PredicateDemo {
    public static void main(String[] args) {
        // Является ли число негативным
        Predicate<Integer> negative = integer -> integer < 0;
        System.out.println(negative.test(-6));
        System.out.println(negative.test(6));
        System.out.println(negative.test(0));

        // Является ли строка пустой
        System.out.println("\nisNull");
        Predicate<String> isNull = s -> s == null;
        System.out.println(isNull.test("Hello"));
        System.out.println(isNull.test(""));
        System.out.println(isNull.test(null));
    }
}
```

## Методы по умолчанию:
```java
default Predicate<T> and(Predicate<? super T> other); // должны выполняться оба
default Predicate<T> or(Predicate<? super T> other); // хотя бы один
default Predicate<T> negate(); // отрицание
```

### Пример

```java
import java.util.function.Predicate;

public class PredicateDemo {
    public static void main(String[] args) {
        System.out.println("\nPredicate.and()");
        Predicate<String> contatainsLetterA = s -> s.contains("a") || s.contains("A");
        Predicate<String> contatainsLetterB = s -> s.contains("b") || s.contains("B");
        // true + true = true
        System.out.println(contatainsLetterA.and(contatainsLetterB).test("abcd"));
        //true + false = false
        System.out.println(contatainsLetterA.and(contatainsLetterB).test("acd"));

        System.out.println("\nPredicate.negate()");
        // true + !false = true
        System.out.println(contatainsLetterA.and(contatainsLetterB.negate()).test("acd"));
    }
}
```

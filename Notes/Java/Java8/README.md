# Содержание

* Функциональные интерфейсы
  * [Функциональные интерфейсы](#funcInt)
* Лямбда-выражения
  * [Лямбда-выражения](#lambda)
  * [Лямбда vs анонимные классы](#lambdaVsAnon)
* Встроенные функциональные интерфейсы
  * [Встроенные функциональные интерфейсы](#innerLambIn) 
  * [Интерфейс Predicate (T -> boolean)](#predicate)
  * [Интерфейс Consumer (T -> void)](#consumer)
  * [Интерфейс Function (T -> R)](#function)
  * [Интерфейс Supplier (() -> T)](#supplier)
  * [Интерфейс UnaryOperator (T -> T)](#unary)
* Ссылки на методы
  * [Ссылки на методы](#mlinks)

<a name="funcInt"></a>

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

<a name="lambda"></a>
# Лямбда-выражения

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

<a name="lambdaVsAnon"></a>

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

<a name="innerLambIn"></a>

## Встроенные функциональные интерфейсы
В Java 8 добавлены встроенные функциональные интерфейсы в пакет _java.util.function_:

* Predicate (T -> boolean)
* Consumer
* Function
* Supplier
* UnaryOperator

<a name="predicate"></a>

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

> Метод `and` комбинирует два предиката, но он оптимизирован для выполнения короткого замыкания
(short-circuiting). Это означает, что **если первый предикат в цепочке вернет false, то второй предикат не будет выполнен**, так как результат всей операции уже будет известен.

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

<a name="consumer"></a>

# Интерфейс Consumer (T -> void)

**Consumer интерфейс используется** в случае, если необходимо передать объект на вход и произвести над ним некоторые операции
не возвращая результат.

Описание интерфейса Consumer:
```java
@FunctionalInterface
public interface Consumer<T> {
    void accept(T t);
}
```

### Пример

Одно действие с объектом
```java
 public static void main(String[] args){
        System.out.println("hw");
        Consumer<String> printUpperCase=s->System.out.println(s.toUpperCase());
        Consumer<String> printLowerCase=s->System.out.println(s.toLowerCase());
        printUpperCase.accept("Hello World");
        printLowerCase.accept("Hello World");
        }
```

Последовательность действий с объектом
```java
public class ConsumerDemo {
    public class Store {
        public int productLeft;

        public Store(int productLeft) {
            this.productLeft = productLeft;
        }
    }


    public static void main(String[] args) {
        //Продажа товара
        System.out.println("\nproduct test");
        Consumer<Store> sellOneProduct = store -> {
            System.out.println("product sold");
            store.productLeft--;
            System.out.println("product left = " + store.productLeft);
        };
        Store store = new ConsumerDemo().new Store(10);
        sellOneProduct.accept(store);
        sellOneProduct.accept(store);
        sellOneProduct.accept(store);
    }
}

```

## Методы по умолчанию:

Consumer интерфейс содержит метод по умолчанию, который возвращает составной Consumer, выполняющий последовательно действия указанные в каждом интерфейсе:

```java
default Consumer<T> andThen(Consumer<? super T> after)
```

### Пример

```java
public static void main(String[] args) {
        Consumer<String> printUpperCase = s -> System.out.println(s.toUpperCase());
        Consumer<String> printLowerCase = s -> System.out.println(s.toLowerCase());
        printUpperCase.andThen(printLowerCase).accept("Hello World");
    }
```

<a name="function"></a>

# Интерфейс Function (T -> R)

Принимает значение в качестве аргумента одного типа и возвращает другое значение. Часто используется для
преобразования одного значения в другое:

```java
@FunctionalInterface
public interface Function<T, R> {
    R apply(T t);
}
```

### Пример

```java
public class FunctionDemo {
    public static void main(String[] args) {
        Function<Double, Long> doubleToLongConverter = d -> Math.round(d);
        System.out.println(doubleToLongConverter.apply(5.23));

        Function<Long, String> longStringFunction = l -> {
            if (l < 0) {
                return "Отрицательное число";
            } else if (l > 0) {
                return "Положительное число";
            }
            return "Ноль";
        };
        System.out.println(longStringFunction.apply(-1L));
        System.out.println(longStringFunction.apply(1L));
        System.out.println(longStringFunction.apply(0L));
    }
}
```

## Методы по умолчанию:
```java
default <V> Function<T, V> andThen(Function<? super R, ? extends V> after);
default <V> Function<V, R> compose(Function<? super V, ? extends T> before);
```

Эти методы позволяют объединить несколько функций в одну цепочку, что может быть полезно при преобразовании данных.

- **andThen()** - выполняется после текущего интерфейса;
- **compose()** - выполняется перед текущим интерфейсом.

```java
static <T> Function<T, T> identity() // всегда возвращает входной параметр.
```

<a name="supplier"></a>

# Интерфейс Supplier (() -> T)

Интерфейс Supplier используется тогда, когда на вход не передаются значения, но необходимо вернуть результат. 

```java
public class SupplierDemo {
    public static void main(String[] args) {
        String str = "hello world";
        Supplier<String> supplier = () -> str.toUpperCase();
        System.out.println(supplier.get());
    }
}
```

<a name="unary"></a>

# Интерфейс UnaryOperator (T -> T)
**Расширяет функционал функционального интерфейса Function**. Используется в случае, если аргумент и возвращаемое значение одного типа.

```java
@FunctionalInterface
public interface UnaryOperator<T> extends Function<T, T>
{
…
}
```
### Пример

```java
import java.util.function.UnaryOperator;

public class UnaryOperatorDemo {
    public static void main(String[] args) {
        UnaryOperator<String> uo = s -> s.toUpperCase();
        // То же, что и Function<String, String> f = s -> s.toUpperCase();
        System.out.print(uo.apply("Java 8"));
    }
}
```

<a name="mlinks"></a>

# Ссылки на методы

Если лямбда выражения вызывают только один существующий метод, лучше ссылаться на этот метод по его имени

### Пример

```java
Consumer<String> consumer = str -> System.out.println(str);
```
Можно переписать с помощью method references следующим образом:
```java
Consumer<String> consumer = System.out::println;
```

Ссылки на методы бывают четырех видов:

| Тип                                                           | Пример                               |
|---------------------------------------------------------------|--------------------------------------|
| Ссылка на статический метод                                   | ContainingClass::staticMethodName    |
| Ссылка на нестатический метод конкретного объекта             | containingObject::instanceMethodName |
| Ссылка на нестатический метод любого объекта конкретного типа | ContainingType::methodName           |
| Ссылка на конструктор                                         | ClassName::new                       |
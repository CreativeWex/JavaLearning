<a name="Begin"></a>

# Содержание
* Java
  * [Классы | ООП](#OOP)
  * [Коллекции | Структуры данных](#Collections)
* Базы данных
  * [Базы данных | JDBC API | Jdbc Template](#JDBC)
* Spring
  * [Spring Core](#SpringCore)
  * [Spring MVC](#SpringMVC)
* Архитектура
  * [Архитектурные паттерны | Принципы проектирования](#Patterns)
  * [REST | HTTP](#REST)
* Прочее 
  * [RegEx - Regular Expressions - Регулярные Выражения](#RegEx) <br>
  * [Класс Random](#random) <br>
  * [Класс File](#file)
---

<a name="OOP"></a>

## Классы | ООП
- ООП
  - [Детали ООП](OOP.md)
  - [Преимущества и недостатки ООП](OOP.md#OOPpluses)
  - [Статическое и динамическое связывание](OOP.md#OOPbinding)
  - [Инкапсуляция и реализации сокрытия](OOP.md#OOPIncaps)
- Парадигмы ООП
  - [Наследование](OOP.md#OOPInherit)
  - [Полиморфизм](OOP.md#OOPPolymorph)
  - [Абстракция](OOP.md#Abstract)
  - [Обмен сообщениями](OOP.md#Letters)

---

<a name="Collections"></a>

## Коллекции | Структуры данных
* Интерфейс Map
  * [Map](Algorithms/Collection/README.md#map)
  * [HashMap](Algorithms/Collection/README.md#hashmap)
  * [LinkedHashMap](Algorithms/Collection/README.md#linkedmap)
  * [TreeMap](Algorithms/Collection/README.md#treemap)
  * [HashTable](Algorithms/Collection/README.md#hashtable)
* Интерфейс Collection
  * [Set](Algorithms/Collection/README.md#set)
  * [HashSet](Algorithms/Collection/README.md#hashset)
  * [LinkedHashSet](Algorithms/Collection/README.md#linkedhashset)
  * [TreeSet](Algorithms/Collection/README.md#treeset)
  * List
  * [ArrayList](Algorithms/Collection/README.md#arraylist)
  * LinkedList
  * Queue
  * Deque
* Перечисления
  * [Enum](Algorithms/Collection/README.md#enum)
  * [EnumSet](Algorithms/Collection/README.md#enumset)


---

<a name="JDBC"></a>

## Базы данных | JDBC API | Jdbc Template
* JDBC API
  * [Пример создания соединения](Database.md#connection)
  * [Statement - создание SQL-выражений](Database.md#statement)
  * [PreparedStatement](Database.md#preparedStatement)
  * [Выполнение SQL-выражений](Database.md#execute)
* JdbcTemplate
  * [JdbcTemplate](Database.md#JdbcTemplate)
  * [Алгоритм работы с JdbcTemplate](Database.md#JdbcTemplateSteps)
  * [RowMapper](Database.md#RowMapper)
  * [Выполнение запросов](Database.md#JdbcTemplateExecution)
---

<a name="Patterns"></a>

## Архитектурные паттерны | Принципы проектирования

* Паттерны
  * [Виды и реализация](Patterns/)
  * [Порождающие](Patterns/README.md#instantiate)
  * [Структурные](Patterns/README.md#structure)
  * [Поведенческие](Patterns/README.md#responsibilities)
* Принципы проектирования
  * [Принципы SOLID](ProgrammingPrinciples.md#Solid) -
    [SRP](ProgrammingPrinciples.md#srp),
    [OCP](ProgrammingPrinciples.md#OCP),
    [LSP](ProgrammingPrinciples.md#LSP),
    [ISP](ProgrammingPrinciples.md#ISP),
    [DIP](ProgrammingPrinciples.md#DIP)

  * [YAGNI](ProgrammingPrinciples.md#YAGNI)
  * [KISS](ProgrammingPrinciples.md#KISS)
  * [DRY](ProgrammingPrinciples.md#DRY)
  * [Чистый код по Р. Мартину](CleanCode.md)

---

<a name="SpringCore"></a>

## Spring Core
IoC, DI, Beans, Configuration

* [DI / IoC](spring/core/Spring.md#diIoc)
* [Application context](spring/core/Spring.md#ApplicationContext)
* [Типичные шаги в работе со Spring](spring/core/Spring.md#SpringSteps)
* Конфигурация Spring приложения. XML
  * [Способы внедрения зависимостей](spring/core/Spring.md#injectionMethods)

* Конфигурация Spring приложения. XML + Аннотации
  * [Аннотации](spring/core/Annotations.md#Annotations)
  * [Внедрение зависимостей с помощью аннотаций](spring/core/Annotations.md#DI)

* Конфигурация Spring приложения. Аннотации + Java код
  * [Java-код](spring/core/javaCode.md)
---

<a name="SpringMVC"></a>

## Spring MVC
* [DispatcherServlet](spring/mvc/README.md#DispatcherServlet)
* [Controller](spring/mvc/README.md#Controller)
* [HTTP](spring/mvc/README.md#HTTP)
* [Model](spring/mvc/README.md#Model)
* [View](spring/mvc/README.md#View)
---

<a name="REST"></a>

## REST | HTTP
* REST
  * [RESTful](REST/README.md)
  * [Ограничения и принципы](REST/README.md#principles)
* Протокол HTTP
  * [HTTP](spring/mvc/README.md#HTTP)
  * [Коммуникаия между клиентом и сервером](REST/README.md#communcation)
  * [URI и ресурсы](REST/README.md#URIResources)
  * [Примеры HTTP запросов](REST/README.md#examples)
---

# Отдельные темы

<a name="file"></a>

# Класс File

Позволяет получить информацию о файле: права доступа, время и дата создания, путь к каталогу. А также осуществлять навигацию по иерархиям подкаталогов.

> Если класс представляет каталог, то его метод list() возвращает массив строк с именами всех файлов.

### Констукторы

* `File(String path)` - указывается путь к файлу без указания имени файла
* `File(String dirPath, Sring name)` - указывается путь к файлу и имя файла
* `File(URI uri)` - указывается объекта URI, описывающий файл

### Методы

* `getAbsolutePath()` - абсолютный путь файла, начиная с корня системы. В Android корневым элементом является символ слеша (/)
* `canRead()` - доступно для чтения
* `canWrite()` - доступно для записи
* `exists()` - файл существует или нет
* `getName()` - возвращает имя файла
* `getParent()` - возвращает имя родительского каталога
* `getPath()` - путь
* `lastModified()` - дата последнего изменения
* `isFile()` - объект является файлом, а не каталогом
* `isDirectory` - объект является каталогом
* `isAbsolute()` - возвращает true, если файл имеет абсолютный путь
* `renameTo(File newPath)` - переименовывает файл. В параметре указывается имя нового имени файла. Если переименование прошло неудачно, то возвращается false
* `delete()` - удаляет файл. Также можно удалить пустой каталог

--- 

<a name="random"></a>

# Класс Random

### Методы

```java
// Конуструкторы
Random()
Random(long seed) // начальное число

// Методы
synchronized void setSeeD(long seed) // устанавливает начальное значение
long nextLong() // возвращает следующее случайное значение типа long
int nextInt(int n) // возвращает следующее случайное значение типа int в диапазоне от 0 до n

// Генерация в определенном интервале
int i = random.nextInt(max - min + 1) 
```

---

# RegEx - Regular Expressions - Регулярные Выражения
<a name="RegEx"></a>
- [Поиск любого символа](#RegEx1) <br>
- [Поиск по набору символов](#RegEx2) <br>
- [Перечисление вариантов](#RegEx3) <br>
- [Метасимволы](#RegEx4) <br>
- [Квантификаторы (количество повторений)](#RegEx5) <br>
- [Позиция внутри строки (Поиск конкретного слова)](#RegEx6) <br>


**Регулярные выражения** — это механизм для поиска и замены текста путем проверки строк на соответствие заданному шаблону.

> Регулярные выражения регистрозависимые, находят первое вхождение.

<br>

<a name="RegEx1"></a>

## Поиск любого символа

 `Точка .` заменяет **один любой символ**. Пример: `А.я` найдет Ася, Аня, А9я, А&я. Для того, чтобы найти именно точку, ее надо экранировать `\.` Пример: `\.txt` найдет file.txt, notes.txt, aw&&d1aw23d.txt

 <br>

<a name="RegEx2"></a>

## Поиск по набору символов

### Квадратные скобки `[диапазон значений]` отвечают за **символ из допустимого значения**

- [нл] — только «н» и «л»

- [а-я] — все русские буквы в нижнем регистре от «а» до «я» (кроме «ё»)

- [А-Я]    — все заглавные русские буквы

- [А-Яа-яЁё]  — все русские буквы

- [a-z]  — латиница мелким шрифтом

- [a-zA-Z]  — все английские буквы

- [0-9]  — любая цифра

- [В-Ю]   — буквы от «В» до «Ю» (да, диапазон — это не только от А до Я)

- [А-ГО-Р]   — буквы от «А» до «Г» и от «О» до «Р»

> При перечислении возможных вариантов, разделители между ними не ставятся!

- [абв] — только «а», «б» или «в»

- [а б в] — «а», «б», «в», или пробел (что может привести к нежелательному результату)

- [а, б, в] — «а», «б», «в», пробел или запятая

>[1-31] диапазон от 1 до 3 и число 1. Дефис видет только по одному символу с каждой стороны

### `Знак ^` внутри [] означает исключение

- [^0-9]  — любой символ, кроме цифр

- [^ёЁ]  — любой символ, кроме буквы «ё»

- [^а-в8]  — любой символ, кроме букв «а», «б», «в» и цифры 8

**Пример:** найти все txt файлы, кроме разбитых на кусочки — заканчивающихся на цифру: `[^0-9]\.txt`

Для **поиска скобок** используется экранирование

```
Regex: fruits\[[0-9]\]

Найдет:

fruits[0] = “апельсин”;

fruits[1] = “яблоко”;

fruits[9] = “лимон”;

Не найдет:

fruits[10] = “банан”;

fruits[325] = “ абрикос ”;
```

<br>

<a name="RegEx3"></a>

## Перечисление вариантов

### `Символ |` используется для перечисления слов

```
Regex: Оля|Олечка|Котик

Найдет:

Оля

Олечка

Котик

Не найдет:

Оленька

Котенка
```

### **Пример:** Регулярное выражение для парсинга даты (день.месяц)
`0[1-9]|[12][0-9]|3[01]\.0[1-9]|1[0-2]`

Если **перебор идет в середине слова**, он берется в круглые `скобки ()`

```
Regex: А(нн|лл|лин|нтонин)а

Найдет:

Анна

Алла

Алина

Антонина
```

> `[]` Используется для указания допустимых значений дл одного символа, `|` для нескольких символов или целого слова

<br>

<a name="RegEx4"></a>

## Метасимволы
|Символ|Пояснение|
|---|---|
|\d    |Цифровой символ|
|\D|Нецифровой символ|
|\s|Пробельный символ|
|\w    |Буквенный или цифровой символ или знак подчёркивания|
|\W|Любой символ, кроме буквенного или цифрового символа или знака подчёркивания|
|Пробел|Любой символ|

Класс символов - еще один способ задания диапазонов

|Класс символов|Пояснение|
|---|---|
|[[:alnum:]]    |Буквы или цифры: [а-яА-ЯёЁa-zA-Z0-9]|
|[[:alpha:]]|Только буквы: [а-яА-ЯёЁa-zA-Z]|
|[[:digit:]]|Только цифры: [0-9]|
|[[:graph:]]    |Только отображаемые символы (пробелы, служебные знаки и т. д. не учитываются)|
|[[:print:]]|Отображаемые символы и пробелы|
|[[:space:]]|Пробельные символы [ \f\n\r\t\v]|
|[[:punct:]]|Знаки пунктуации: ! " # $ % & ' ( ) * + , \ -. / : ; < = > ? @ [ ] ^ _ ` { | }|
|[[:word:]]|Буквенный или цифровой символ или знак подчёркивания: [а-яА-ЯёЁa-zA-Z0-9_]|

<br>

<a name="RegEx5"></a>

## Квантификаторы (количество повторений)

|Квантификатор|Число повторений|
|-|-|
|?|Ноль или одно|
|*|Ноль или более|
|+|Один или более|

```
Regex: \w+@\w+\.\w+

Найдет:

test@mail.ru

olga31@gmail.com

pupsik_99_and_slonik_33_and_mikky_87_and_kotik_28@yandex.megatron
```

Чтобы указать конкретно количество повторений, их надо записать внутри фигурных скобок

|Квантификатор|Число повторений|
|-|-|
|{n}|Ровно n раз|
|{m,n}|От m до n включительно|
|{m,}|Не менее m|
|{,n}|Не более n|

> Квантификатор применяется к последнему символу!
```
Regex: data{2}

Найдет: dataa

Не найдет: datadata
```

> Или группе символов, если они взяты в круглые скобки:
```
Regex: (data){2}

Найдет: datadata

Не найдет: dataa
```

<br>

<a name="RegEx6"></a>

## Позиция внутри строки (Поиск конкретного слова)

|Символ|Значение|
|-|-|
|\b|Граница слова|
|\B|Не граница слова|
|^|Начало текста (строки)|
|$|Конец текста (строки)|

`\b` обозначает **границу слова**
```
Regex: \bарка\b

Найдет:

арка

Не найдет:

чарка

аркан

баварка

знахарка
```

Если использовать метасимвол `\B`, он найдем нам **НЕ-границу слова**:
```
Regex: \Bакр\B

Найдет:

закройка

Не найдет:

акр

акрил
```

Если мы хотим **найти конкретную фразу**, а не слово, то используем следующие спецсимволы:

`^` — начало текста (строки)

`$` — конец текста (строки)

Если использовать их, мы будем уверены, что в наш текст не закралось ничего лишнего:

```
Regex: ^Я нашел!$

Найдет:

Я нашел!

Не найдет:

Смотри! Я нашел!

Я нашел! Посмотри!
```
<br>

Источник: https://habr.com/ru/post/545150/
<br> [В начало](#Begin)
---
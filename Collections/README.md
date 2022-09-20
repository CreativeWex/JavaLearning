# Коллекции
### Содержание

* [Map](#map)
  * [HashMap](#hashmap)
  * [LinkedHashMap](#linkedmap)
  * [TreeMap](#treemap)
  * [HashTable](#hashtable)
  <br><br>
* [Set](#set)
  * [HashSet](#hashset)
  * [LinkedHashSet](#linkedhashset)
  * [TreeSet](#treeset)
  * [EnumSet](#enumset)

# Map | Словарь

Интерфейс Map содержит основные реализации: `Hashmap`, `LinkedHashMap`, `Hashtable`, `TreeMap`. 

<a name="hashmap"></a>

## HashMap

Коллекция "ключ-значение", все ключи уникальны в рамках объекта **Program**.

> Облегчает поиск значения, если известен ключ - уникальный итендификатор.

### Создание словаря. Заполнение и получение значений
```java
HashMap<Integer, String> hashMap = new HashMap<>();
hashMap.put(1, "one"); // добавление элемента, если в коллекции уже есть элемент с подобным ключом, то он перезаписывается
hashMap.put(2, "two");
hashMap.put(3, "three");
hashMap.get(1); // Получение значения по ключу или null, если значение отсутствует
hashMap.get(2);
hashMap.get(3);
```
### Перебор словаря и вывод содержимого на экран
```java
for (Map.Entry entry : hashMap.entrySet()) // entrySet() - возвращает множество элементов коллекции 
{
    System.out.print("key: " + entry.getKey()); // getKey() - получить ключ сущности
    System.out.println("; value: " + entry.getValue()); // getKey() - получить значение сущности
}
```

### Конвертация Map в List
```java
// key list
List<Integer> keyList = new ArrayList<>(map.keySet());
// value list
List<String> valueList = new ArrayList<>(map.values());
// key-value list
List<Map.Entry<Integer, String>> entryList = new ArrayList<>(map.entrySet());
```

### Основные методы

```java
void clear() // очищает коллекцию
int size() // возвращает количество элементов коллекции
boolean containsKey(Object k) // возвращает true, если коллекция содержит ключ k
boolean containsValue(Object v) // возвращает true, если коллекция содержит значение v
int size() // возвращает количество элементов коллекции
replace(ключ, значение); // заменить значение элемента по ключу
remove(ключ); // удаление элемента по ключу     ч
```
<a name="linkedmap"></a>

## LinkedHashMap

**LinkedHashMap** - отображение с запоминанием порядка, в котором добавлялись элементы, разрешает перебор в порядке вставки.

```java
// ====LinkedHashMap====
System.out.println("LinkedHashMap");
LinkedHashMap <Integer, String> linkedHashMap = new LinkedHashMap<>();
linkedHashMap.put(1, "one");
linkedHashMap.put(3, "three");
linkedHashMap.put(4, "four");
linkedHashMap.put(2, "two");
for (Map.Entry entry : linkedHashMap.entrySet()) {
    System.out.println("key: " + entry.getKey() + "; value = " + entry.getValue());
}

// ====HashMap====
System.out.println("\nHashMap");
HashMap<Integer, String> hashMap = new HashMap<>();
hashMap.put(1, "one");
hashMap.put(3, "three");
hashMap.put(4, "four");
hashMap.put(2, "two");
for (Map.Entry entry : hashMap.entrySet()) {
    System.out.println("key: " + entry.getKey() + "; value = " + entry.getValue());
}

// Результат
LinkedHashMap
key: 1; value = one
key: 3; value = three
key: 4; value = four
key: 2; value = two

HashMap
key: 1; value = one
key: 2; value = two
key: 3; value = three
key: 4; value = four
```

<a name="treemap"></a>

## TreeMap

`Map` сортирующая элементы по ключу. Позволяет как создавать изначально отсортированные мапы или
сортировать уже существующие.

### Сортировка ключей существующей `Map`

```java
LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap<>(); // неотсортированная мапа
linkedHashMap.put("xxx", 5);
linkedHashMap.put("a", 3);
linkedHashMap.put("ccc", 1);
linkedHashMap.put("b", 2);
for (Map.Entry entry : linkedHashMap.entrySet()) {
    System.out.println("key: " + entry.getKey() + "; value: " + entry.getValue());
}

System.out.println("\n");

TreeMap<String, Integer> treeMap = new TreeMap<>(linkedHashMap); // Передача неотсортированной мапы для сортировки
for (Map.Entry entry : treeMap.entrySet()) {
    System.out.println("key: " + entry.getKey() + "; value: " + entry.getValue());
}

//Результат
key: xxx; value: 5
key: a; value: 3
key: ccc; value: 1
key: b; value: 2

key: a; value: 3
key: b; value: 2
key: ccc; value: 1
key: xxx; value: 5
```

<a name="hashtable"></a>

## HashTable

HashTable хранит пары ключей/значений в хэш-таблице. В качестве ключа выступает хэш-код переданного объекта, который используется
как индекс.

---
# Set | Множества

<a name="set"></a>

> Каждый элемент хранится только в одном экземпляре, разные реализации `Set` используют разный порядок хранения
элементов.

Если порядок хранения важен, применяется `TreeSet`, в котором **объекты хранятся отсортированными по возрастанию**
или `LinkedHashSet`. с **хранением элементов в порядке добавления**

<a name="hashset"></a>

## HashSet

### Создание множества и заполнение элементами
```java
HashSet<String> countries = new HashSet<>(); // Создание множества
countries.add("Россия"); // добавление элемента в множенство
countries.add("Франция"); // если такого элемента нет
countries.add("Испания"); // возвр. true - если добавился элемент иначе false
```

### Получение множества | метод `iterator()`
**iterator()** - метод позволяющий получить все множество элементов.
```java
Iterator<String> iterator = countries.iterator(); // Создание итератора
while (iterator.hasNext()) {
    System.out.println(iterator.next());
}
```

### Основные методы

```java
int size() // размер
boolean isEmpty()
boolean contains(Object o)
boolean addAll(Collection c)
Object[] toArray()
boolean remove(Object o)
boolean removeAll(Collection c)
void clear()
```

<a name="linkedhashset"></a>

## LinkedHashSet | Элементы в порядке добавления

Не добавляет новых методов. ласс поддерживает связный список элементов набора в том порядке, в котором они вставлялись.
Это позволяет организовать упорядоченную итерацию вставки в набор.

<a name="treeset"></a>

## TreeSet | Элементы отсортированные по возрастанию

```java
Random random = new Random();
SortedSet<Integer> sortedNumbers = new TreeSet<>(); //Создаие TreeSet

for (int i = 0; i < 5; i++) {
    sortedNumbers.add(random.nextInt(10));
}

// Результаты работы
Элементы отсортированы по возрастанию, размер множества меняется, так как повторяющиеся
элементы не добавляются, а игнорируются
[0, 4, 6, 9]
[0, 1, 2, 4, 8]
[2, 3, 9]
```

<a name="enumset"></a>

## EnumSet
Содержит только значения перечисления, принадлежащие к тому же типу перечисления, элементы хранятся в порядке
их сохранения. Он не позволяет пользователю добавлять значения NULL и создает исключение NullPointerException

---
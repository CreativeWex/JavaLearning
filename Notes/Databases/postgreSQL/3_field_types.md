# Типы данных
## I. Целочисленные
- smallint
- integer
- bigint

Автоматическая нумерация `integer primary key generated always as identity`

### C фиксированной точностью знаков
- numeric
- decimal.

Используются для точных вычислений, имеют общий синтаксис
```
numeric(кол-во цифр в числе, кол-во цифр после запятой)

decimal(кол-во цифр в числе, кол-во цифр после запятой)
```
### С плавающей точкой
- real
- double
- float

<br>

---
## II. Символьные

- character(n) - `char` - дополняет до n пробелами
- character varying(n) - `varchar` - не дополняет
- `text` - не имеет ограничений

Параметр n отвечает за макс. количество символов в поле

<br>

---

## III. Типы "дата/время"
- date(yyyy-mm-dd)
```
SELECT 'Sep 12, 2016'::date;
```
```
SELECT '21:15:(опционально секунды)'::time;
```
Для вычитания двух дат используется функция DATEDIFF(дата_1, дата_2), результатом которой является количество дней между дата_1 и дата_2.
Для того, чтобы выделить номер месяца из даты используется функция MONTH(дата).

Например, MONTH('2020-04-12') = 4.

Если определяется месяц для  значений столбца date_first, то используется запись MONTH(date_first)

Для того, чтобы выделить название месяца из даты используется функция MONTHNAME(дата), которая возвращает название месяца на английском языке для указанной даты. Например, MONTHNAME('2020-04-12')='April'
**DATEDIFF(начало; конец)**

---

<br>

## IV. Логический тип boolean
- Истина: TRUE, 't','true', 'y', 'yes', 'on', '1'.
- Ложь: FALSE, 'f', 'false', 'n', 'no', 'off', '0'.

---

<br>

## V. Массивы

Для объявления массива к имени типа нужно добавить квадртные скобки, указывать число элементов не обязательно.

`schedule integer[]` - пример объявления

`{1, 2, 3, 4, 5, 6}` - присвоение значения

<br>

### Основные операции

Добавление значения с помощью `конкатенации`
```
UPDATE pilots
SET schedule = schedule || 7
WHERE pilot_name = 'Boris';
```

<br>

Добавление значения в конец списка с помощью ф-ии `array_append`

```
UPDATE pilots
SET schedule = array_prepend( 1, schedule )
WHERE pilot_name = 'Pavel';
```

<br>

Добавление значения в конец списка с помощью ф-ии `array_prepend`

```
UPDATE pilots
SET schedule = array_prepend( 1, schedule )
WHERE pilot_name = 'Pavel';
```

<br>

Удаление элемента массива с помощью ф-ии `array_remove`

```
UPDATE pilots
SET schedule = array_remove( schedule, 5 )
WHERE pilot_name = 'Ivan';
```

<br>

Обащение к элементам массива `по индексам`

```
UPDATE pilots
SET schedule[ 1 ] = 2, schedule[ 2 ] = 3
WHERE pilot_name = 'Petr';
```
---

<br>

## VI. JSON

Существует 2 типа - json и `jsonb`, различющиеся в основном, в быстродействии

json - быстрая вставка строк, медленное последующее обращение, сохраняет порядок следования ключей. jsonb противоположен.

```
CREATE TABLE pilot_hobbies
(
    pilot_name text,
    hobbies jsonb
);

INSERT INTO pilot_hobbies
    VALUES ( 'Ivan',
'{ "sports": [ "ôóòáîë", "ïëàâàíèå" ],
"home_lib": true, "trips": 3
}'::jsonb
),
( 'Petr',
'{ "sports": [ "òåííèñ", "ïëàâàíèå" ],
"home_lib": true, "trips": 2
}'::jsonb
),
( 'Pavel',
'{ "sports": [ "ïëàâàíèå" ],
"home_lib": false, "trips": 4
}'::jsonb
),
( 'Boris',
'{ "sports": [ "ôóòáîë", "ïëàâàíèå", "òåííèñ" ],
"home_lib": true, "trips": 0
}'::jsonb
);
```

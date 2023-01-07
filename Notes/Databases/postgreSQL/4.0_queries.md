# Запросы
Порядок команд:
+ SELECT
+ CASE
+ FROM
+ WHERE
+ ORDER BY
+ LIMIT
+ OFFSET

# Основные команды
`SELECT` - Список SELECT (между ключевыми словами SELECT и FROM)

`SELECT DISCTINCT` - неповторяющиеся значения

`FROM` - перечисляются одна или несколько таблиц, служащих источниками данных для SELECT

`LIMIT число` - ограничить число строк в выборке 

`OFFSET число` - пропустить строки в выборке. OFFSET 4 - пропуск 4 строк, запрос выводит строки, начиная с 5.

# Принадлежность

`столбец AS столбец` - присвоение имени столбцу

**SELECT name AS first** - переименовывание столбца

**SELECT range, range/1.609 AS miles** - первый столбец км, второй - мили

`ORDER BY столбец` - сортирует значения по столбцу. ORDER BY столбец DESC; - сортировка по убыванию

<br>

## Условие выражения
```
CASE WHEN условие THEN выражение
    [ WHEN ... ]
    [ ELSE выражение ]
END
```
Пример
```
SELECT model, range,
    CASE WHEN range < 2000 THEN 'Ближнемагистральный'
         WHEN range < 5000 THEN 'Среднемагистральный'
         ELSE 'Дальнемагистральный'
END AS type
FROM aircrafts
ORDER BY model;
```
IF(условие, выражение, иначе)

# Источики

https://postgrespro.ru/docs/postgresql/9.6/sql-select

https://postgrespro.ru/docs/postgresql/9.6/functions-matching
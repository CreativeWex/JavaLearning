# Задание IF | CASE

При анализе продаж книг выяснилось, что наибольшей популярностью пользуются книги Михаила Булгакова, на втором месте книги Сергея Есенина. Исходя из этого решили поднять цену книг Булгакова на 10%, а цену книг Есенина - на 5%. Написать запрос, куда включить автора, название книги и новую цену, последний столбец назвать new_price. Значение округлить до двух знаков после запятой.

## Структура таблицы
```
+---------+-----------------------+------------------+--------+--------+
| book_id | title                 | author           | price  | amount |
+---------+-----------------------+------------------+--------+--------+
| 1       | Мастер и Маргарита    | Булгаков М.А.    | 670.99 | 3      |
| 2       | Белая гвардия         | Булгаков М.А.    | 540.50 | 5      |
| 3       | Идиот                 | Достоевский Ф.М. | 460.00 | 10     |
| 4       | Братья Карамазовы     | Достоевский Ф.М. | 799.01 | 2      |
| 5       | Стихотворения и поэмы | Есенин С.А.      | 650.00 | 15     |
+---------+-----------------------+------------------+--------+--------+
```
## Решение
```
SELECT author, title,
ROUND(IF (author = 'Булгаков М.А.',price + price * 10/100, IF (author = 'Есенин С.А.', price + price * 5/100, price)),2) AS new_price FROM book;

+------------------+-----------------------+-----------+
| author           | title                 | new_price |
+------------------+-----------------------+-----------+
| Булгаков М.А.    | Мастер и Маргарита    | 738.09    |
| Булгаков М.А.    | Белая гвардия         | 594.55    |
| Достоевский Ф.М. | Идиот                 | 460.00    |
| Достоевский Ф.М. | Братья Карамазовы     | 799.01    |
| Есенин С.А.      | Стихотворения и поэмы | 682.50    |
+------------------+-----------------------+-----------+
```

<br>

# Задание BETWEEN | IN

Вывести название и авторов тех книг, цены которых принадлежат интервалу от 540.50 до 800 (включая границы),  а количество или 2, или 3, или 5, или 7 .

## Структура таблицы
```
+---------+-----------------------+------------------+--------+--------+
| book_id | title                 | author           | price  | amount |
+---------+-----------------------+------------------+--------+--------+
| 1       | Мастер и Маргарита    | Булгаков М.А.    | 670.99 | 3      |
| 2       | Белая гвардия         | Булгаков М.А.    | 540.50 | 5      |
| 3       | Идиот                 | Достоевский Ф.М. | 460.00 | 10     |
| 4       | Братья Карамазовы     | Достоевский Ф.М. | 799.01 | 2      |
| 5       | Стихотворения и поэмы | Есенин С.А.      | 650.00 | 15     |
+---------+-----------------------+------------------+--------+--------+
```
## Решение
```
SELECT title, author FROM book WHERE price BETWEEN 540.50 AND 800 AND amount IN (2, 3, 5, 7);

+--------------------+------------------+
| title              | author           |
+--------------------+------------------+
| Мастер и Маргарита | Булгаков М.А.    |
| Белая гвардия      | Булгаков М.А.    |
| Братья Карамазовы  | Достоевский Ф.М. |
+--------------------+------------------+
```

<br>

# Задание Групповые функции | GROUP BY

Для каждого автора вычислить суммарную стоимость книг S (имя столбца Стоимость), а также вычислить налог на добавленную стоимость  для полученных сумм (имя столбца НДС ) , который включен в стоимость и составляет k = 18%,  а также стоимость книг  (Стоимость_без_НДС) без него. Значения округлить до двух знаков после запятой. В запросе для расчета НДС(tax)  и Стоимости без НДС(S_without_tax) использовать следующие формулы: tax = (s * k/100)/(1 + k/100); s_without_tax = s/(1+k/18)

## Структура таблицы
```
+---------+-----------------------+------------------+--------+--------+
| book_id | title                 | author           | price  | amount |
+---------+-----------------------+------------------+--------+--------+
| 1       | Мастер и Маргарита    | Булгаков М.А.    | 670.99 | 3      |
| 2       | Белая гвардия         | Булгаков М.А.    | 540.50 | 5      |
| 3       | Идиот                 | Достоевский Ф.М. | 460.00 | 10     |
| 4       | Братья Карамазовы     | Достоевский Ф.М. | 799.01 | 3      |
| 5       | Игрок                 | Достоевский Ф.М. | 480.50 | 10     |
| 6       | Стихотворения и поэмы | Есенин С.А.      | 650.00 | 15     |
+---------+-----------------------+------------------+--------+--------+
```
## Решение
```
SELECT
author,
SUM(price * amount) AS 'Стоимость',
ROUND((SUM(price * amount) * 18/100)/(1+18/100),2) AS 'НДС',
ROUND(SUM(price * amount)/(1+18/100),2) AS 'Стоимость_без_НДС'
FROM book
GROUP BY author;

+------------------+-----------+---------+-------------------+
| author           | Стоимость | НДС     | Стоимость_без_НДС |
+------------------+-----------+---------+-------------------+
| Булгаков М.А.    | 4715.47   | 719.31  | 3996.16           |
| Достоевский Ф.М. | 11802.03  | 1800.31 | 10001.72          |
| Есенин С.А.      | 9750.00   | 1487.29 | 8262.71           |
+------------------+-----------+---------+-------------------+
```

<br>

# Задание Групповые функции | HAVING

Посчитать стоимость всех экземпляров каждого автора без учета книг «Идиот» и «Белая гвардия». В результат включить только тех авторов, у которых суммарная стоимость книг (без учета книг «Идиот» и «Белая гвардия») более 5000 руб. Вычисляемый столбец назвать Стоимость. Результат отсортировать по убыванию стоимости.

## Структура таблицы
```
+---------+-----------------------+------------------+--------+--------+
| book_id | title                 | author           | price  | amount |
+---------+-----------------------+------------------+--------+--------+
| 1       | Мастер и Маргарита    | Булгаков М.А.    | 670.99 | 3      |
| 2       | Белая гвардия         | Булгаков М.А.    | 540.50 | 5      |
| 3       | Идиот                 | Достоевский Ф.М. | 460.00 | 10     |
| 4       | Братья Карамазовы     | Достоевский Ф.М. | 799.01 | 3      |
| 5       | Игрок                 | Достоевский Ф.М. | 480.50 | 10     |
| 6       | Стихотворения и поэмы | Есенин С.А.      | 650.00 | 15     |
+---------+-----------------------+------------------+--------+--------+
```
## Решение
```
SELECT
    author,
    SUM(price*amount) AS 'Стоимость'
FROM
    book
WHERE
    title NOT IN ('Белая гвардия', 'Идиот')
GROUP BY
    author 
HAVING
    SUM(price*amount) > 5000
ORDER BY
    SUM(price*amount) DESC;

+------------------+-----------+
| author           | Стоимость |
+------------------+-----------+
| Есенин С.А.      | 9750.00   |
| Достоевский Ф.М. | 7202.03   |
+------------------+-----------+
```

<br>

# Задание IF | CASE

При анализе продаж книг выяснилось, что наибольшей популярностью пользуются книги Михаила Булгакова, на втором месте книги Сергея Есенина. Исходя из этого решили поднять цену книг Булгакова на 10%, а цену книг Есенина - на 5%. Написать запрос, куда включить автора, название книги и новую цену, последний столбец назвать new_price. Значение округлить до двух знаков после запятой.

## Структура таблицы
```
+---------+-----------------------+------------------+--------+--------+
| book_id | title                 | author           | price  | amount |
+---------+-----------------------+------------------+--------+--------+
| 1       | Мастер и Маргарита    | Булгаков М.А.    | 670.99 | 3      |
| 2       | Белая гвардия         | Булгаков М.А.    | 540.50 | 5      |
| 3       | Идиот                 | Достоевский Ф.М. | 460.00 | 10     |
| 4       | Братья Карамазовы     | Достоевский Ф.М. | 799.01 | 2      |
| 5       | Стихотворения и поэмы | Есенин С.А.      | 650.00 | 15     |
+---------+-----------------------+------------------+--------+--------+
```
## Решение
```
SELECT author, title,
ROUND(IF (author = 'Булгаков М.А.',price + price * 10/100, IF (author = 'Есенин С.А.', price + price * 5/100, price)),2) AS new_price FROM book;

+------------------+-----------------------+-----------+
| author           | title                 | new_price |
+------------------+-----------------------+-----------+
| Булгаков М.А.    | Мастер и Маргарита    | 738.09    |
| Булгаков М.А.    | Белая гвардия         | 594.55    |
| Достоевский Ф.М. | Идиот                 | 460.00    |
| Достоевский Ф.М. | Братья Карамазовы     | 799.01    |
| Есенин С.А.      | Стихотворения и поэмы | 682.50    |
+------------------+-----------------------+-----------+
```

<br>

# Задание Вложенные запросы | IN 

Вывести информацию (автора, книгу и количество) о тех книгах, количество экземпляров которых в таблице book не дублируется.

В соответствии с этой таблицей:

- количество экземпляров книг "Мастер и Маргарита" и "Братья Карамазовы" одинаково и равно 3 (так как число 3 встречается в таблице два раза, книги с таким количеством не подходят под условие);
- количество экземпляров книг "Идиот" и "Игрок" тоже одинаково и равно 10 (не подходят под условие);
- количество экземпляров книги "Белая гвардия равно" 5, при этом в таблице нет других книг, количество экземпляров которых равно 5, следовательно, эта книга подходит под условие задачи (так как количество экземпляров 5 в таблице не дублируется);
- количество экземпляров книги "Стихотворение и поэмы"  - 15, в таблице нет других книг, количество экземпляров которых тоже равно 15, следовательно, и эта книга подходит под условие.

Таким образом, необходимо вывести те строки таблицы, у которых числа в столбце amount не повторяются.

## Структура таблицы
```
+---------+-----------------------+------------------+--------+--------+
| book_id | title                 | author           | price  | amount |
+---------+-----------------------+------------------+--------+--------+
| 1       | Мастер и Маргарита    | Булгаков М.А.    | 670.99 | 3      |
| 2       | Белая гвардия         | Булгаков М.А.    | 540.50 | 5      |
| 3       | Идиот                 | Достоевский Ф.М. | 460.00 | 10     |
| 4       | Братья Карамазовы     | Достоевский Ф.М. | 799.01 | 3      |
| 5       | Игрок                 | Достоевский Ф.М. | 480.50 | 10     |
| 6       | Стихотворения и поэмы | Есенин С.А.      | 650.00 | 15     |
+---------+-----------------------+------------------+--------+--------+
```
## Решение
```
SELECT author, title, amount
FROM book
WHERE amount IN(
                SELECT amount FROM book
                GROUP BY amount
                HAVING COUNT(amount) = 1
   );

+---------------+-----------------------+--------+
| author        | title                 | amount |
+---------------+-----------------------+--------+
| Булгаков М.А. | Белая гвардия         | 5      |
| Есенин С.А.   | Стихотворения и поэмы | 15     |
+---------------+-----------------------+--------+
```

<br>

# Задание Вложенные запросы | IN 

Вывести фамилию с инициалами и общую сумму суточных, полученных за все командировки для тех сотрудников, которые были в командировках больше чем 3 раза, в отсортированном по убыванию сумм суточных виде. Последний столбец назвать Сумма.

## Структура таблицы
```
+---------+---------------+-----------------+----------+------------+------------+
| trip_id | name          | city            | per_diem | date_first | date_last  |
+---------+---------------+-----------------+----------+------------+------------+
| 1       | Баранов П.Е.  | Москва          | 700.00   | 2020-01-12 | 2020-01-17 |
| 2       | Абрамова К.А. | Владивосток     | 450.00   | 2020-01-14 | 2020-01-27 |
| 3       | Семенов И.В.  | Москва          | 700.00   | 2020-01-23 | 2020-01-31 |
| 4       | Ильиных Г.Р.  | Владивосток     | 450.00   | 2020-01-12 | 2020-03-02 |
| 5       | Колесов С.П.  | Москва          | 700.00   | 2020-02-01 | 2020-02-06 |
| 6       | Баранов П.Е.  | Москва          | 700.00   | 2020-02-14 | 2020-02-22 |
| 7       | Абрамова К.А. | Москва          | 700.00   | 2020-02-23 | 2020-03-01 |
| 8       | Лебедев Т.К.  | Москва          | 700.00   | 2020-03-03 | 2020-03-06 |
| 9       | Колесов С.П.  | Новосибирск     | 450.00   | 2020-02-27 | 2020-03-12 |
| 10      | Семенов И.В.  | Санкт-Петербург | 700.00   | 2020-03-29 | 2020-04-05 |
| 11      | Абрамова К.А. | Москва          | 700.00   | 2020-04-06 | 2020-04-14 |
| 12      | Баранов П.Е.  | Новосибирск     | 450.00   | 2020-04-18 | 2020-05-04 |
| 13      | Лебедев Т.К.  | Томск           | 450.00   | 2020-05-20 | 2020-05-31 |
| 14      | Семенов И.В.  | Санкт-Петербург | 700.00   | 2020-06-01 | 2020-06-03 |
| 15      | Абрамова К.А. | Санкт-Петербург | 700.00   | 2020-05-28 | 2020-06-04 |
| 16      | Федорова А.Ю. | Новосибирск     | 450.00   | 2020-05-25 | 2020-06-04 |
| 17      | Колесов С.П.  | Новосибирск     | 450.00   | 2020-06-03 | 2020-06-12 |
| 18      | Федорова А.Ю. | Томск           | 450.00   | 2020-06-20 | 2020-06-26 |
| 19      | Абрамова К.А. | Владивосток     | 450.00   | 2020-07-02 | 2020-07-13 |
| 20      | Баранов П.Е.  | Воронеж         | 450.00   | 2020-07-19 | 2020-07-25 |
+---------+---------------+-----------------+----------+------------+------------+
```
## Решение
```
SELECT name, SUM((DATEDIFF(date_last, date_first) + 1) * per_diem) AS 'Сумма' FROM trip
WHERE name IN (SELECT name FROM trip GROUP BY name HAVING COUNT(*) > 3)
GROUP BY name
ORDER BY Сумма DESC;

+---------------+----------+
| name          | Сумма    |
+---------------+----------+
| Абрамова К.А. | 29200.00 |
| Баранов П.Е.  | 21300.00 |
+---------------+----------+
```

<br>

# Задание АЛИАС | AS

Занести в таблицу fine суммы штрафов, которые должен оплатить водитель, в соответствии с данными из таблицы traffic_violation. При этом суммы заносить только в пустые поля столбца  sum_fine.
## Структура таблицы
```
fine
+---------------+--------+------------------------------+----------+----------------+--------------+
| name          | number | violation                    | sum_fine | date_violation | date_payment | 
|               | _plate |                              |          |                |              | 
+---------------+--------+------------------------------+----------+----------------+--------------+
| Баранов П.Е.  | Р523ВТ | Превышение скорости(от 40... | 500.00   | 2020-01-12     | 2020-01-17   |
| Абрамова К.А. | О111АВ | Проезд на запрещающий сигнал | 1000.00  | 2020-01-14     | 2020-02-27   |
| Яковлев Г.Р.  | Т330ТТ | Превышение скорости(от 20... | 500.00   | 2020-01-23     | 2020-02-23   |
| Яковлев Г.Р.  | М701АА | Превышение скорости(от 20... | NULL     | 2020-01-12     | NULL         |
| Колесов С.П.  | К892АХ | Превышение скорости(от 20... | NULL     | 2020-02-01     | NULL         |
| Баранов П.Е.  | Р523ВТ | Превышение скорости(от 40... | NULL     | 2020-02-14     | NULL         |
| Абрамова К.А. | О111АВ | Проезд на запрещающий сигн...| NULL     | 2020-02-23     | NULL         |
| Яковлев Г.Р.  | Т330ТТ | Проезд на запрещающий сигн...| NULL     | 2020-03-03     | NULL         |
+---------------+--------+------------------------------+----------+----------------+--------------+

traffic_violation
+--------------+----------------------------------+----------+
| violation_id | violation                        | sum_fine |
+--------------+----------------------------------+----------+
| 1            | Превышение скорости(от 20 до 40) | 500.00   |
| 2            | Превышение скорости(от 40 до 60) | 1000.00  |
| 3            | Проезд на запрещающий сигнал     | 1000.00  |
+--------------+----------------------------------+----------+
```
## Решение
```
UPDATE fine AS f, traffic_violation AS tv
SET f.sum_fine = tv.sum_fine
WHERE f.violation = tv.violation AND f.sum_fine IS NULL;

+---------------+--------------+----------------------------------+--------------------------+
| name          | number_plate | violation                        | description              |
+---------------+--------------+----------------------------------+--------------------------+
| Баранов П.Е.  | Р523ВТ       | Превышение скорости(от 40 до 60) | Уменьшенная сумма штрафа |
| Абрамова К.А. | О111АВ       | Проезд на запрещающий сигнал     | Стандартная сумма штрафа |
| Яковлев Г.Р.  | Т330ТТ       | Превышение скорости(от 20 до 40) | Стандартная сумма штрафа |
+---------------+--------------+----------------------------------+--------------------------+
```

<br>

# Задание АЛИАС | AS

Вывести фамилию, номер машины и нарушение только для тех водителей, которые на одной машине нарушили одно и то же правило   два и более раз. При этом учитывать все нарушения, независимо от того оплачены они или нет. В таблице fine увеличить в два раза сумму неоплаченных штрафов для отобранных на предыдущем шаге записей. 
## Структура таблицы
```
fine
+---------------+--------+------------------------------+----------+----------------+--------------+
| name          | number | violation                    | sum_fine | date_violation | date_payment |
|               | _plate |                              |          |                |              |
+---------------+--------+------------------------------+----------+----------------+--------------+
| Баранов П.Е.  | Р523ВТ | Превышение скорости(от 40... | 500.00   | 2020-01-12     | 2020-01-17   |
| Абрамова К.А. | О111АВ | Проезд на запрещающий сигнал | 1000.00  | 2020-01-14     | 2020-02-27   |
| Яковлев Г.Р.  | Т330ТТ | Превышение скорости(от 20... | 500.00   | 2020-01-23     | 2020-02-23   |
| Яковлев Г.Р.  | М701АА | Превышение скорости(от 20... | 500.00   | 2020-01-12     | NULL         |
| Колесов С.П.  | К892АХ | Превышение скорости(от 20... | 500.00   | 2020-02-01     | NULL         |
| Баранов П.Е.  | Р523ВТ | Превышение скорости(от 40... | 1000.00  | 2020-02-14     | NULL         |
| Абрамова К.А. | О111АВ | Проезд на запрещающий сигн...| 1000.00  | 2020-02-23     | NULL         |
| Яковлев Г.Р.  | Т330ТТ | Проезд на запрещающий сигн...| 1000.00  | 2020-03-03     | NULL         |
+---------------+--------+------------------------------+----------+----------------+--------------+
```
## Решение
```
UPDATE fine AS f,
       (
           SELECT name, number_plate, violation
            FROM fine
            GROUP BY name, number_plate, violation
            HAVING COUNT(*) > 1
       ) query_in
       
SET sum_fine = sum_fine * 2
WHERE
    f.date_payment IS NULL AND
    f.name = query_in.name AND
    f.number_plate = query_in.number_plate AND
    f.violation = f.violation;

+---------------+--------------+----------------------------------+
| name          | number_plate | violation                        |
+---------------+--------------+----------------------------------+
| Абрамова К.А. | О111АВ       | Проезд на запрещающий сигнал     |
| Баранов П.Е.  | Р523ВТ       | Превышение скорости(от 40 до 60) |
+---------------+--------------+----------------------------------+

```
<br>
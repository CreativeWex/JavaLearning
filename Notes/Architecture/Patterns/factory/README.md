# factory - Фабрика - Фабричный метод

**Вкратце**: Это способ делегирования логики создания объектов (instantiation logic) дочерним классам.
Суть способа заключается в том, что объекты создаются посредством вызова не конструктора, а генерирующего метода,
определенного в интерфейсе и реализованного дочерними классами.

**Аналогия**: Одна кадровичка не в силах провести собеседования со всеми кандидатами на все должности. В зависимости от вакансии она может делегировать разные этапы собеседований разным сотрудникам.

**Пример использования:** заранее неизвестно, экземпляры какого класса нужно будет создавать.

**Реализация:**
1. Реализация отдельных нужных нам объектов. (Классы `JavaDeveloper`, `CppDeveloper` и `PythonDeveloper`).
2. Реализация интерфейса с генерирующим методом (Интерфейс `DeveloperFactory`).
3. Реализация классов-фабрик, наследующих интерфейс и возвращающих свежесозданный объект
```java
public class CppDeveloperFactory implements DeveloperFactory{
    @Override
    public Developer createDeveloper() {
        return new CppDeveloper();
    }
}
```
4. Реализация в коде статического метода, который в зависимости от условия создает фабрику
```java
static DeveloperFactory createDeveloperBySpecialty(String specialty) {
        if (specialty.equalsIgnoreCase("java")) {
            return new JavaDeveloperFactory();
        } else if (specialty.equalsIgnoreCase("cpp")) {
            return new CppDeveloperFactory();
        } else if (specialty.equalsIgnoreCase("python")) {
            return new PythonDeveloperFabric();
        } else {
            throw new RuntimeException(specialty + " is unknown specialty");
        }
    }
```
5. Создание в коде фабрики и ее продукта
```java
        DeveloperFactory developerFactory = createDeveloperBySpecialty(scanner.nextLine());
        Developer developer = developerFactory.createDeveloper();
```
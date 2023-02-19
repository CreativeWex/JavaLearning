# Spring MVC

* [DispatcherServlet](#DispatcherServlet)
* [Controller](#Controller)
  * [Mapping](#Mapping)
  * [@RequestMapping](#RequestMapping)
  * [@RequestParam](#RequestMapping)
* [Model](#Model)
  * [@ModelAttribute](#ModelAttribute)
* [View](#View)
* [Thymeleaf](#thymeleaf)
  * [Синтаксис](#th:synt)
  * [Форма | th:method | th:action | th:object](#th:form)
  * [Перебор Элементов | th:each](#th:each)

Предполагает разработку web-приложений с использованием архитектуры Model - View - Controller.



## MVC | Model - View - Controller

- `Model` - логика работы с данными (обращение к БД)
- `View` - логика представления, интерфейс
- `Controller` - логика навигации, обработки запросов

<a name = "DispatcherServlet"></a>

---

## DispatcherServlet

Входная точка Spring MVC приложения. Находится между `Controller`
и `HTTP Request`

HTTP запрос от пользователя:
1. Приходит на сервер. Сервер обрабатывает запрос и передает
его на Spring MVC приложение.
2. Запрос попадает в DispatcherServlet.
3. DispatcherServlet отправляет запрос на правильный контроллер.

<a name = "Controller"></a>

---

# Controller

- `@Controller`
- Обрабатывает запросы от пользователя
- Обменивается данными с моделью
- Показывает пользователю правильное представление
- Переадресовывает пользователя на другие страницы

`@Controller` - тот же `@Component`, но с дополнительными возможностями.
В контроллере может быть несколько методов, которые возвращают строки - названия представлений, которые нужно показать
пользователю. Обычно каждый метод соответствует одному URL'у 

<a name = "Mapping"></a>

### Маппинги

Связывают метод контроллера с адресом, по которому к этому можно к этому методу обратиться (из браузера, например)

```java
@Controller
public class HelloController {
    @GetMapping("/hello-world")
    public String sayHello() {return "hello_world"; }
}
```

<a name = "RequestMapping"></a>


#### @RequestMapping

Используется для того, чтобы указать, что **ВСЕ МЕТОДЫ** класса в начале адреса имели префикс

```java
@Controller
@RequestMapping("/people"
public class PersonController {
    @GetMapping("/new") // обрабатывает запросы /people/new
    public String new() { return "new"; }

      @GetMapping("/old") // обрабатывает запросы /people/old
      public String old() { return "old"; }
}
```

<a name = "RequestParam"></a>

#### @RequestParam

Обработка параметров GET-запроса
```java
    @GetMapping("/hello")
    public String helloPage(@RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "surname", required = false) String surname) {

        System.out.println("Hello, " + name + " " + surname);

        return "first/hello";
    }
    
//    URL: localhost/hello?name=nikita&surname=bereznev
//    Result: Hello, nikita bereznev
```

### HTTP

[Примеры запросов](../../Architecture/REST/README.md/#examples)

<a name = "HTTP"></a>

#### HTTP-Запросы(методы)

| HTTP метод | Аннотация      | Описание                                                                                                                                                                                                                                                                                                                                                                                                                                                                       |
|------------|----------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| GET        | @GetMapping    | Самый используемый запрос. Получение данных с удаленного сервера (переход по адресу, ссылке, поисковой запрос и т.д.). Ничего не меняет на сервере<br/><br/> Параметры нужны для передачи информации от клиента серверу во время запроса. Передаются в самом URL после знака **?** в формате **ключ=значение**. Несколько параметров разделяются знаком **&**.<br/> <br/>https://vk.com/audios202613076?q=lilpeep&section=playlist Параметры (**?q=lilpeep&section=playlist**) |
| POST       | @PostMapping   | Добавить на сервер (отправка данных с веб-форм, создать учетную запись, твитнуть, загрузить фото, добавить запись в сообщество). Параметры закодированы в теле запроса.                                                                                                                                                                                                                                                                                                        |
| PUT        | @PutMapping    | Создаёт новый ресурс или заменяет представление целевого ресурса, данными представленными в теле запроса                                                                                                                                                                                                                                                                                                                                                                       |
| DELETE     | @DeleteMapping | Удаляет указанный ресурс                                                                                                                                                                                                                                                                                                                                                                                                                                                       |
| PATCH      | @PatchMapping  | Частично изменяет ресурс                                                                                                                                                                                                                                                                                                                                                                                                                                                       |

<a name = "ServerAnswers"></a>

#### HTTP-Ответы

* 200 - все ОК
* 3хх - редирект
* 4хх - ошибка клиента (404 - клиент пытается попасть на несущестующую страницу)
* 5хх - ошибка сервера (502: Bad Gateway -  сервер не смог обработать полученный запрос по техническим причинам)

---

<a name = "Model"></a>


# Model

Контейнер для данных приложения

 - Хранит в себе данные
 - Взаимодействует с бд для получения данных
 - Отдает данные контроллеру

<a name = "ModelAttribute"></a>

## @ModelAttribute

### Аннотация метода

Используется для добавления тех пар ключ-значение, которые нужны во всех моделях этого контроллера.

```java
@ModelAttribute("headerMessage")
public String populateHeaderMessage() {
    return "Welcome!";    
}

// Любая модель контроллера по умолчанию будет иметь значение "Welcome!" с ключом headerMessage
```

Может передавать в модель любой объект
```java
@ModelAttribute("messageObject")
public MessageObject populayeHeaderMessage() {
    MessageObject messageObject = new MessageObject();
    messageObject.setSomeField("Hello");
    return messageObject;
}
```

### Аннотирование аргумента метода

Создает объект с полями - данными формы и записывает объект в модель.

```java
@PostMapping()
public String create(@ModelAttribute("person") Person person) {
    //Аннотация создает объект, инициализирует поля данными с формы и записывает объект в модель с ключом "person".
    return "sucessPage";   
}
// Если в форме не будет данных, то в модель будет положен новый объект со значениями по умолчанию (0, null, и тд)
```




<a name = "View"></a>

---
## View

- Получает данные от контроллера и отображает их в браузере
- Для динамического отображения данных используются шаблонизаторы
  (`Thymeleaf`, `Freemaker`, `Velocity`)

<a name = "thymeleaf"></a>

## Thymeleaf

<a name = "th:synt"></a>

* Простые выражения:
  * **Переменная**: ${...}
  * **Выбранная переменная**: *{...}
  * **Сообщение**: #{...}
  * **Ссылка URL**: @{...}
  * **Фрагмент**: ~{...}
* Литералы/Literals:
  * **Текст**: 'one text', 'Another one!',...
  * **Число**: 0, 34, 3.0, 12.3,...
  * **Boolean**: true, false
  * **Null**: null
  * **Токены**: one, sometext, main,...
* Текст:
  * **Соединение строк**: +
  * **Подстроки**: |The name is ${name}|
* Арифметика:
  * **Binary**: +, -, *, /, %
  * **Минус (unary operator)**: -
* Boolean:
  * **Binary**: and, or
  * **Boolean отрицание (unary operator)**: !, not
* Сравнение и равенство:
  * **Сравнение**: >, <, >=, <= (gt, lt, ge, le)
  * **Равенство**: ==, != (eq, ne)
* Условные:
  * **If-then**: (if)? (then)
  * **If-then-else**: (if)? (then): (else)
  * **Default: (value) ?**: (defaultvalue)
* Специальные токены:
  * **No-Operation**: _

### Пример

Контроллер:
```java
@GetMapping("/new")
public String newPerson(Model model) {
      model.addAttribute("person", new Person()); // добавляем объект по ключу "person"
      return "people/new"; // имя представления для отображения
}
```

### Представление new.html

<a name = "th:form"></a>

#### Форма | th:method | th:action | th:object

```html
<form th:method="POST" th:action="@{/people}" th:object="${person}">

    <label for="name">Enter name: </label>
    <input type="text" th:field="*{name}" id="name"/>
    <br>

    <label for="email">Enter email: </label>
    <input type="text" th:field="*{email}" id="email">
    <br>

    <input type="submit" value="Create"/>
</form>
```
**th:method**="HTTP метод"  
**th:action**="Для какой страницы сохраняем данные"  
**th:object**="используемый объект"

<br>

<a name = "th:each"></a>

#### Перебор Элементов | th:each

```html
<div th:each="person : ${peopleList}">
    <a th:href="@{/people/{id}(id=${person.getId()})}"
       th:text="${person.getName() + ', ' +  person.getAge()}">user</a>
</div>
```

#### Форма для отправки параметров GET запроса
```html
<form action="http://foo.com" method="get">
    <div>
        <label for="say">What greeting do you want to say?</label>
        <input name="say" id="say" value="Hi">
    </div>
    <div>
        <label for="to">Who do you want to say it to?</label>
        <input name="to" id="to" value="Mom">
    </div>
    <div>
        <button>Send my greetings</button>
    </div>
</form>
```


https://habr.com/ru/post/350870/
# Spring

* Spring data
* Spring web-mvc
* Spring security

---

# Конфигурация Spring приложения. XML

## Содержание

* [DI / IoC](#diIoc)
* [Application context](#ApplicationContext)
* [Типичные шаги в работе со Spring](#SpringSteps) 
* [Способы внедрения зависимостей](#injectionMethods)
---
<a name = "diIoc"></a>

## DI / IoC

**IoC (Inversion of Control)** - Инверсия управления - архитектурный подход, при котором сущность не сама создает свои
зависимости, а зависимости подаются извне. 

Может реализовываться как механизм передачи процесса выполнения кода/программы
фреймворку. При использовании библиотеки вы сами прописываете в своем коде какой метод какого объекта вызвать, а в
случае с фреймворками — чаще всего уже фреймворк будет вызывать в нужный ему момент тот код, который вы написали. То есть, тут уже не вы управляете процессом выполнения кода/программы, а фреймворк это делает за вас. Вы передали ему управление (инверсия управления).

**Dependency Inversion** - инверсия зависимостей - попытки не делать жестких связей между вашими модулями/классами,
где один класс напрямую завязан на другой;

**Dependency Injection** - Внедрение зависимости — процесс предоставления внешней зависимости программному компоненту. 
Вместо ручного создания объектов в мейне их создает Спринг и передает их в методы по конфигурации.

---

<a name = "SpringSteps"></a>

## Типичные шаги в работе со Spring
1. Создаем Java-классы (Будущие бины);
2. Создаем и связываем бины с помощью Spring (Аннотации, XML, или Java-код);
3. При использовании, все объекты (бины) берутся из контейнера Spring

---

<a name = "ApplicationContext"></a>

## Application context

**Контекст** - набор бинов (объектов). Обращаясь к контексту можно получить нужный бин (объект) по его имени,
типу или чему-то еще.

> applicationContext.xml создается в папке resources

### Жизненный цикл бина

1. Запуск Spring приложения
2. Запускается Spring контейнер (Application Context)
3. Создается объект бина
4. В бин внедряются зависимости (Dependency Injection)
5. Вызывается указанный **init-method**, отвечающий за инициализацию ресурсов, обращение к внешним файлам, запуск БД
6. Бин готов к использованию
7. Вызывается указанный **destroy-method**, отвечающий за очищение ресурсов, закрытие потоков ввода-вывода, закрытие доступа
   к бд.
8. Остановка Spring приложения

> Для бинов со scope "prototype" Spring **не вызывает destroy метод**

Пример:
```java
<bean id="musicBean"
          class="ru.alishev.springcourse.ClassicalMusic"
          init-method="doMyInit"
          destroy-method="doMyDestroy">
    </bean>

// Методы создаются в классе бина
```

### Способы конфигурации приложения

Указать спрингу, какие именно объекты нужны для работы можно тремя способами, которые можно комбинировать
между собой:
1. **Автоматическая конфигурация** - наиболее приоритетный способ
2. При помощи **java-конфигов** (создание объектов, используя java-код)
3. При помощи **xml файлов/конфигов** - самый низкоприоритетный способ

---

<a name = "injectionMethods"></a>

## Способы внедрения зависимостей
Внедряемый бин
```java
<bean id="musicBean"
      class="org.example.ClassicalMusic">
</bean>
```

### Через конструктор (передавать в конструктор одного объекта другой)
```java
public MusicPlayer(Music music) { this.music = music;}

<bean id = "musicPlayer" class = "org.example.MusicPlayer">
<constructor-arg ref="musicBean"/> // Ссылка на объект
</bean>
```
### Через setter
Внедрение ссылки на объект
```java
public void setMusic(Music music) {
    this.music = music;
}

<bean id = "musicPlayer" class = "org.example.MusicPlayer">
<property name="music" ref="musicBean"/>
<!--        name - имя сеттера с маленькой буквы-->
</bean>
```
Внедрение простых значений
```java
private String name;
private String volume;

public void setName(String name) {    this.name = name;    }
public void setVolume(String volume) {    this.volume = volume;    }

<property name="name" value="Cant Stxp"/>
<property name="volume" value="50"/>
<!--        name - имя сеттера с маленькой буквы-->
```
Внедрение значений из файла
>Файл с расширением `.properties` в каталоге resources
```java
// musicPlayer.properties:
//musicPlayer.name=SomeName
//musicPlayer.value=70

<context:property-placeholder location="classpath:musicPlayer.properties"/>
<!--classpath: - папка resources  -->
<property name="name" value="${musicPlayer.name}"/>
<property name="volume" value="${musicPlayer.value}"/>
<!--musicPlayer - название файла конфигурации  -->
```

## Конфигурации внедрения (scope, init-method, destroy-method, factory method);

### Scope

**Scope** наборы паттернов, по которым Spring создает бины.

 - По умолчанию используется **Singleton**. Применяется когда у бина нет изменяемых состояний (stateless).
 - **Prototype**, каждый раз создающий новый объект при вызове `getBean()`. Применяется когда у бина есть изменяемые
состояния (stateful).
 - request
 - session
 - global-session

Пример
```java
class="MusicPlayer" scope="prototype">
```

##

* XML, аннотации или Java-код;
* Автоматизация (Autowiring);

---
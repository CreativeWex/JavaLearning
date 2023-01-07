# Конфигурация БД

* [Получение доступа к значениям из файла свойств](#DatabaseConfiguration)
* [Использование аннотации `@Value`](#ValueAnnotation)
* [Абстракция среды Spring](#BootConfiguration)

---

<a name="DatabaseConfiguration"></a>

# Получение доступа к значениям из файла свойств

Файл свойств по умолчанию, создаваемый `Spring Boot`, называется `application.properties` и находится в каталоге `src/main/resources/` проекта. Свойства следуют одному и тому же синтаксису ключ=значение. При инициализации проекта файл по умолчанию пуст.

Пример файла `application.properties`:

```properties
db.driver=org.postgresql.Driver
db.url=jdbc:postgresql://localhost:5432/data_base
db.username=postgres
db.password=8888
```
> Обычно файл `.properties` не публикуют (скрытый, к примеру в .gitignore).
>
> Можно создать публичный файл c такимже префиксом, названием и окончанием `.origin`, но без значений (ключ=). Он будет задавать структуру файла, которая нужна для запуска приложения.
____

<a name="ValueAnnotation"></a>

# Использование аннотации @Value

Аннотация `@Value` -это предварительно определенная аннотация, используемая для чтения значений из любых файлов свойств в пути к классам проекта.

Чтобы получить доступ к значению одного свойства из файла свойств с помощью этой аннотации, указываем имя свойства в качестве аргумента:

```Java
@Value("${db.driver}")
private String DRIVER_DB;   //org.postgresql.Driver
```
___

<a name="BootConfiguration"></a>
# Абстракция среды Spring

Другим способом доступа к значениям, определенным в `Spring Boot`, является автоматическое подключение объекта `Environment` и вызов метода `getProperty()` для доступа к значению файла свойств.

Метод `getProperty()` принимает один обязательный параметр, представляющий собой строку, содержащую имя свойства, и возвращает значение этого свойства, если оно существует. Если свойство не существует, метод вернет значение **null**.

```Java
@SpringBootApplication
public class MvcCrudApplication implements EnvironmentAware {
    private String DRIVER_DB;

    @Override
    public void setEnvironment(Environment environment) {
        this.DRIVER_DB = environment.getProperty("db.driver");
        //...
    }
}
```

Другой вариант — просто внедрить `Environment` в наш контроллер/компонент.

```Java
@SpringBootApplication
public class MvcCrudApplication {

    @Autowired
    private Environment environment;

    public void setEnvironment() {
        String DRIVER_DB = environment.getProperty("db.driver");
        //...
    }
}
```
____
<br>

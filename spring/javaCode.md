# Конфигурация Spring приложения. Аннотации + Java код

```java
AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
```

## Аннотация @Configuration

Помечает Java класс, который мы хотим использовать для конфигурации Spring приложения.

```java
@Configuration
public class SpringConfig{
}
```

Пустой конфигурационный Java класс равен по фунционалу пустому конфигурационному XML файлу

## XML теги и соответствующие аннотации

| XML                                                                    | Аннотация                                      |
|------------------------------------------------------------------------|------------------------------------------------|
| `<context:component-scan base-package="директория с компонентами"/>`   | `@ComponentScan("директория с компонентами")`  |
| `<bean/> `                                                             | `@Bean`                                        |
| `<context:property-placeholder location="classpath:файл.properties"/>` | `@PropertySource("classpath:файл.properties")` |

### Ручное внедрение зависимостей

```java
@Configuration
public class SpringConfig {
    
    @Bean
    @Scope("singleton")
    public ClassicalMusic musicBean() { // beanId = musicBean 
        return new ClassicalMusic();
    }
    
    @Bean
    public MusicPlayer musicPlayer() { // beanId = musicPlayer 
        return new MusicPlayer(musicBean()); // Внедрение созданного бина
    }
}
```
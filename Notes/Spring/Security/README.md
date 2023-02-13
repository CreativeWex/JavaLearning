# Spring Security

Зависимость
```java
<dependency>
 <groupId>org.springframework.boot</groupId>
 <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```
## Файл конфигурации

Объявляет bean-компонент `PasswordEncoder`, используемый для содания новых пользователей при аутентификации
- BCryptPasswordEncoder - применяет надежное шифрование bcrypt;
- NoOpPasswordEncoder – не применяет шифрования (для тестирования);
- StandardPasswordEncoder – применяет шифрование SHA-256 (устарел);



## InMemoryUserDetailsManager

Хранилище, в котором хранятся пользователи.

Роли хранятся в отдельном перечислении:
```java
public enum Role {
    USER,
    ADMIN;
}
```
Файл конфигурации:

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // Защита от csrf угроз
                .csrf().disable()
                .authorizeRequests()
                // Кто в какие URL имеет доступ
                .antMatchers("/").permitAll()
                // Какие методы HTTP запроса разрешены каким ролям
                .antMatchers(HttpMethod.GET, "/api/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                .antMatchers(HttpMethod.POST, "/api/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/api/**").hasRole(Role.ADMIN.name())
                // Каждый из запросов должен аутентифицироваться
                .anyRequest().authenticated().and().httpBasic();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        // Создание ролей
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("admin")
                        .password(passwordEncoder().encode("admin"))
                        .roles(Role.ADMIN.name())
                        .build(),
                User.builder()
                        .username("user")
                        .password(passwordEncoder().encode("user"))
                        .roles(Role.USER.name())
                        .build()
        );
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}

```

## Авторизация с использованием прав доступа | authorities

Каждой роли присваивается определенное количество прав.

Создание новой сущности `Permission`:
```java
public enum Permission {
    DEVELOPERS_READ("developers:read"),
    DEVELOPERS_WRITE("developers:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
```

Изменим Enum `Role`

```java
public enum Role {
    // Выдаем permission созданным ролям
    USER(Set.of(Permission.DEVELOPERS_READ)),
    ADMIN(Set.of(Permission.DEVELOPERS_READ, Permission.DEVELOPERS_WRITE));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    // Преобразовывем permission в authority
    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toSet());
    }
}
```
Меняем в файле конфигурации проверку наличия ролей на проверку наличия прав:
```java
@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // Защита от csrf угроз
            .csrf().disable()
            .authorizeRequests()
            // Кто в какие URL имеет доступ
            .antMatchers("/").permitAll()
            // Какими правами надо обладать для использования HTTP меодов
            .antMatchers(HttpMethod.GET, "/api/**").hasAuthority(Permission.DEVELOPERS_READ.getPermission())
            .antMatchers(HttpMethod.POST, "/api/**").hasAuthority(Permission.DEVELOPERS_WRITE.getPermission())
            .antMatchers(HttpMethod.DELETE, "/api/**").hasAuthority(Permission.DEVELOPERS_WRITE.getPermission())
            // Каждый из запросов должен аутентифицироваться
            .anyRequest().authenticated().and().httpBasic();
    }
```
При создании пользователей вместо ролей присваиваем права:
```java
@Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return new InMemoryUserDetailsManager(
            User.builder()
                    .username("admin")
                    .password(passwordEncoder().encode("admin"))
                    .authorities(Role.ADMIN.getAuthorities())
                    .build(),
            User.builder()
                    .username("user")
                    .password(passwordEncoder().encode("user"))
                    .authorities(Role.USER.getAuthorities())
                    .build()
        );
    }
```

## Аннотация `@PreAuthorize`

**Разрешить вызов метода только пользователю с конкретными правами.**

В контроллере определяем права для методов:
```java
@GetMapping("/{id}")
    // !
    @PreAuthorize("hasAnyAuthority('developers:read')")
    // !
    public Developer getById(@PathVariable Long id) {
        return DEVELOPERS.stream().filter(developer -> developer.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    // !
    @PreAuthorize("hasAnyAuthority('developers:write')")
    // !
    public Developer create(@RequestBody Developer developer) {
        this.DEVELOPERS.add(developer);
        return developer;
    }

    @DeleteMapping("/{id}")
    // !
    @PreAuthorize("hasAnyAuthority('developers:write')")
    // !
    public void deleteById(@PathVariable Long id) {
        this.DEVELOPERS.removeIf(developer -> developer.getId().equals(id));
    }
```

**Настройка SecurityConfig**. Добавляем специальную аннотацию, часть кода можно убрать
```java
//!
@EnableGlobalMethodSecurity(prePostEnabled = true) // Позволяет использовать @PreAuthorize
//!
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // Защита от csrf угроз
                .csrf().disable()
                .authorizeRequests()
                // Кто в какие URL имеет доступ
                .antMatchers("/").permitAll()
                // Каждый из запросов должен аутентифицироваться
                .anyRequest().authenticated().and().httpBasic();
    }
```

## Аутентификация с использованием формы ввода
Вместо `.httpBasic()` применяется `.formLogin()`
```java
     @Override
protected void configure(HttpSecurity http) throws Exception {
        http
        // Защита от csrf угроз
        .csrf().disable()
        .authorizeRequests()
        // Кто в какие URL имеет доступ
        .antMatchers("/").permitAll()
        // Каждый из запросов должен аутентифицироваться
        .anyRequest()
        .authenticated()
        .and()
        .formLogin()
        // Адрес
        .loginPage("/auth/login").permitAll()
        // Если успешно, то перенаправляемся на URL
        .defaultSuccessUrl("/auth/success")
        .and()
        //логаут
        .logout()
        //проводится по пути с методом
        .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST"))
        //после чего сессия закрывается
        .invalidateHttpSession(true)
        .clearAuthentication(true)
        .deleteCookies("JSESSIONID")
        // После логаута перенаправляемся на
        .logoutSuccessUrl("/auth/login")
        ;
        }
```
**Создание формы логина**. Метод POST отправляется на контроллер "/auth/login"
```html
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Login Customer</title>
</head>
<body>
<div class="container">
    <form class="form-signin" method="post" action="/auth/login">
        <h2 class="form-signin-heading">Login</h2>
        <p>
            <label for="username">Username</label>
            <input type="text" id="username" name="username" class="form-control" placeholder="Username" required>
        </p>
        <p>
            <label for="password">Password</label>
            <input type="password" id="password" name="password" class="form-control" placeholder="Password" required>
        </p>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
    </form>
</div>
</body>
</html>
```
Использования метода запроса GET для логаута является небезопасным. Поэтому сделаем POST
метод.

Форма для Logout
```html
<form action="/auth/logout" method="POST">
        <button type="submit">Logout</button>
    </form>
```

## Аутентификация и авторизация при помощи БД
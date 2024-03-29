# ООП

Программа считается объектно-ориентированной, только если выполнены **все три требования**:
- ООП использует в качестве основных логических конструктивных элементов объекты, а не алгоритмы;
- каждый объект является экземпляром определенного класса
- классы образуют иерархии

ООП **обязательно должно использовать** наследование, в противном случае методология будет не объектно-ориентированной, а **программированием с помощью абстрактных типов данных**.

> Согласно парадигме ООП программа состоит из объектов, обменивающихся сообщениями. Объекты могут обладать состоянием, единственный способ изменить состояние объекта - послать ему сообщение, в ответ на которое объект может изменить свое состояние.

**Класс** – это способ описания сущности, определяющий состояние и поведение, зависящее от этого состояния, а также правила для взаимодействия с данной сущностью (контракт).

>С точки зрения программирования класс можно рассматривать как набор данных (полей, атрибутов, членов класса) и функций для работы с ними (методов).
>
>С точки зрения структуры программы, класс является сложным типом данных.

**Объект** (экземпляр) – это отдельный представитель класса, имеющий конкретное состояние и поведение, полностью определяемое классом. Каждый объект имеет конкретные значения атрибутов и методы, работающие с этими значениями на основе правил, заданных в классе.

**Интерфейс** – это набор методов класса, доступных для использования. Интерфейсом класса будет являться набор всех его публичных методов в совокупности с набором публичных атрибутов. По сути, интерфейс специфицирует класс, чётко определяя все возможные действия над ним.

<a name = "OOPpluses"></a>

## Преимущества и недостатки ООП

**Преимущества**:
- Ориентированность на человеческое восприятие мира, а не на компьютерную реализацию;
- Классы позволяют абстрагироваться от деталей реализации;
- Локализация кода и данных улучшает наглядность и удобство сопровождения программного обеспечения.
  Локализация кода заключается в объединении данных и методов для работы с ними в единую
  сущность, не разнося их по всей программе как в случае с процедурным программированием.
- Возможности описать независимые от приложения части предметной области в виде набора универсальных классов, или фреймворка, который в дальнейшем будет расширен за счет добавления частей, специфичных для конкретного приложения.
- Повторное использование кода:
    - Сокращение времени на разработку;
    - Сокращение количества ошибок;
    - Упрощение использования, понимания и написания кода.

**Недостатки**:
- Документирование классов - задача более трудная, чем это было в случае процедур и модулей. Поскольку любой метод может быть переопределен, в документации должно говориться не только о том, что делает данный метод, но и о том, в каком контексте он вызывается.
- Неэффективность и неэкономное распределения памяти на этапе выполнения (по причине издержек на динамическое связывание и проверки типов на этапе выполнения).
- Излишняя универсальность. Часто содержится больше методов, чем это реально необходимо текущей программе. А поскольку лишние методы не могут быть удалены, они становятся мертвым грузом.

<a name = "OOPbinding"></a>

## Статическое и динамическое связывание

Присоединение вызова метода к телу метода называется **связыванием**.
Если связывание проводится компилятором (компоновщиком) перед запуском программы, то оно называется **статическим или ранним связыванием (early binding).**

**Позднее связывание (late binding), динамическим (dynamic) или связыванием на стадии выполнения (runtime binding)** - это связывание, проводимое непосредственно во время выполнения программы, в зависимости от типа объекта.
В языках, реализующих позднее связывание, должен существовать механизм определения фактического типа объекта во время работы программы, для вызова подходящего метода.
Иначе говоря, компилятор не знает тип объекта, но механизм вызова методов определяет его и вызывает соответствующее тело метода. Механизм позднего связывания зависит от конкретного языка, но нетрудно предположить, что для его реализации в объекты должна включаться какая-то дополнительная информация.

> Для всех методов Java используется механизм позднего (динамического) связывания, если только метод не был объявлен как `final` (приватные методы являются `final` по умолчанию).

<a name = "OOPIncaps"></a>

## Инкапсуляция

**Инкапсуляция** - механизм языка, связывающий данные и методы для работы с ними в единый объект, позволяющий реализовать сокрытие.

**Цель инкапсуляции** - уйти от зависимости внешнего интерфейса класса (то, что могут использовать другие классы) от реализации. Малейшее изменение в классе не должно влечь за собой изменение внешнего поведения класса.

### Реализация сокрытия

- Модификаторы доступа (private, public, protected, package-private или friendly)
- Ключевое слово `package`, разделяющее глобальное пространство имен (package mypackage;)
- Ключевое слово `import`, подключающее пакет (import java.util.ArrayList; import mypackage;)

Отличие `private` от `friendly`:
- **private** - никто не имеет доступа к члену кроме класса, в котором он объявлен, а также вложенных и внешних классов. **не наследуются**'.
- **friendly** - модификатор по умолчанию, не имеет ключевого слова. к члену класса имеют доступ все классы, в пределах данного пакета
  . За пределами пакета член класса имеет модификатор доступа private. Поля и методы с данным модификатором доступа **наследуются**'.

При подключении пакетов, имеющих одинаковые члены(типы), могут возникать коллизии, для решения которых
при работе с членом надо указывать CLASSPATH.

```java
java.util.Vector v = new java.util.Vector();
```

<a name = "OOPInherit"></a>

## Наследование

**Наследование** - механизм языка, позволяющий создать новые классы на основе уже существующих с полностью
или частично заимствованным функционалом.

`extends` это ключевое слово, предназначенное для расширения реализации какого-то существующего класса. Создается
новый класс на основе существующего, и этот новый класс расширяет (extends) возможности старого.

`implements` это ключевое слово, предназначенное для реализации интерфейса (interface).

Для переопределения метода используется аннотация `@Override`

Однако наследование не всегда является хорошей практикой, и его следует использовать с осторожностью по следующим причинам:

1. **Жесткая связь между классами**: Наследование создает жесткую связь между родительским и дочерним классами.
Изменения в родительском классе могут повлиять на все дочерние классы, что может стать проблемой при обслуживании и развитии кодовой базы.
Нежелательные изменения в родительском классе могут привести к непредсказуемым поведениям в дочерних классах.
2. **Нарушение инкапсуляции**: Наследование может нарушить принцип инкапсуляции, потому что дочерний класс получает доступ
к защищенным и публичным членам родительского класса. Это может сделать сложнее контролировать и обеспечивать целостность 
данных и поведения классов.
3. **Жесткая иерархия классов**: Использование наследования может привести к созданию слишком сложной иерархии классов,
которая затруднит понимание кода и его поддержку. Это может усложнить архитектуру программы и сделать код менее гибким.

## Отношения между классами

- **IS-A**: наследование и релизация интерфейса.
- **HAS-A** (один класс использует другой) отношения:
  - Ассоциация - объекты двух классов могут ссылаться друг на друга:
    - Агрегация - объект поступает из-вне (более слабая связь).
    - Композиция - объект создается внутри конструктора текущего класса (более сильная связь).
   
Пример агрегации
```java
public class Horse {
    private Halter halter;
    public  Horse (Halter halter) {
        this.halter = halter;
    }
}
```

Пример композиции
```java
public class Horse {
    private Halter halter;
    public  Horse () {
        this.halter = new Halter();
    }
}
```

>По возможности лучше использовать композицию вместо наследования из-за недостатков наследования.

<a name = "OOPPolymorph"></a>

## Полиморфизм

**Полиморфизм** - механизм языка позволяющий членам класса с одинаковой иерархией иметь различную реализацию,
многообразие форм. _Одна задача - множество решений_.

Реализовывается через перегрузки методов.

<a name = "Abstract"></a>

## Абстракция

**Абстрагирование** – это способ выделить набор общих характеристик объекта, исключая из рассмотрения частные и незначимые.
Соответственно, **абстракция** – это набор всех таких характеристик.

Пример реализация класса **Animal** для создание объекта **pig**.

<a name = "Letters"></a>

## Обмен сообщениями

Объекты взаимодействуют, **посылая и получая сообщения**.

**Сообщение** — это запрос на выполнение действия, дополненный набором аргументов, которые могут понадобиться при выполнении действия. В ООП посылка сообщения (вызов метода) — это единственный путь передать управление объекту. Если объект должен «отвечать» на это сообщение, то у него должна иметься соответствующий данному сообщению метод.
Так же объекты, используя свои методы, могут и сами посылать сообщения другим объектам. Обмен сообщениями реализуется с помощью динамических вызовов, что приводит к **чрезвычайно позднему связыванию (extreme late binding)**.

---
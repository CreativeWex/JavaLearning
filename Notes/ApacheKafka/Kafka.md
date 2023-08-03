# Apache Kafka

Для передачи информации между миросервисами применяется технология **брокеров/диспетчеров сообщений**, таких как Kafka,
RabbitMQ и прочих.

Они позволяют компонентам ставить друг другу задачи, сообщать об изменениях в системе и уведомлять другие части логики о
своем состоянии.

### Применение

- **Связь микросервисов между собой**: пересылаем сообщения другим сервисам после какого-то действия, либо подписываемся на
обновления от других сервисов.
- **Организация потоков данных**: передача событий по цепочке.
- **Агрегация записей**: в кафка можно писать данные быстрее, чем в обычную БД. С помощью сообщений можно организовать сбор метрик,
производить вычисления и результат записывать в БД.
- **Сбор логов**: дает возможность хранить сообщения в течение определенного времени.

> Особенность kafka заключается в том, что в нем, в отличие от других брокеров, таких как RabbitMQ, сообщения хранятся
даже после их обработки потребителями.

**Производители** - это микросервисы, веб-приложения, все, что может создавать сообщения, события потоков.

**Брокер** - это виртуальная машина, которая запускает процесс kafka. На количество брокеров нет ограничений

**Kafka Streams Java API** - библиотека для обработки и преобразования данных, с помощью которой можно:
- преобразовывать данные;
- расширять данные;
- выполнять фильтрацию, группировку, агрегацию и др.

# KafkaProducer API

Позволяет приложению **отправлять сообщения (события) в брокеры** Kafka.
Как продюсер, ваше приложение будет отправлять сообщения в различные топики, и затем другие приложения,
называемые потребителями, смогут считывать и обрабатывать эти сообщения.

Основными компонентами и концепциями являются:
- конфигурация;
- топики;
- сериализация;
- разбиение на партиции;
- отправка сообщений;
- асинхронность.

## I. Конфигурация

Конфигурация включает в себя ряд свойств, настраивающих поведение продюсера при отправке сообщений в Kafka.

Конфигурация состоит из **следующих шагов**:
1. producerConfig - Найстройка свойств конфигурации;
2. Бин ProducerFactory<K, V>;
3. Бин KafkaTemplate<K, V>.

### 1. producerConfig

Свойства представляют собой пару ключ-значение и могут быть реализованы через специальный класс `Properties()` или
через `Map<String,Object>` и может выглядеть следующим образом:

```java
public Map<String, Object> producerConfig() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServersUrl);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return properties;
    }
```

Класс ProducerConfig содержит константы, отвечающие за свойства. Рассмотрим свойства подробнее:
- **ProducerConfig.BOOTSTRAP_SERVERS_CONFIG** (обязательное свойство) - список адресов серверов Kafka, для установления связи с брокерами.
Если работа ведется с кластером Kafka и серверов несколько, то они указываются через запятую. В примере список адресов
хранится в поле класса bootstrapServersUrl, в которую Spring вставляет значение из application.properties Spring Boot приложения.
```java
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServersUrl;
```

Файл application.properties. После знака '=' указывается ip-адрес kafka (например, localhost) и порт (9092 по умолчанию).
```java
spring.kafka.bootstrap-servers=kafka:9092
```
> В данном примере 'kafka' - название контейнера docker-compose
```dockerfile
  kafka:
    image: wurstmeister/kafka:2.13-2.8.1
    container_name: kafka
    ports:
      - "9092:9092"
    environment:
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka:9092
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT
      KAFKA_LISTENERS: PLAINTEXT://0.0.0.0:9092,PLAINTEXT_HOST://kafka:29092
      KAFKA_INTER_BROKER_LISTENER_NAME: PLAINTEXT
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
    depends_on:
      - zookeeper
      
    spring-app:
    build:
      context: .
      dockerfile: Dockerfile
    container_name: spring-app
    ports:
      - "8080:8080"
    depends_on:
      - kafka
    environment:
      SPRING_KAFKA_BOOTSTRAP_SERVERS: kafka:9092
```
- **ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG** - задает класс сериализатора, который преобразует сообщения в байтовый формат.
При отправке сообщений в Kafka, ключи и значения должны быть сериализованы в байтовый формат для сохранения в топиках.
Можно писать свои сериализаторы или использовать стандартные, предоставляемые Kafka (выбираются в зависимости от типа сообщений,
например `StringSerializer.class`, `IntegerSerializer.class`, `DoubleSerializer.class` и т.д.);
<br><br>
- **ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG** - сериализатор значений, преобразует значения сообщений в байтовый
формат;
  <br><br>
- **ProducerConfig.RETRIES_CONFIG** - количество попыток переотправить сообщение в случае ошибки;
  <br><br>
- **ProducerConfig.ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG** - время ожидания ответа от сервера перед повторной попыткой.

### 2. ProducerFactory<K, V>

Интерфейс для создания экземпляров KafkaProducer. ProducerFactory обрабатывает жизненный цикл KafkaProducer, включая
создание, закрытие и пересоздание в случае ошибок.

```java
@Bean
public ProducerFactory<String, String> producerFactory() {
    return new DefaultKafkaProducerFactory<>(producerConfig());
}
```

В примере использутеся `DefaultKafkaProducerFactory` - простая конфигурация продюсера с помощью свойств, передаваемых
в конструктор.

Существуют и другие реализации ProducerFactory:
- `CachingProducerFactory` - обеспечивает кеширование экземпляров KafkaProducer, что улучшает производительность,
поскольку пересоздание продюсеров занимает некоторое время. **Используется если есть большое количество продюсеров
с одинаковой конфигурацией**;
<br><br>
- `TransactionalProducerFactory` - позволяет отправлять сообщения в Kafka в пределах одной транзакции, обеспечивая атомарность при записи в несколько топиков.
  **Используется при одновременной работе с большим количеством топиков**;

### 3. KafkaTemplate

KafkaTemplate предоставляет различные методы для отправки сообщений в Kafka.

```java
@Bean
public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> producerFactory) {
    return new KafkaTemplate<>(producerFactory);
}
```

### Пример конфигуации KafkaProducer

```java
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServersUrl;

    public Map<String, Object> producerConfig() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServersUrl);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return properties;
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

}
```

## II. Разбиение топиков на партиции

### Topic

**Topic** - это некая категория, канал и коллекция, в которую записываются, где хранятся и откуда читаются различные
события и сообщения. Топик можно представить как аналог таблицы в базе данных или папки электронной почты, где
**сообщения с одним и тем же ключом сгруппированы вместе**. Например, может быть топик "логи", куда записываются все
логи приложения, или топик "заказы", куда записываются все заказы от клиентов.

### Партиции

**Партиция (partition)** - физическое хранилище данных внутри топика. Каждый топик может быть разделен на несколько
партиций и каждая партиция представляет собой упорядоченную последовательность сообщений. 

Партиции **нужны для повышения производительности и для горизонтального масштабирования**. Разные партиции могут
параллельно обрабатываться на разных брокерах. Это позволяет распределить нагрузку между брокерами и обеспечить
высокую пропускную способность при обработке сообщений.

> Количество партиций определяется при создании топика и остается постоянным.

### Ключи

Все сообщения с одинаковым ключом попадают в одну и ту же партицию и обрабатываются в порядке их отправки. Это обеспечивает семантику упорядоченности для сообщений с одинаковым ключом.

## III. Отправка сообщений

Для отправки сообщений используется экземпляр KafkaTemplate.
Основные методы:

**send(String topic, V data)**: Отправляет сообщение в указанный топик без ключа.
```java
// Отправка сообщения в указанный топик без ключа
kafkaTemplate.send("my-topic", "Hello, Kafka!");
```

**send(String topic, K key, V data)**: Отправляет сообщение с указанным ключом в указанный топик.
Сообщения с одинаковым ключом будут отправлены в одну и ту же партицию.
```java
// Отправка сообщения с ключом в указанный топик
kafkaTemplate.send("my-topic", "my-key", "Hello, Kafka!");
```

**send(Message \<?> message)**: Отправляет сообщение, представленное объектом типа `Message<?>`.
Message позволяет указывать топик, ключ и другие заголовки сообщения.
```java
// Создание объекта Message и отправка его
Message<String> message = MessageBuilder
    .withPayload("Hello, Kafka!")
    .setHeader(KafkaHeaders.TOPIC, "my-topic")
    .setHeader(KafkaHeaders.MESSAGE_KEY, "my-key")
    .build();
kafkaTemplate.send(message);
```

**sendDefault(ProducerRecord<K, V> record)**: Отправляет сообщение в топик по умолчанию без указания ключа.
Если не указан топик, сообщение будет отправлено в топик, который задан как топик по умолчанию в конфигурации.
```java
// Отправка сообщения в топик по умолчанию без ключа
kafkaTemplate.sendDefault(new ProducerRecord<>("Hello, Kafka!"));
```


## Чтение Topic в Apache Kafka

Ввести команду `bin/kafka-console-consumer.sh --topic <название_топика> --from-beginning --bootstrap-server localhost:9092`

Для ОС Windows или при работе с контейнерами нужно ввести команду в интерактивном терминале контейнера Kafka, для этого:
1. Запустить контейнер Kafka и найти его id с помощью команды `docker ps`.
2. Войти в контейнер Kafka в интерактивном режиме среды Linux с помощью команды
   `docker exec -it <id_контейнера> bash`.
3. Выполнить команду `/opt/kafka/bin/kafka-console-consumer.sh --topic <название_топика> --from-beginning --bootstrap-server localhost:9092`.
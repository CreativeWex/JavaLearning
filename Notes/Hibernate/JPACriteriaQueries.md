```java
@Id
@SequenceGenerator(name = "_id_seq", sequenceName = "_id_seq", allocationSize = 1)
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "_id_seq")
private Long id;
```
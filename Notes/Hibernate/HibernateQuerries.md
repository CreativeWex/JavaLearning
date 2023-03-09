Поле
```java
@PersistenceContext
    private EntityManager entityManager;
```

Метод
```java
@Override
    @SuppressWarnings("unchecked")
    public List<Task> findActiveTasks() {
        if (entityManager == null || entityManager.unwrap(Session.class) == null) {
            throw new NullPointerException();
        }
        return entityManager.createQuery("select task from Task task where task.status = ?1").setParameter(1, "ACTIVE").getResultList();
    }
```
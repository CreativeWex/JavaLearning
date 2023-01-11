package Notes.Spring.boot.CRUD.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import springcourse.models.Person;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Optional<Person> show(String email) {
        return jdbcTemplate.query("SELECT * FROM person WHERE email=?", new Object[]{email},
            new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public Person show(int id) {
       return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
               .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(name, age, email) VALUES(?, ?, ?)", person.getName(), person.getAge(), person.getEmail());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE Person SET name=?, age=?, email=? WHERE id=?", updatedPerson.getName(), updatedPerson.getAge(),
                updatedPerson.getEmail(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }

    ///////////////////////////////
    // Пакетная вставка
    //////////////////////////////

    public void testBatchUpdate() {
        List<Person> people = create1000People();
        long timeStart = System.currentTimeMillis();

        jdbcTemplate.batchUpdate("INSERT INTO Person (name, age, email) VALUES(?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                        preparedStatement.setString(1, people.get(i).getName());
                        preparedStatement.setInt(2, people.get(i).getAge());
                        preparedStatement.setString(3, people.get(i).getEmail());
                    }

                    @Override
                    public int getBatchSize() {
                        return people.size();
                    }
                });

        long timeFinish = System.currentTimeMillis();
        System.out.println("Batch update time: " + (timeFinish - timeStart));
    }

    public void testMultipleUpdate() {
        List<Person> people = create1000People();
        long timeStart = System.currentTimeMillis();

        for (Person person : people) {
            jdbcTemplate.update("INSERT INTO Person (name, age, email) VALUES(?, ?, ?)",
                    person.getName(), person.getAge(), person.getEmail());
        }
        long timeFinish = System.currentTimeMillis();
        System.out.println("Ordinary test time: " + (timeFinish - timeStart));
    }

    private List<Person> create1000People() {
        List<Person> people = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            people.add(new Person(i + 1000, "Name" + i, 18 + i, "test" + i + "mail.ru"));
        }
        return  people;
    }

    public void deleteTestUnits() {
        jdbcTemplate.update("DELETE FROM Person WHERE name LIKE 'Name%';");
    }
}


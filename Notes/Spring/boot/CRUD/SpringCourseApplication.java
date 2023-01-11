package Notes.Spring.boot.CRUD;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import javax.sql.DataSource;
import java.util.Objects;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class SpringCourseApplication {
    private final Environment environment;

    @Autowired
    public SpringCourseApplication(Environment environment) {
        this.environment = environment;
    }

    @Bean
    HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName((Objects.requireNonNull(environment.getProperty("DB.driver"))));
        dataSource.setUrl(environment.getProperty("DB.url"));
        dataSource.setUsername(environment.getProperty("DB.user"));
        dataSource.setPassword(environment.getProperty("DB.password"));

        return  dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringCourseApplication.class, args);
    }

}

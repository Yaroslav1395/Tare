package kg.zavod.Tare.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@RequiredArgsConstructor
public class SchemaInitializer {

    /*private final DataSource dataSource;

    @PostConstruct
    public void initialize() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("CREATE SCHEMA IF NOT EXISTS Zavod");
        jdbcTemplate.execute("SET SCHEMA Zavod");
    }*/
}

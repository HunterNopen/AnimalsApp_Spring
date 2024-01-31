package com.example.animals_app.repository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SqlScriptRunner implements CommandLineRunner {
    private final JdbcTemplate jdbcTemplate;

    public SqlScriptRunner(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        String sql="INSERT INTO animal_entity(id,name,type, cuteness_lvl, DTYPE) VALUES (1, 'Grogg','Capybara', '10', 'C')";
        jdbcTemplate.execute(sql);
    }
}

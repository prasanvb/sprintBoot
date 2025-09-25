package com.example.jdbc.dao;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootApplication
@Log
public class DaoApplication  implements CommandLineRunner {

    private final DataSource dataSource;

    public DaoApplication(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public static void main(String[] args) {
		SpringApplication.run(DaoApplication.class, args);
	}

    @Override
    public void run(String... args) {
        log.info("Postgres dataSource: "+ dataSource.toString());
        final JdbcTemplate restTemplate = new JdbcTemplate(dataSource);
        restTemplate.execute("select 1");
    }
    
}

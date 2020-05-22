package ru.shakurov.spring_webapp.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Component
public class DataBaseInit {
    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void init() {
        Resource resource = new ClassPathResource("schema.sql");

        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();

        resourceDatabasePopulator.addScript(resource);
        resourceDatabasePopulator.execute(dataSource);
    }
}

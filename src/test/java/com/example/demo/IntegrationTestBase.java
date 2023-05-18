package com.example.demo;

import com.example.demo.initializer.Postgres;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@Sql("/sql/data.sql")
@ActiveProfiles("test")
@SpringBootTest
@ContextConfiguration(initializers = {
       Postgres.Initializer.class
})
@Transactional
public abstract class IntegrationTestBase {
    @BeforeAll
    static void init() {
        Postgres.container.start();
    }


    @Test
    public void testTest() {
        System.out.println("test");
    }
}

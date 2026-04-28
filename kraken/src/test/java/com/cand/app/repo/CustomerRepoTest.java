package com.cand.app.repo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

@JdbcTest
public class CustomerRepoTest {

    @Autowired
    private JdbcTemplate jdbc;

    @Test
    void embeddedDatabaseIsAvailable() {
        Integer tables = jdbc.queryForObject("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES", Integer.class);
        Assertions.assertNotNull(tables);
        Assertions.assertTrue(tables > 0);
    }

//    @Test
    void retrieveTables() {
        var tables = JdbcTestUtils.countRowsInTable(jdbc, "pg_catalog.pg_tables");
        Assertions.assertEquals(68, tables);
    }
}
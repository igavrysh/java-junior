package com.gavrysh.demo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLExample_12 {
    public static final String JDBC_URL
            = "jdbc:mysql://127.0.0.1:3306?quiz_db";

    public static void main(String[] args) throws SQLException {
        java.sql.Driver driver = new com.mysql.cj.jdbc.Driver();
        Properties properties = new Properties() {{
            put("user", "username");
            put("password", "password");
        }};

        System.out.println(driver.acceptsURL("jdbc:SUPER_DB"));
        System.out.println(driver.acceptsURL(JDBC_URL));

        try (Connection conn = driver.connect(JDBC_URL, properties)) {
            System.out.println("conn = " + conn);
        }
    }
}

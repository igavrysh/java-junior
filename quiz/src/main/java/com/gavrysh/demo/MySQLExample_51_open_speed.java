package com.gavrysh.demo;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLExample_51_open_speed {

    public static final String JDBC_URL
            = "jdbc:mysql://127.0.0.1:3306/quiz_db";

    public static Properties properties = new Properties() {{
        put("user", "username");
        put("password", "password");
    }};

    public static void main(String[] args) throws SQLException {
        Driver driver = new com.mysql.jdbc.Driver();
        for (int k = 0; k < 200; k++) {
            long t0 = System.nanoTime();
            try (Connection conn = driver.connect(JDBC_URL, properties)) {}
            long t1 = System.nanoTime();
            System.out.printf("%,10d\n", t1-t0);
        }
    }
}

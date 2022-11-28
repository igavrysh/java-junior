package com.gavrysh.demo;


import java.sql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;

public class MySQLExample_20 {

    public static final String JDBC_URL
            = "jdbc:mysql://127.0.0.1:3306?quiz_db";

    public static void main(String[] args) throws SQLException {
        Enumeration<Driver> iter = DriverManager.getDrivers();
        while (iter.hasMoreElements()) {
            Driver driver = iter.nextElement();
            System.out.println("driver = " + driver);
        }

        Properties properties = new Properties() {{
            put("user", "username");
            put("password", "password");
        }};

        try (Connection conn = DriverManager.getConnection(JDBC_URL, properties)) {
            System.out.println("conn = " + conn);
        }
    }
}

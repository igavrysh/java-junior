package com.gavrysh.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class MySQLExample_60 {

    public static final String JDBC_URL
            = "jdbc:mysql://127.0.0.1:3306/quiz_db";

    public static Properties properties = new Properties() {{
        put("user", "username");
        put("password", "password");
    }};

    public static void main(String[] args) throws SQLException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, properties)) {
            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS tmp;");
            statement.execute("CREATE TABLE tmp (id INT, name VARCHAR(64));");
            statement.execute("DROP TABLE tmp;");
        }
    }
}

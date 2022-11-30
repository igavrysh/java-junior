package com.gavrysh.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class MySQLExample_71 {

    public static final String JDBC_URL
            = "jdbc:mysql://127.0.0.1:3306/quiz_db";

    public static Properties properties = new Properties() {{
        put("user", "username");
        put("password", "password");
    }};

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(JDBC_URL, properties);
        try {
            connection.setAutoCommit(false);

            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS Users;");
            statement.execute("CREATE TABLE Users (id INT, name VARCHAR(64));");
            statement.execute("INSERT INTO Users (id, name) VALUES (1, 'Mike')");
            connection.commit();
        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        }
    }
}
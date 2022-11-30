package com.gavrysh.demo;

import java.sql.*;
import java.util.Properties;

public class MySQLExample_70 {

    public static final String JDBC_URL
            = "jdbc:mysql://127.0.0.1:3306/quiz_db";

    public static Properties properties = new Properties() {{
        put("user", "username");
        put("password", "password");
    }};

    public static void main(String[] args) throws SQLException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, properties)) {
            connection.setAutoCommit(false);

            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS Users;");
            statement.execute("CREATE TABLE Users (id INT, name VARCHAR(64));");
            statement.execute("INSERT INTO Users (id, name) VALUES (1, 'Mike')");

            connection.rollback();

            ResultSet rs = statement.executeQuery("SELECT id, name FROM Users");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                System.out.printf("%d, %s\n", id, name);
            }
            statement.execute("DROP TABLE Users;");
        }
    }
}

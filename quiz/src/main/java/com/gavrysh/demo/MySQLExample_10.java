/**
 * https://docs.oracle.com/javase/tutorial/jdbc/basics/connecting.html
 */
package com.gavrysh.demo;

import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLExample_10 {
    public static final String JDBC_URL
            = "jdbc:mysql://127.0.0.1:3306?quiz_db";

    public static void main(String[] args) throws SQLException {
        Driver driver = new Driver();
        Properties properties = new Properties();
        properties.put("user", "username");
        properties.put("password", "password");
        Connection conn = driver.connect(JDBC_URL, properties);
        try {
            // use conn here
        } finally {
            conn.close();
        }
    }
}

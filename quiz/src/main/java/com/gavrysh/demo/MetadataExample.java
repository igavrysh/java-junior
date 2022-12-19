package com.gavrysh.demo;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MetadataExample {

    public static final String JDBC_URL
            = "jdbc:mysql://127.0.0.1:3306/quiz_db";

    public static Properties properties = new Properties() {{
        put("user", "username");
        put("password", "password");
    }};

    public static void main(String[] args) throws SQLException {
        Connection conn = getConnection();
        DatabaseMetaData metaData = conn.getMetaData();
        dumpDbConstraints(metaData);
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, properties);
    }

    private static void dumpDbConstraints(DatabaseMetaData metaData) throws SQLException {
        System.out.println("Db constraints: ");
        System.out.println("    MaxStatements = " + metaData.getMaxStatements());
        System.out.println("    MaxColumnsInGroupBy = " + metaData.getMaxColumnsInGroupBy());
        System.out.println("    MaxColumnsInIndex = " + metaData.getMaxColumnsInIndex());
        System.out.println("    MaxColumnsInSelect = " + metaData.getMaxColumnsInSelect());
        System.out.println("    MaxColumnsInTable = " + metaData.getMaxColumnsInTable());
        System.out.println("    MaxConnections = " + metaData.getMaxConnections());
        System.out.println("    MaxStatementLength = " + metaData.getMaxStatementLength());
        System.out.println("    MaxTablesInSelect = " + metaData.getMaxTablesInSelect());
    }
}

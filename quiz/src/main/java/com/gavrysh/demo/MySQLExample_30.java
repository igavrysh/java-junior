package com.gavrysh.demo;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

public class MySQLExample_30 {

    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("com.gavrysh.demo.SuperDbDriver1");

        Enumeration<Driver> iter = DriverManager.getDrivers();
        while (iter.hasMoreElements()) {
            Driver driver = iter.nextElement();
            System.err.println("driver = " + driver);
        }
    }
}

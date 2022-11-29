package com.gavrysh.demo;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Enumeration;

public class MySQLExample_41 {

    public static void main(String[] args) throws SQLException, IOException {
        //ClassLoader loader = Thread.currentThread().getContextClassLoader();
        ClassLoader loader = MySQLExample_41.class.getClassLoader();
        Enumeration<URL> drivers
                 = loader.getResources("META-INF/services/" + "java.sql.Driver");
        while (drivers.hasMoreElements()) {
            System.out.println(drivers.nextElement());
        }
    }
}

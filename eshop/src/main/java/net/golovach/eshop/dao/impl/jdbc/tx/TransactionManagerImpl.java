package net.golovach.eshop.dao.impl.jdbc.tx;

import net.golovach.eshop.dao.BaseDataSource;
import net.golovach.eshop.dao.impl.jdbc.JdbcUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class TransactionManagerImpl extends BaseDataSource implements TransactionManager {

    public static final String JDBC_URL
            = "jdbc:mysql://127.0.0.1:3306/quiz_db";

    public static Properties properties = new Properties() {{
        put("user", "username");
        put("password", "password");
    }};

    private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();

    @Override
    public <T, E extends Exception> T doInTransaction(UnitOfWork<T, E> unitOfWork) throws E, SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(JDBC_URL);
            connectionHolder.set(conn);
            T result = unitOfWork.doInTx();
            conn.commit();
            return result;
        } catch (SQLException sqlException) {
            conn.rollback();
            throw sqlException;
        } finally {
            JdbcUtils.closeQuietly(conn);
            connectionHolder.remove();
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection conn = connectionHolder.get();
        if (conn != null) {
            return conn;
        } else {
            throw new IllegalArgumentException("Don't call 'getConnection' not under TransactionManager!");
        }
    }
}

class MyThreadLocal<T> {
    // For GC to collect Thread here we should use weak
    // reference to Thread instead of strong reference used below
    private Map<Thread, T> holder = new ConcurrentHashMap<>();

    public T get() {
        return holder.get(Thread.currentThread());
    }

    public void set(T elem) {
        holder.put(Thread.currentThread(), elem);
    }
}

class Test {
    public static void main(String[] args) throws InterruptedException {
        // ThreadLocal
        final ThreadLocal<String> local
                = new ThreadLocal<>();
        System.out.println(local.get());
        local.set("Hello!");
        Thread t = new Thread(() -> {
            System.out.println("T2: " + local.get()) ;
        });
        t.start();
        t.join();

        System.out.println(local.get());
    }
}

class Test2 {
    public static void main(String[] args) throws InterruptedException {
        // they monitor that new Thread was created in current thread
        // and pass a copy of variables (cached) to a new child
        final InheritableThreadLocal<String> local
                = new InheritableThreadLocal<>();
        System.out.println(local.get());
        local.set("Hello!");
        Thread t = new Thread(() -> {
            System.out.println("T2: " + local.get());
        });
        t.start();
        t.join();

        System.out.println(local.get());
    }
}

class Test3 {
    public static void main(String[] args) throws InterruptedException {
        // ThreadLocal
        final InheritableThreadLocal<String> local = new InheritableThreadLocal<>();
        System.out.println(local.get());
        local.set("Hello!");
        Thread t = new Thread(() -> {
            System.out.println("T2: " + local.get()) ;
            local.set("XXX");
            System.out.println("T2: " + local.get()) ;
        });
        t.start();
        t.join();

        System.out.println(local.get());
    }
}




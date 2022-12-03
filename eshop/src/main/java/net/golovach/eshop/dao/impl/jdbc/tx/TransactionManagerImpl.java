package net.golovach.eshop.dao.impl.jdbc.tx;

import net.golovach.eshop.dao.BaseDataSource;
import net.golovach.eshop.dao.impl.jdbc.JdbcUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.Callable;

public class TransactionManagerImpl extends BaseDataSource implements TransactionManager {

    public static final String JDBC_URL
            = "jdbc:mysql://127.0.0.1:3306/quiz_db";

    public static Properties properties = new Properties() {{
        put("user", "username");
        put("password", "password");
    }};

    private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();

    @Override
    public <T> T doInTransaction(Callable<T> unitOfWork) throws Exception {
        Connection conn = DriverManager.getConnection(JDBC_URL);
        conn.setAutoCommit(false);
        connectionHolder.set(conn);
        try {
            T result = unitOfWork.call();
            conn.commit();;
            return result;
        } catch (Exception e) {
            conn.rollback();
            throw e;
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

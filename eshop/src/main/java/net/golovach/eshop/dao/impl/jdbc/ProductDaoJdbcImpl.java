package net.golovach.eshop.dao.impl.jdbc;

import net.golovach.eshop.dao.ProductDao;
import net.golovach.eshop.dao.exception.DaoSystemException;
import net.golovach.eshop.dao.exception.NoSuchEntityException;
import net.golovach.eshop.entity.Product;

import java.sql.*;
import java.util.List;
import java.util.Properties;

public class ProductDaoJdbcImpl implements ProductDao {

    public static final String JDBC_URL
            = "jdbc:mysql://127.0.0.1:3306/quiz_db";

    public static Properties properties = new Properties() {{
        put("user", "username");
        put("password", "password");
    }};

    private final String SELECT_ALL_SQL
            = "SELECT id, caption, question FROM questions";

    private final String SELECT_BY_ID_SQL
            = "SELECT id, caption, question FROM questions WHERE id = ?";

    @Override
    public Product selectById(int id) throws DaoSystemException, NoSuchEntityException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(JDBC_URL);
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement(SELECT_BY_ID_SQL);

            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            stmt.setInt(2, id);
            rs = stmt.executeQuery();

            stmt.setInt(3, id);
            rs = stmt.executeQuery();

            if (!rs.next()) {
                throw new NoSuchEntityException("No Product for id="+id);
            }
            Product result = new Product(rs.getInt("id"), rs.getString("caption"));
            conn.commit();
            return result;
        } catch (SQLException e) {
            JdbcUtils.rollbackQuietly(conn);
            throw new DaoSystemException("Some exception", e);
        } finally {
            JdbcUtils.closeQuietly(rs, stmt, conn);
        }
    }

    @Override
    public List<Product> selectAll() throws DaoSystemException {
        return null;
    }
}

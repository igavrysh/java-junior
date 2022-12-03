package net.golovach.eshop.dao.impl.jdbc;

import net.golovach.eshop.dao.ProductDao;
import net.golovach.eshop.dao.exception.DaoSystemException;
import net.golovach.eshop.dao.exception.NoSuchEntityException;
import net.golovach.eshop.entity.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Properties;

public class ProductDaoJdbcExternalTxImpl implements ProductDao {

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

    private DataSource dataSource;

    private void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Product selectById(int id) throws DaoSystemException, NoSuchEntityException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            Connection conn = dataSource.getConnection();
            stmt = conn.prepareStatement(SELECT_BY_ID_SQL);

            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (!rs.next()) {
                throw new NoSuchEntityException("No Product for id="+id);
            }

            return new Product(rs.getInt("id"), rs.getString("caption"));
        } catch (SQLException e) {
            throw new DaoSystemException("Some exception", e);
        } finally {
            JdbcUtils.closeQuietly(rs, stmt);
        }
    }

    @Override
    public List<Product> selectAll() throws DaoSystemException {
        return null;
    }
}

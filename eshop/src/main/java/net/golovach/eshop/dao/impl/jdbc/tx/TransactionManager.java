package net.golovach.eshop.dao.impl.jdbc.tx;

import java.sql.SQLException;
import java.util.concurrent.Callable;

public interface TransactionManager {

    public <T, E extends Exception>
    T doInTransaction(UnitOfWork<T, E> unitOfWork) throws E, SQLException;
}

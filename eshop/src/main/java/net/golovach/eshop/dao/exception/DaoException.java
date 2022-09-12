package net.golovach.eshop.dao.exception;

/**
 * Created by BELSHINA on 08.02.2017.
 */
public class DaoException extends Exception {
	
    private static final long serialVersionUID = 1L;

	public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}

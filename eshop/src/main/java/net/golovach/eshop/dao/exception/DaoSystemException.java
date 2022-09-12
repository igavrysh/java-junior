package net.golovach.eshop.dao.exception;

/**
 * Created by BELSHINA on 08.02.2017.
 */
public class DaoSystemException extends DaoException {
	
    private static final long serialVersionUID = 1L;

	public DaoSystemException(String message) {
        super(message);
    }

    public DaoSystemException(String message, Throwable cause) {
        super(message, cause);
    }
}

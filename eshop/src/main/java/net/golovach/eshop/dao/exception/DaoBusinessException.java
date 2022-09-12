package net.golovach.eshop.dao.exception;

/**
 * Created by BELSHINA on 08.02.2017.
 */
public class DaoBusinessException extends DaoException {

	private static final long serialVersionUID = 1L;

	public DaoBusinessException(String message) {
        super(message);
    }

    public DaoBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}

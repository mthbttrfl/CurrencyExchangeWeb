package org.example.currencyexchangeweb.exeptions;

import org.example.currencyexchangeweb.utils.DaoErrorDecoder;

import java.sql.SQLException;

public class DaoException extends RuntimeException {
    private final String sqlState;
    private final String decodedMessage;

    public DaoException(SQLException cause) {
        super(cause.getMessage(), cause);
        this.sqlState = cause.getSQLState();
        this.decodedMessage = DaoErrorDecoder.decodeSQLException(cause);
    }

    public String getSqlState() {
        return sqlState;
    }

    public String getDecodedMessage() {
        return decodedMessage;
    }
}

package org.example.currencyexchangeweb.utils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public final class DaoErrorDecoder {

    private static final Map<String, String> ERROR_CODES = new HashMap<>();

    static {
        ERROR_CODES.put("23505", "Unique constraint violation (duplicate key)");
        ERROR_CODES.put("23503", "Foreign key violation");
        ERROR_CODES.put("23502", "NOT NULL constraint violation");
        ERROR_CODES.put("23514", "Check constraint violation");
        ERROR_CODES.put("23000", "Integrity constraint violation");
        ERROR_CODES.put("23001", "Unique constraint violation");
        ERROR_CODES.put("42501", "Access denied");
        ERROR_CODES.put("42601", "SQL syntax error");
        ERROR_CODES.put("42703", "Unknown column");
        ERROR_CODES.put("42883", "Unknown function");
        ERROR_CODES.put("42P01", "Unknown table");
        ERROR_CODES.put("42P07", "Table already exists");
        ERROR_CODES.put("08000", "Connection error");
        ERROR_CODES.put("08003", "Connection does not exist");
        ERROR_CODES.put("08006", "Connection failure");
        ERROR_CODES.put("08001", "Unable to establish connection");
        ERROR_CODES.put("08004", "Connection rejected by server");
        ERROR_CODES.put("40P01", "Deadlock detected");
        ERROR_CODES.put("53000", "Insufficient resources");
        ERROR_CODES.put("54000", "Limit exceeded");
        ERROR_CODES.put("55000", "Access denied");
        ERROR_CODES.put("57014", "Operation canceled");
        ERROR_CODES.put("58000", "System error");
        ERROR_CODES.put("72000", "SQL error");
        ERROR_CODES.put("0", "General database error");
    }

    private DaoErrorDecoder() {
    }

    public static String decodeSQLException(SQLException ex) {
        String sqlState = ex.getSQLState();
        return ERROR_CODES.getOrDefault(sqlState,
                "Unknown database error (SQLState: " + sqlState + ", Message: " + ex.getMessage() + ")");
    }
}
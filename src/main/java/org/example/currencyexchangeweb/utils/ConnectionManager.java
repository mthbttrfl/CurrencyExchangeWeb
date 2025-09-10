package org.example.currencyexchangeweb.utils;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.example.currencyexchangeweb.exeptions.ConnectionException;

import java.sql.Connection;
import java.sql.SQLException;

public final class ConnectionManager {

    private final static HikariDataSource DATA_SOURCE;

    private final static String MESSAGE_DRIVER = "Driver not found, database connection error.";
    private final static String MESSAGE_CONNECTION = "Database connection error.";

    private final static String URL_KEY = "db.url";
    private final static String USER_KEY = "db.user";
    private final static String PASSWORD_KEY = "db.password";
    private final static String DRIVER_KEY = "db.driver";
    private final static String POOL_SIZE_KEY = "db.pool.size";
    private final static String MAX_LIFETIME_KEY = "db.max.lifetime";

    private ConnectionManager(){}

    static {
        try {
            Class.forName(PropertiesUtil.get(DRIVER_KEY));
        } catch (ClassNotFoundException ex) {
            throw new ConnectionException(MESSAGE_DRIVER);
        }

        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(PropertiesUtil.get(URL_KEY));
        config.setUsername(PropertiesUtil.get(USER_KEY));
        config.setPassword(PropertiesUtil.get(PASSWORD_KEY));

        config.setMaximumPoolSize(Integer.parseInt(
                PropertiesUtil.get(POOL_SIZE_KEY)));
        config.setMaxLifetime(Long.parseLong(
                PropertiesUtil.get(MAX_LIFETIME_KEY)));

        DATA_SOURCE = new HikariDataSource(config);
    }

    public static Connection open(){
        try {
            return DATA_SOURCE.getConnection();
        } catch (SQLException ex) {
            throw new ConnectionException(MESSAGE_CONNECTION);
        }
    }

    public static void close() {
        if (DATA_SOURCE != null && !DATA_SOURCE.isClosed()) {
            DATA_SOURCE.close();
        }
    }
}

package com.hsbc.rest.booking.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class DatabaseConnection {

    static final String JDBC_DRIVER = "org.h2.Driver";

    static final String DB_URL = "jdbc:h2:~/test";

    static final String USER = "sa";

    static final String PASSWORD = "";

    static volatile Connection connection;

    private DatabaseConnection() {}

    public static Connection getConnection() {
         if (Objects.isNull(connection)) {
             try {
                 connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             } catch (SQLException e) {
                 e.printStackTrace();
             }
         }
         return connection;
    }
}

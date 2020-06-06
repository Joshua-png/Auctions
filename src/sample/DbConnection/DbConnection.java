package sample.DbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private String URL = "jdbc:mysql://localhost/auction_management";
    private String USERNAME = "dbuser";
    private String PASS = "";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL,USERNAME,PASS);
    }

}

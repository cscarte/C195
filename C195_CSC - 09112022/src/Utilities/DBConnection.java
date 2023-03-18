package Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static String databaseName = "client_schedule";
    private static String url = "jdbc:mysql://localhost:3306/" + databaseName;

    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String dbUserNameJavaVM = "sqlUser";
    private static String dbPasswordJavaVM = "Passw0rd!";

    private static String dbUserNameLocal = "test";
    private static String dbPasswordLocal = "test";

    private static Connection connection;


    /**
     * Method to make connection to database.
     * @return connection
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection makeConnection() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        connection = (Connection) DriverManager.getConnection(url, dbUserNameJavaVM, dbPasswordJavaVM);
        return connection;
    }

    /**
     * Method to return the current connection
     * @return conn
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * Method to close current connection
     */
    public static void closeConnection() throws SQLException {
        connection.close();
    }

    /**
     * Sets connection for a prepared statement
     * @param connection
     */
    public static void setConnection(Connection connection) {
        DBConnection.connection = connection;
    }
}

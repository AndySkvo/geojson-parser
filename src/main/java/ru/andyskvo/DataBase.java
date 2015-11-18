package ru.andyskvo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
    private static Connection conn = null;
    public static Connection getConnection() throws SQLException {

        String driver = "com.mysql.jdbc.Driver";
        String url    = "jdbc:mysql://localhost:3306/YOUR_TABLE_NAME";
        String username = "root";
        String password = "";
        System.setProperty(driver,"");

        return DriverManager.getConnection(url,username,password);
    }

    public static void Connect() {
        try {
            conn = getConnection();

            if (conn == null) {
                System.out.println("No connection!");
                System.exit(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertTown(Town town, int kp) {
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("INSERT INTO YOUR_TABLE_NAME VALUES (" + town.getId() + ", \"" + town.getName() + "\", " + town.getLatitude() + ", " + town.getLongitude() + ", "+ kp +")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

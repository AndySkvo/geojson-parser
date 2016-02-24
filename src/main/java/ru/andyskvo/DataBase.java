package ru.andyskvo;

import java.sql.*;

public class DataBase {
    private static Connection conn = null;

    public static Connection getConnection() throws SQLException {

        String driver = "com.mysql.jdbc.Driver";
        String url    = "jdbc:mysql://localhost:3306/aurora"; //change the db name
        String username = "root"; //change the username
        String password = ""; //change the password
        System.setProperty(driver,"");

        return DriverManager.getConnection(url,username,password);
    }

    public static void connect() {
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

    public static void insertTown(Town town) {
        try {
            Statement statement = conn.createStatement();
            if (!isDublicate(town.getId())) {
                statement.executeUpdate("INSERT INTO place VALUES (" + town.getId() + ", \"" + town.getNameRu() + "\", \"" + town.getNameEn() + "\",\"" + town.getNameUk() + "\",\"" + town.getNameBe() + "\", " + town.getLatitude() + ", " + town.getLongitude() + ")"); //change the table name
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean isDublicate(long id) throws SQLException {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT id FROM place where id =" + id); //change table name
            if (result.next()) {
                return (result.getLong("id") != 0) ? true : false;
            }
            return false;
    }


}

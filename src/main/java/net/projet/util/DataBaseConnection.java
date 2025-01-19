package net.projet.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/quiz";
    private static final String PASSWORD ="";
    private static final String USER = "root";

    private static  Connection connection;

    private DataBaseConnection(){}
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("CONNECTED...");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver JDBC non trouvé", e);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur de connexion à la base de données", e);
        }
    }


    public static Connection getConnection() {
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connexion fermée.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

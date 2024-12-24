package net.projet.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/quiz";
    private static final String PASSWORD ="";
    private static final String USER = "root";

    public static Connection getConnection(){
        Connection connection = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
            if(connection!=null)
                System.out.println("CONNECTED...");


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return connection;
    }


}

package net.projet;

import net.projet.util.DataBaseConnection;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection connection = DataBaseConnection.getConnection();

    }
}
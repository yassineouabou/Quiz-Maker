package net.projet;
import net.projet.entity.User;
import net.projet.enums.Roles;
import net.projet.ui.professorUI.ProfessorInterface;
import net.projet.util.DataBaseConnection;

import javax.swing.*;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection connection = DataBaseConnection.getConnection();
        JFrame frame=new JFrame("quiz maker");
        frame.setSize(800,600);
        frame.setResizable(false);
        User professor=new User("said","nassiri","123456789","nassiri21@gmail.com", Roles.PROFESSEUR);
        professor.setId((long) 1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ProfessorInterface prof=new ProfessorInterface(professor);

        frame.add(prof);
        frame.setVisible(true);
    }
}
package net.projet.ui.professorUI;

import net.projet.entity.User;

import javax.swing.*;
import java.awt.*;

public class ProfessorInterface extends JPanel{


    CardLayout cards;
    ProfessorHome home;

    Manage_quizzes manage_quizzes;
    public ProfessorInterface(User prof){

        this.setSize(800,600);

        cards=new CardLayout();
        this.setLayout(cards);
        manage_quizzes=new Manage_quizzes(this,prof);
        home=new ProfessorHome(this,cards,prof);


        add(home,"profhomepage");

        add(manage_quizzes,"managequizpage");

        cards.show(this,"profhomepage");







    }

}

package net.projet.ui.professorUI;

import net.projet.entity.User;

import javax.swing.*;
import java.awt.*;

public class ProfessorInterface extends JPanel{


    CardLayout cards;
    ProfessorHome home;
    CreateQuiz createquiz;

    public ProfessorInterface(User prof){

        this.setSize(800,600);

        cards=new CardLayout();
        this.setLayout(cards);
        home=new ProfessorHome(this,cards);
        createquiz=new CreateQuiz(this,cards,prof);

        add(home,"profhomepage");

        add(createquiz,"createquizpage");
        cards.show(this,"profhomepage");







    }

}

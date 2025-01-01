package net.projet.ui.professorUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfessorHome extends JPanel{
    JButton createquizbtn,viewquizzes;

    public  ProfessorHome(JPanel parent, CardLayout cards){

        this.setSize(800,600);


        createquizbtn =new JButton("Create Quiz");
        createquizbtn.setBounds(40,40,140,30);
        createquizbtn.setBackground(new Color(255,255,255));
        createquizbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                cards.show(parent,"createquizpage");
            }
        });
        this.add(createquizbtn);

        viewquizzes=new JButton("View Quizzes");
        viewquizzes.setBounds(260,40,140,30);
        viewquizzes.setBackground(new Color(255,255,255));
        this.add(viewquizzes);

    }
}

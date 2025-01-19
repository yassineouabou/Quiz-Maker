package net.projet.ui.professorUI;

import net.projet.entity.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfessorHome extends JPanel{
    JButton create_quizbtn, manage_quizzes;

    JLabel title,user_name;
    JLabel quiz_icon;
    User prof;
    public  ProfessorHome(JPanel parent, CardLayout cards, User prof){
        this.prof=prof;
        this.setSize(800,600);
        this.setBackground(new Color(245, 247, 251));
        this.setLayout(null);

        ImageIcon QuizIcon = new ImageIcon("src/main/resources/images/quiz_app.png");
        Image resizedQuiz = QuizIcon.getImage().getScaledInstance(300,300, Image.SCALE_SMOOTH);
        ImageIcon resized = new ImageIcon(resizedQuiz);

        quiz_icon=new JLabel(resized);
        quiz_icon.setBounds(310,0,200,200);
        this.add(quiz_icon);

        title=new JLabel("Bienvenue Mr ");
        title.setFont(new Font("Segoe UI",Font.BOLD,25));
        title.setBounds(250,150,200,40);

        this.add(title);

        user_name = new JLabel(prof.getPrenom()+" "+prof.getNom());
        user_name.setFont(new Font("SansSerif",Font.BOLD,25));
        user_name.setForeground(new Color(2, 81, 171));
        user_name.setBounds(420,150,400,40);
        this.add(user_name);

        ImageIcon createIcon = new ImageIcon("src/main/resources/images/create_icon.png");
        Image resizedImage = createIcon.getImage().getScaledInstance(35,35, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);


        create_quizbtn =new JButton("Create Quiz");
        create_quizbtn.setIcon(resizedIcon);
        create_quizbtn.setBounds(330,200,140,40);
        create_quizbtn.setFocusPainted(false);
        create_quizbtn.setBackground(Color.white);
        create_quizbtn.setForeground(new Color(2, 81, 171));
        create_quizbtn.setBorder(new LineBorder(new Color(2, 81, 171),2));
        create_quizbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateQuiz createquiz=new CreateQuiz(parent,cards,prof);
                parent.add(createquiz,"createquizpage");
                cards.show(parent,"createquizpage");
            }
        });
        this.add(create_quizbtn);


        ImageIcon manageIcon = new ImageIcon("src/main/resources/images/manage.png");
        Image resizedmanage = manageIcon.getImage().getScaledInstance(35,32, Image.SCALE_SMOOTH);
        ImageIcon manage_Icon = new ImageIcon(resizedmanage);

        manage_quizzes =new JButton("manage Quizzes");
        manage_quizzes.setIcon(manage_Icon);
        manage_quizzes.setBounds(330,280,140,40);
        manage_quizzes.setForeground(new Color(2, 81, 171));
        manage_quizzes.setBackground(new Color(255, 255, 255));
        manage_quizzes.setFocusPainted(false);
        manage_quizzes.setBorder(new LineBorder(new Color(2, 81, 171),2));
        manage_quizzes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                cards.show(parent,"managequizpage");
            }
        });
        this.add(manage_quizzes);

    }
}

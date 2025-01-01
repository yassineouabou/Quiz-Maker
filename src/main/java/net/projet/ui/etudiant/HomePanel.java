package net.projet.ui.etudiant;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {
    JLabel title,text,user_name,complete_text,complete_result,moyenne_text,moyenne_result,temp_text,temp_result;
    JTextField code_exam;
    JButton acceder_btn;
    JPanel form_exam,complete_panel,moyenne_panel,temp_panel;

    private final Color primaryColor = new Color(79, 120, 229);
    private final Color primaryDark = new Color(2, 81, 171);
    private final Color backgroundColor = new Color(245, 247, 251);
    private final Color textColor = new Color(55, 65, 81);

    public HomePanel(){
        JFrame frame = new JFrame();
        frame.setSize(800,600);
        frame.setBackground(backgroundColor);

        frame.setLayout(null);

        title = new JLabel("Bienvenue");
        title.setFont(new Font("Segoe UI",Font.BOLD,25));
        title.setBounds(210,10,200,40);
        frame.add(title);

        user_name = new JLabel("Yassine Ouabou");
        user_name.setFont(new Font("SansSerif",Font.BOLD,25));
        user_name.setForeground(primaryDark);
        user_name.setBounds(340,10,400,40);
        frame.add(user_name);

        complete_panel =  new JPanel();
        complete_panel.setBounds(40,80,200,70);
        complete_panel.setBackground(Color.white);
        complete_panel.setLayout(null);
        frame.add(complete_panel);

        complete_text = new JLabel("Examens complétés");
        complete_text.setBounds(15,6,200,20);
        complete_panel.add(complete_text);

        complete_result = new JLabel("0");
        complete_result.setBounds(15,33,100,20);
        complete_result.setFont(new Font("SansSerif",Font.BOLD,20));
        complete_result.setForeground(primaryDark);
        complete_panel.add(complete_result);







        moyenne_panel =  new JPanel();
        moyenne_panel.setBounds(280,80,200,70);

        moyenne_panel.setBackground(Color.white);
        moyenne_panel.setLayout(null);
        frame.add(moyenne_panel);

        moyenne_text = new JLabel("Moyenne générale");
        moyenne_text.setBounds(15,6,200,20);
        moyenne_panel.add(moyenne_text);

        moyenne_result = new JLabel("18/20");
        moyenne_result.setBounds(15,33,100,20);
        moyenne_result.setFont(new Font("SansSerif",Font.BOLD,20));
        moyenne_result.setForeground(new Color(0, 99, 19 ));
        moyenne_panel.add(moyenne_result);








        temp_panel =  new JPanel();
        temp_panel.setBounds(510,80,200,70);
        temp_panel.setBackground(Color.white);
        temp_panel.setLayout(null);
        frame.add(temp_panel);

        temp_text = new JLabel("Temps total d'examens");
        temp_text.setBounds(15,6,200,20);
        temp_panel.add(temp_text);

        temp_result = new JLabel("0h");
        temp_result.setBounds(15,33,100,20);
        temp_result.setFont(new Font("SansSerif",Font.BOLD,20));
        temp_result.setForeground(new Color(174, 0, 179 ));
        temp_panel.add(temp_result);





        form_exam = new JPanel();
        form_exam.setBounds(20,180,250,150);
        form_exam.setBackground(Color.white);
        form_exam.setLayout(null);
        frame.add(form_exam);



        text = new JLabel("Commencer un examen");
        text.setBounds(42,4,200,50);
        text.setFont(new Font("Segoe UI",Font.BOLD,15));
        form_exam.add(text);

        code_exam = new JTextField();
        code_exam.setBounds(25,60,200,30);
        form_exam.add(code_exam);

        acceder_btn = new JButton("Accéder a l'examen");
        acceder_btn.setBounds(25,100,200,30);
        acceder_btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        acceder_btn.setForeground(Color.WHITE);
        acceder_btn.setBackground(primaryDark);
        acceder_btn.setFocusPainted(false);
        acceder_btn.setBorderPainted(false);
        acceder_btn.setContentAreaFilled(true);
        acceder_btn.setOpaque(true);
        form_exam.add(acceder_btn);






        frame.setVisible(true);
    }
    public static void main(String[] arg){
        HomePanel homePanel = new HomePanel();
    }
}

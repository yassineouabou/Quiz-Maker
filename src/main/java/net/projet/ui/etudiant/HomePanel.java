package net.projet.ui.etudiant;

import net.projet.entity.Exam;
import net.projet.entity.Result;
import net.projet.entity.User;
import net.projet.exceptions.ResultNotFoundException;
import net.projet.services.ExamService;
import net.projet.services.ResultService;


import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class HomePanel extends JPanel {
    JLabel title,text,user_name,complete_text,complete_result,moyenne_text,moyenne_result,resultLabel;
    JTextField code_exam;
    JButton acceder_btn,resultBtn;
    JPanel form_exam,complete_panel,moyenne_panel,result_panel;
    private ExamService examService;
    private ResultService resultService;

    private final Color primaryDark = new Color(2, 81, 171);
    private final Color backgroundColor = new Color(245, 247, 251);

    public HomePanel(JPanel cardPanel, User user){

        examService = new ExamService();
        resultService = new ResultService();

        this.setSize(800,600);
        this.setBackground(backgroundColor);

        this.setLayout(null);

        title = new JLabel("Bienvenue");
        title.setFont(new Font("Segoe UI",Font.BOLD,25));
        title.setBounds(210,10,200,40);
        this.add(title);

        user_name = new JLabel(user.getPrenom()+" "+user.getNom());
        user_name.setFont(new Font("SansSerif",Font.BOLD,25));
        user_name.setForeground(primaryDark);
        user_name.setBounds(340,10,400,40);
        this.add(user_name);

        complete_panel =  new JPanel();
        complete_panel.setBounds(50,120,300,100);
        complete_panel.setBackground(Color.white);
        complete_panel.setLayout(null);
        this.add(complete_panel);

        int nbrExamSolved = resultService.nbrResultByetudiantId(user.getId());
        complete_text = new JLabel("Examens complétés");
        complete_text.setBounds(80,6,200,25);
        complete_panel.add(complete_text);

        complete_result = new JLabel(String.valueOf(nbrExamSolved));
        complete_result.setBounds(130,50,100,20);
        complete_result.setFont(new Font("SansSerif",Font.BOLD,25));
        complete_result.setForeground(primaryDark);
        complete_panel.add(complete_result);







        moyenne_panel =  new JPanel();
        moyenne_panel.setBounds(400,120,300,100);
        moyenne_panel.setBackground(Color.white);
        moyenne_panel.setLayout(null);
        this.add(moyenne_panel);

        float moyenne = resultService.moyenneGenerale(user.getId());
        moyenne_text = new JLabel("Moyenne générale");
        moyenne_text.setBounds(100,6,200,25);
        moyenne_panel.add(moyenne_text);

        moyenne_result = new JLabel(String.valueOf(moyenne)+"/20");
        moyenne_result.setBounds(110,50,100,20);
        moyenne_result.setFont(new Font("SansSerif",Font.BOLD,25));
        moyenne_result.setForeground(new Color(0, 99, 19 ));
        moyenne_panel.add(moyenne_result);




        form_exam = new JPanel();
        form_exam.setBounds(50,280,300,150);
        form_exam.setBackground(Color.white);
        form_exam.setLayout(null);
        this.add(form_exam);


        ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/quiz.png")));
        Image resizedImage = imageIcon.getImage().getScaledInstance(40,40, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel imageLabel = new JLabel(resizedIcon);
        imageLabel.setBounds(220,9,40,40);
        form_exam.add(imageLabel);

        text = new JLabel("Commencer un examen");
        text.setBounds(45,4,200,50);
        text.setFont(new Font("Segoe UI",Font.BOLD,15));
        form_exam.add(text);

        code_exam = new JTextField();
        code_exam.setBounds(45,60,200,30);
        form_exam.add(code_exam);

        acceder_btn = new JButton("Accéder a l'examen");
        acceder_btn.setBounds(45,100,200,30);
        acceder_btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        acceder_btn.setForeground(Color.WHITE);
        acceder_btn.setBackground(primaryDark);
        acceder_btn.setFocusPainted(false);
        acceder_btn.setBorderPainted(false);
        acceder_btn.setContentAreaFilled(true);
        acceder_btn.setOpaque(true);
        form_exam.add(acceder_btn);

        acceder_btn.addActionListener(e ->{
            Result result=null;
            String codeUnique = code_exam.getText();
            Exam exam = examService.findExamByCodeUnique(codeUnique);
            try{
                result = resultService.findByEtudiantId(user.getId());
            }catch (ResultNotFoundException er){
                System.out.println(er.getMessage());
            }
            //comparer entre exam  et le result de ce exam si true alor exam il est deja submit par ce etudiant
            if(result!=null && exam.getId().equals(result.getExam().getId())){
                JOptionPane.showMessageDialog(this,"Déjà fait cet examen.","warning",JOptionPane.WARNING_MESSAGE);
            }
            else{
                JPanel questionPanel = new QuestionsPanel(cardPanel,codeUnique,user);
                cardPanel.add(questionPanel,"questions");
                CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
                cardLayout.show(cardPanel,"questions");
            }

        });

        result_panel = new JPanel();
        result_panel.setBounds(400,280,300,150);
        result_panel.setBackground(Color.white);
        result_panel.setLayout(null);
        this.add(result_panel);

        resultLabel = new JLabel("Resultat des Exam");
        resultLabel.setBounds(85,0,200,40);
        resultLabel.setFont(new Font("Segoe UI",Font.BOLD,15));
        result_panel.add(resultLabel);


        ImageIcon imageIcon2 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/bonne-note.png")));
        Image resizedImage2 = imageIcon2.getImage().getScaledInstance(60,60, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon2 = new ImageIcon(resizedImage2);
        JLabel imageLabel2 = new JLabel(resizedIcon2);
        imageLabel2.setBounds(120,38,60,60);
        result_panel.add(imageLabel2);


        resultBtn = new JButton("Resultat");
        resultBtn.setBounds(55,110,200,25);
        resultBtn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        resultBtn.setForeground(Color.WHITE);
        resultBtn.setBackground(primaryDark);
        resultBtn.setFocusPainted(false);
        resultBtn.setBorderPainted(false);
        resultBtn.setContentAreaFilled(true);
        resultBtn.setOpaque(true);
        result_panel.add(resultBtn);


        resultBtn.addActionListener(e->{
            ResultPanel resultPanel = new ResultPanel(user,cardPanel);
            cardPanel.add(resultPanel,"result");
            CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
            cardLayout.show(cardPanel,"result");
        });

        ImageIcon img = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/eteindre.png")));
        Image resize = img.getImage().getScaledInstance(40,40, Image.SCALE_SMOOTH);
        ImageIcon resizedImg= new ImageIcon(resize);
        JButton decButton =new JButton(resizedImg);
        decButton.setBounds(700, 10, 42, 42);
        decButton.addActionListener(e -> {
            CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
            cardLayout.show(cardPanel, "login");
        });
        add(decButton);



    }


}

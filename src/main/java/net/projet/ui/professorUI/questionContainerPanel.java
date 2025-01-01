package net.projet.ui.professorUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

public class questionContainerPanel extends JPanel {

    JLabel l_question,l_rightanswer;
    JTextField question,rightanswer;

    ArrayList<JLabel> l_options;
    ArrayList<JTextField> options;

    public void setQuestionLabel(String question){
        this.l_question.setText(question);
    }
    public JLabel getQuestionLabel(){
        return this.l_question;
    }

    public questionContainerPanel(int questionNumber){
        this.setPreferredSize(new Dimension(800,400));
        this.setLayout(null);

        Font font = new Font("Serif", Font.BOLD , 20);

        l_question=new JLabel("Question "+questionNumber+": ");
        l_question.setBounds(150,50,130,30);
        l_question.setFont(font);
        question=new JTextField();
        question.setBorder(new EmptyBorder(0,0,0,0));
        question.setBounds(300,50,340,30);

        l_rightanswer=new JLabel("Correct Answer: ");
        l_rightanswer.setBounds(40,100,150,30);
        l_rightanswer.setFont(font);
        rightanswer=new JTextField();
        rightanswer.setBounds(210,100,300,30);
        rightanswer.setBorder(new EmptyBorder(0,0,0,0));

        this.add(l_question);
        this.add(question);
        this.add(l_rightanswer);
        this.add(rightanswer);




        l_options=new ArrayList<>(3);
        options=new ArrayList<>(3);


        l_options.add(new JLabel("option 1: "));
        l_options.get(0).setBounds(100,150,100,30);
        l_options.get(0).setFont(font);
        l_options.add(new JLabel("option 2: "));
        l_options.get(1).setBounds(100,200,100,30);
        l_options.get(1).setFont(font);
        l_options.add(new JLabel("option 3: "));
        l_options.get(2).setBounds(100,250,100,30);
        l_options.get(2).setFont(font);

        options.add(new JTextField());
        options.get(0).setBounds(210,150,300,30);
        options.get(0).setBorder(new EmptyBorder(0,0,0,0));
        options.add(new JTextField());
        options.get(1).setBounds(210,200,300,30);
        options.get(1).setBorder(new EmptyBorder(0,0,0,0));
        options.add(new JTextField());
        options.get(2).setBounds(210,250,300,30);
        options.get(2).setBorder(new EmptyBorder(0,0,0,0));
        this.add(l_options.get(0));
        this.add(l_options.get(1));
        this.add(l_options.get(2));
        this.add(options.get(0));
        this.add(options.get(1));
        this.add(options.get(2));
        this.setSize(800,400);

        for (int i=0;i<3;i++){
            this.add(l_options.get(i));
            this.add(options.get(i));
        }




        for(Component c:this.getComponents()){
            if(c instanceof JTextField){
                JTextField textField = (JTextField) c;
                textField.addFocusListener(new java.awt.event.FocusAdapter() {
                    @Override
                    public void focusGained(java.awt.event.FocusEvent evt) {
                        textField.setBorder(new LineBorder(Color.cyan,2));
                    }
                    @Override
                    public void focusLost(java.awt.event.FocusEvent evt) {
                        textField.setBorder(new EmptyBorder(0,0,0,0));
                    }
                });
            }
        }



    }

}

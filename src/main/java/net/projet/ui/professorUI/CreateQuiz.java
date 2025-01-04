package net.projet.ui.professorUI;

import net.projet.entity.Exam;
import net.projet.entity.Question;
import net.projet.entity.User;
import net.projet.services.ExamService;
import net.projet.services.QuestionService;

import java.awt.datatransfer.StringSelection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class CreateQuiz extends JPanel {
    User prof;
    JTextField title;
    JLabel l_title;
    JPanel titlePanel,questionsContainer, ControlPanel;
    JButton addQuestionBtn,deleteQuestionBtn,CancelBtn,SubmitBtn,nextBtn,previousBtn;

    CardLayout questionscards;
    //the questions array variable is for taking data from the gui components
    ArrayList<Question> questions;
    int questionNumber=0,currentQuestionNumber=0;
    //this is an array of questions each in its panel, later to be used to import data from components;
    ArrayList<questionContainerPanel> questionPanels;

    public CreateQuiz(JPanel parent, CardLayout cards, User prof){

        this.prof= prof;
        Font font = new Font("Serif", Font.BOLD , 20);

        questionPanels=new ArrayList<>();

        questionscards=new CardLayout();
        questionsContainer=new JPanel(questionscards);
        questionsContainer.setPreferredSize(new Dimension(800,400));
        questionsContainer.setBackground(Color.MAGENTA);

        questions=new ArrayList<>();





        this.setLayout(new BorderLayout());

        this.setBackground(Color.white);
        l_title=new JLabel("Quiz Title: ");
        l_title.setFont(font);
        title=new JTextField("");
        title.setPreferredSize(new Dimension(200, 30));
        title.setBorder(new EmptyBorder(0,0,0,0));
        titlePanel=new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,10));
        titlePanel.setPreferredSize(new Dimension(800,50));

        titlePanel.add(l_title);
        titlePanel.add(title);

        this.add(titlePanel,BorderLayout.NORTH);
        this.add(questionsContainer,BorderLayout.CENTER);



        //time
        SpinnerDateModel timeModel = new SpinnerDateModel();
        JSpinner timeSpinner = new JSpinner(timeModel);

        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
        timeSpinner.setEditor(timeEditor);
        timeSpinner.setValue(new Date());

        timeSpinner.setBounds(500,0,80,30);

        JLabel jLabel =new JLabel("Select Time:");
        jLabel.setBounds(400,0,100,30);
        titlePanel.add(jLabel);
        titlePanel.add(timeSpinner);




        addQuestionBtn =new JButton("Add A question");
        addQuestionBtn.setBounds(630,0,140,35);
        addQuestionBtn.setBackground(new Color(0, 109, 229));
        addQuestionBtn.setForeground(Color.white);
        addQuestionBtn.setFont(new Font("Serif", Font.BOLD, 15));
        addQuestionBtn.setBorder(new EmptyBorder(0,0,0,0));
        addQuestionBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addQuestionfunction();
            }
        });

        deleteQuestionBtn=new JButton("Delete Question");
        deleteQuestionBtn.setBounds(40,0,140,35);
        deleteQuestionBtn.setBackground(new Color(255, 114, 0));
        deleteQuestionBtn.setForeground(Color.white);
        deleteQuestionBtn.setFont(new Font("Serif", Font.BOLD, 15));
        deleteQuestionBtn.setBorder(new EmptyBorder(0,0,0,0));
        deleteQuestionBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(questionNumber>1){
                    deleteQuestionfunction();
                }

            }
        });

        previousBtn=new JButton("Previous");
        previousBtn.setBounds(290,0,100,35);
        previousBtn.setForeground(Color.white);
        previousBtn.setBorder(new EmptyBorder(0,0,0,0));
        previousBtn.setBackground(new Color(40, 188, 77));
        previousBtn.setFont(new Font("Serif", Font.BOLD, 15));
        previousBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cards = (CardLayout) questionsContainer.getLayout();
                cards.previous(questionsContainer);
                currentQuestionNumber= currentQuestionNumber==1?questionNumber:currentQuestionNumber-1;
                System.out.println("minus: "+currentQuestionNumber);
            }
        });

        nextBtn=new JButton("Next");
        nextBtn.setBounds(410,0,100,35);
        nextBtn.setForeground(Color.white);
        nextBtn.setBorder(new EmptyBorder(0,0,0,0));
        nextBtn.setBackground(new Color(40, 188, 77));
        nextBtn.setFont(new Font("Serif", Font.BOLD, 15));
        nextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cards = (CardLayout) questionsContainer.getLayout();
                cards.next(questionsContainer);
                currentQuestionNumber= currentQuestionNumber==questionNumber?1:currentQuestionNumber+1;
                System.out.println("plus: "+currentQuestionNumber);
            }
        });

        CancelBtn=new JButton("Cancel");
        CancelBtn.setBounds(40,120,100,35);
        CancelBtn.setForeground(Color.white);
        CancelBtn.setBackground(new Color(213, 52, 52));
        CancelBtn.setBorder(new EmptyBorder(0,0,0,0));
        CancelBtn.setFont(new Font("Serif", Font.BOLD, 15));
        CancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                cards.show(parent,"profhomepage");
            }
        });

        SubmitBtn=new JButton("Submit");
        SubmitBtn.setBounds(630,120,140,35);
        SubmitBtn.setForeground(Color.white);
        SubmitBtn.setBackground(new Color(0, 109, 229));
        SubmitBtn.setBorder(new EmptyBorder(0,0,0,0));
        SubmitBtn.setFont(new Font("Serif", Font.BOLD, 15));
        SubmitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    submitQuiz(timeSpinner);
                    cards.show(parent,"profhomepage");
                    questions.clear();
                    questionPanels.clear();
                    questionNumber=0;
                    currentQuestionNumber=0;
                } catch (SQLException throwables) {
                    System.out.println(throwables.getMessage());
                }
            }
        });

        ControlPanel =new JPanel();
        ControlPanel.setLayout(null);
        ControlPanel.setPreferredSize(new Dimension(800,200));

        addQuestionfunction();


        ControlPanel.add(previousBtn);
        ControlPanel.add(nextBtn);
        ControlPanel.add(addQuestionBtn);
        ControlPanel.add(CancelBtn);
        ControlPanel.add(SubmitBtn);
        ControlPanel.add(deleteQuestionBtn);
        ControlPanel.setBorder(new EmptyBorder(0,10,0,10));



        this.add(ControlPanel,BorderLayout.SOUTH);




    }
    public void addQuestionfunction(){
        System.out.println("new question");
        questionNumber++;
        questionPanels.add(new questionContainerPanel(questionNumber));
        questionsContainer.add(questionPanels.get(questionPanels.size()-1),"question"+questionNumber);
        questionsContainer.revalidate();
        questionsContainer.repaint();
        questionscards.show(questionsContainer,"question"+questionNumber);

        currentQuestionNumber=questionNumber;
    }

    public void deleteQuestionfunction(){
        System.out.println("delete question: "+currentQuestionNumber);
        System.out.println("question number before: "+questionNumber);
        System.out.println("current question number before: "+currentQuestionNumber);
        questionsContainer.remove(questionPanels.get(currentQuestionNumber-1));
        questionPanels.remove(currentQuestionNumber-1);
        currentQuestionNumber--;

        questionNumber--;
        System.out.println("question number: "+questionNumber);
        System.out.println("current question number: "+currentQuestionNumber);
        for(int i=currentQuestionNumber;i<questionPanels.size();i++){
            System.out.println("i: "+i);
            questionPanels.get(i).setQuestionLabel("Question "+(i+1)+": ");
        }
        questionscards.previous(questionsContainer);
        questionsContainer.revalidate();
    }
    public void submitQuiz(JSpinner timeSpinner) throws SQLException {

        ExamService examService=new ExamService();
        QuestionService questionService=new QuestionService();

        String codeUnique = UUID.randomUUID().toString().substring(0,20);

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date selectedTime = (Date) timeSpinner.getValue();
        String formattedTime = formatter.format(selectedTime);

        Exam exam=new Exam(title.getText(),this.prof,formattedTime,codeUnique);

        Long examId = examService.createExam(exam);
        exam.setId(examId);

        for (questionContainerPanel q:questionPanels){
            String CorrectAnswer=q.rightanswer.getText();
            StringBuilder options = new StringBuilder();
            options.append(CorrectAnswer).append('#');
            for (JTextField option:q.options){
                options.append(option.getText()).append('#');
            }
            String questionText = q.question.getText();
            Question question=new Question(questionText,options.toString(),exam);

            questionService.createQuestion(question);
            questions.add(question);

        }
        int rep = JOptionPane.showConfirmDialog(this,
                "Cliquez sur Yes pour copier :\n"+codeUnique,
                "Exam Code",
                JOptionPane.YES_NO_OPTION);

        if(rep==JOptionPane.YES_OPTION){
            StringSelection stringSelection = new StringSelection(codeUnique);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection,null);

            JOptionPane.showMessageDialog(
                    this,
                    "Code copied to clipboard!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }

    }
}

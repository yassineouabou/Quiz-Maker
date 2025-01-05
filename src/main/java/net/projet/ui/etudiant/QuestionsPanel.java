package net.projet.ui.etudiant;

import net.projet.entity.EtudiantReponse;
import net.projet.entity.Exam;
import net.projet.entity.Question;
import net.projet.entity.User;
import net.projet.enums.Roles;
import net.projet.services.ExamService;
import net.projet.services.QuestionService;
import net.projet.services.ReponseService;
import net.projet.ui.professorUI.ProfessorInterface;
import net.projet.util.DataBaseConnection;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;


public class QuestionsPanel extends JPanel {

    JLabel title,temps_label,temps;
    JPanel cardPanelQuestions;
    JButton next, previous, submit;
    HashMap<Long, ButtonGroup> buttonGroups;
    private static int nbrQuestion = 0;
    private int remainingSeconds;
    Timer timer;

    private final Color primaryColor = new Color(79, 120, 229);
    private final Color primaryDark = new Color(2, 81, 171);
    private final Color backgroundColor = new Color(245, 247, 251);
    private final Color textColor = new Color(55, 65, 81);

    public QuestionsPanel(JPanel cardPanel, String codeUnique, User user,JFrame parentFrame) {


        ReponseService reponseService = new ReponseService();
        ExamService examService = new ExamService();
        QuestionService questionService = new QuestionService();

        Exam exam = examService.findExamByCodeUnique(codeUnique);
        if (exam == null || exam.getQuestions().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No questions available for this exam.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int rep= JOptionPane.showConfirmDialog(this,
                "Le temps d'examen:"+exam.getTemps() +"\nVous ne pouvez pas sortir de cette fenêtre avant la fin de l'examen.",
                "Avertissement",
                JOptionPane.DEFAULT_OPTION);

        if(rep==0){
            startCountdown(reponseService, questionService, user,cardPanel);
        }


        this.setSize(800, 600);
        this.setLayout(null);
        this.setBackground(backgroundColor);

        title = new JLabel("QCM "+exam.getNom());
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setForeground(textColor);
        title.setBounds(200, 10, 200, 50);
        this.add(title);

        temps_label = new JLabel("Time: ");
        temps_label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        temps_label.setBounds(450,27,70,20);
        this.add(temps_label);

        temps = new JLabel(exam.getTemps());
        temps.setFont(new Font("Arial", Font.BOLD, 20));
        temps.setBounds(550,27,100,20);
        this.add(temps);


        remainingSeconds = parseTimeToSeconds(exam.getTemps());



        cardPanelQuestions = new JPanel(new CardLayout());
        cardPanelQuestions.setBorder(BorderFactory.createLineBorder(primaryDark, 2));
        cardPanelQuestions.setBounds(90, 100, 600, 300);
        cardPanelQuestions.setBackground(backgroundColor);

        buttonGroups = new HashMap<>();
        int idPanel = 0;

        for (Question question : exam.getQuestions()) {
            JPanel panel = createQuestionPanel(question);
            cardPanelQuestions.add(panel, "Panel" + idPanel++);
        }

        this.add(cardPanelQuestions);


        initializeNavigationButtons(exam.getQuestions().size());



        //cardLyout for Question options
        CardLayout cl = (CardLayout) cardPanelQuestions.getLayout();
        cl.show(cardPanelQuestions, "Panel0");


        next.addActionListener(e -> {
            navigateToNextQuestion(cl, exam.getQuestions().size());
        });

        previous.addActionListener(e -> {
            navigateToPreviousQuestion(cl);
        });

        submit.addActionListener(e -> {
            handleSubmission(reponseService, questionService, user,cardPanel);
        });


        parentFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowDeactivated(WindowEvent e) {
                if(!parentFrame.isFocused()){
                    CardLayout cl = (CardLayout) cardPanel.getLayout();

                    // Get the currently visible card (panel) from the card layout
                    Component currentPanel = getCurrentVisiblePanel(cardPanel);

                    // Check if the current visible panel is an instance of QuestionsPanel
                    if (currentPanel instanceof QuestionsPanel) {
                        System.out.println("dkhl");
                        timer.stop();
                        handleSubmission(reponseService, questionService, user,cardPanel);

                    }
                }
            }
        });


    }




    private JPanel createQuestionPanel(Question question) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.white);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel questionLabel = new JLabel(question.getText());
        questionLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        questionLabel.setForeground(textColor);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(questionLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));

        ButtonGroup buttonGroup = new ButtonGroup();
        String[] options = question.getOptions().split("#");
        List<String> optionList = Arrays.asList(options);
        Collections.shuffle(optionList);
            for (String option : optionList) {
                JRadioButton radioButton = new JRadioButton(option);
                radioButton.setBackground(Color.white);
                radioButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
                radioButton.setForeground(textColor);
                buttonGroup.add(radioButton);
                panel.add(radioButton);
                panel.add(Box.createRigidArea(new Dimension(0, 10)));
            }

        buttonGroups.put(question.getId(), buttonGroup);
        return panel;
    }

    private void initializeNavigationButtons(int totalQuestions) {
        next = new JButton("Next");
        next.setBounds(350, 460, 100, 30);
        next.setBackground(primaryColor);
        next.setForeground(Color.WHITE);
        next.setFocusPainted(false);
        this.add(next);

        previous = new JButton("Previous");
        previous.setBounds(150, 460, 100, 30);
        previous.setBackground(primaryColor);
        previous.setForeground(Color.WHITE);
        previous.setFocusPainted(false);
        previous.setEnabled(false);
        this.add(previous);

        submit = new JButton("Submit");
        submit.setBounds(550, 460, 100, 30);
        submit.setBackground(Color.green);
        submit.setForeground(Color.WHITE);
        submit.setFocusPainted(false);
        submit.setVisible(totalQuestions==1);
        this.add(submit);
    }


    private void navigateToNextQuestion(CardLayout cl, int totalQuestions) {
        nbrQuestion++;
        next.setEnabled(nbrQuestion < totalQuestions - 1);
        previous.setEnabled(true);
        submit.setVisible(nbrQuestion == totalQuestions - 1 || totalQuestions==1);

        cl.show(cardPanelQuestions, "Panel" + nbrQuestion);
    }

    private void navigateToPreviousQuestion(CardLayout cl) {
        nbrQuestion--;
        previous.setEnabled(nbrQuestion > 0);
        next.setEnabled(true);
        submit.setVisible(false);
        cl.show(cardPanelQuestions, "Panel" + nbrQuestion);
    }


    private void handleSubmission(ReponseService reponseService, QuestionService questionService, User user,JPanel cardPanel) {
        for (Map.Entry<Long, ButtonGroup> entry : buttonGroups.entrySet()) {
            Long questionId = entry.getKey();
            ButtonGroup group = entry.getValue();
            String selectedOption = null;

            for (AbstractButton button : Collections.list(group.getElements())) {
                if (button.isSelected()) {
                    selectedOption = button.getText();
                    break;
                }
            }
            if (selectedOption == null && timer.isRunning()) {
                JOptionPane.showMessageDialog(this, "Please answer all questions.", "Incomplete Submission", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Question question = questionService.findById(questionId);
            EtudiantReponse response = new EtudiantReponse(user, question, selectedOption);
            reponseService.addReponse(response);
        }

        //cardLayout Principal
        timer.stop();
        JOptionPane.showMessageDialog(this, "Exam submitted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
        cardLayout.show(cardPanel,"home");
    }


    // Démarrer le compte à rebours
    private void startCountdown(ReponseService reponseService, QuestionService questionService, User user,JPanel cardPanel) {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (remainingSeconds > 0) {
                    remainingSeconds--;
                    temps.setText(formatTime(remainingSeconds));
                } else {
                    timer.stop();
                    temps.setText("Terminé!");
                    handleSubmission(reponseService, questionService, user,cardPanel);

                }
            }
        });
        timer.start();
    }

    // Convertit un String HH:mm:ss en nombre total de secondes
    private int parseTimeToSeconds(String time) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        int seconds = Integer.parseInt(parts[2]);
        return hours * 3600 + minutes * 60 + seconds;
    }

    // Formate le temps en secondes au format HH:mm:ss
    private String formatTime(int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }

    private Component getCurrentVisiblePanel(JPanel cardPanel) {
        CardLayout cl =(CardLayout) cardPanel.getLayout();
        // Get the current card (panel) by showing it
        for (Component comp : cardPanel.getComponents()) {
            if (comp.isVisible()) {
                return comp; // Return the currently visible component
            }
        }
        return null; // If no panel is visible, return null
    }


}



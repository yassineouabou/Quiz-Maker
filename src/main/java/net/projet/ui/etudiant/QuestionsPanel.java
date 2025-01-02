package net.projet.ui.etudiant;

import net.projet.entity.EtudiantReponse;
import net.projet.entity.Exam;
import net.projet.entity.Question;
import net.projet.entity.User;
import net.projet.services.ExamService;
import net.projet.services.QuestionService;
import net.projet.services.ReponseService;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class QuestionsPanel extends JPanel {

    JLabel title;
    JPanel cardPanelQuestions;
    JButton next, previous, submit;
    HashMap<Long, ButtonGroup> buttonGroups;
    private static int nbrQuestion = 0;

    private final Color primaryColor = new Color(79, 120, 229);
    private final Color primaryDark = new Color(2, 81, 171);
    private final Color backgroundColor = new Color(245, 247, 251);
    private final Color textColor = new Color(55, 65, 81);

    public QuestionsPanel(JPanel cardPanel, String codeUnique, User user) {


        ReponseService reponseService = new ReponseService();
        ExamService examService = new ExamService();
        QuestionService questionService = new QuestionService();


        Exam exam = examService.findExamByCodeUnique(codeUnique);
        if (exam == null || exam.getQuestions().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No questions available for this exam.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.setSize(800, 600);
        this.setLayout(null);
        this.setBackground(backgroundColor);


        title = new JLabel("QCM "+exam.getNom());
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setForeground(textColor);
        title.setBounds(320, 10, 200, 50);
        this.add(title);

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


        CardLayout cl = (CardLayout) cardPanelQuestions.getLayout();
        cl.show(cardPanelQuestions, "Panel0");


        next.addActionListener(e -> {
            navigateToNextQuestion(cl, exam.getQuestions().size());
        });

        previous.addActionListener(e -> {
            navigateToPreviousQuestion(cl);
        });

        submit.addActionListener(e -> {
            handleSubmission(reponseService, questionService, user);
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
        submit.setVisible(false);
        this.add(submit);
    }


    private void navigateToNextQuestion(CardLayout cl, int totalQuestions) {
        nbrQuestion++;
        next.setEnabled(nbrQuestion < totalQuestions - 1);
        previous.setEnabled(true);
        submit.setVisible(nbrQuestion == totalQuestions - 1);
        cl.show(cardPanelQuestions, "Panel" + nbrQuestion);
    }

    private void navigateToPreviousQuestion(CardLayout cl) {
        nbrQuestion--;
        previous.setEnabled(nbrQuestion > 0);
        next.setEnabled(true);
        submit.setVisible(false);
        cl.show(cardPanelQuestions, "Panel" + nbrQuestion);
    }


    private void handleSubmission(ReponseService reponseService, QuestionService questionService, User user) {
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

            if (selectedOption == null) {
                JOptionPane.showMessageDialog(this, "Please answer all questions.", "Incomplete Submission", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Question question = questionService.findById(questionId);
            EtudiantReponse response = new EtudiantReponse(user, question, selectedOption);
            reponseService.addReponse(response);
        }

        JOptionPane.showMessageDialog(this, "Exam submitted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }




}



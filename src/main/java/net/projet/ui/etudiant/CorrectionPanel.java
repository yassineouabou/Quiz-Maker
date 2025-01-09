package net.projet.ui.etudiant;

import net.projet.entity.EtudiantReponse;
import net.projet.entity.Question;
import net.projet.entity.Result;
import net.projet.entity.User;
import net.projet.services.ReponseService;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class CorrectionPanel extends JPanel {
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 250);
    private static final Color PRIMARY_COLOR = new Color(67, 99, 216);
    private static final Color CORRECT_COLOR = new Color(46, 174, 96);
    private static final Color INCORRECT_COLOR = new Color(239, 68, 68);
    private static final int PADDING = 20;

    private ReponseService reponseService;

    public CorrectionPanel(Result result, User user, JPanel cardPanel) {
        reponseService = new ReponseService();

        setLayout(null);
        setBackground(BACKGROUND_COLOR);
        setSize(800, 600);

        JButton returnButton = new JButton("Retour");
        returnButton.setBounds(20, 15, 100, 30);
        returnButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        returnButton.setBackground(new Color(255, 255, 255));
        returnButton.setForeground(PRIMARY_COLOR);
        returnButton.setBorder(new LineBorder(PRIMARY_COLOR, 1, true));
        returnButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        returnButton.setFocusPainted(false);


        returnButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) cardPanel.getLayout();
            cl.show(cardPanel, "result");
        });

        // En-tête
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(null);
        headerPanel.setBounds(0, 0, 800, 60);
        headerPanel.setBackground(PRIMARY_COLOR);

        JLabel titleLabel = new JLabel("Correction: " + result.getExam().getNom());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(20, 15, 760, 30);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        headerPanel.add(returnButton);
        headerPanel.add(titleLabel);
        add(headerPanel);

        // Panel principal pour les questions
        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(null);
        mainContainer.setBackground(BACKGROUND_COLOR);

        int yPosition = 10;
        int questionNumber = 1;

        for (Question question : result.getExam().getQuestions()) {
            String correctAnswer = question.getOptions().split("#")[0];
            EtudiantReponse etudiantReponse = reponseService.getAllReponseByEtudiantId(user.getId(), question.getId());
            String etudiantAnswer = etudiantReponse.getSelectedOption();
            boolean isCorrect = etudiantAnswer.equals(correctAnswer);

            // Panel pour chaque question
            JPanel questionPanel = new JPanel();
            questionPanel.setLayout(null);
            questionPanel.setBounds(20, yPosition, 640, 120);
            questionPanel.setBackground(Color.WHITE);
            questionPanel.setBorder(new LineBorder(new Color(230, 230, 230), 1, true));

            // Numéro et texte de la question
            JLabel questionLabel = new JLabel("Question " + questionNumber + ": " + question.getText());
            questionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            questionLabel.setBounds(15, 10, 610, 30);
            questionPanel.add(questionLabel);

            // Réponse de l'étudiant
            JLabel studentAnswerLabel = new JLabel("Votre réponse: " + etudiantAnswer);
            studentAnswerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            studentAnswerLabel.setForeground(isCorrect ? CORRECT_COLOR : INCORRECT_COLOR);
            studentAnswerLabel.setBounds(30, 45, 580, 25);
            questionPanel.add(studentAnswerLabel);

            // Réponse correcte
            JLabel correctAnswerLabel = new JLabel("Réponse correcte: " + correctAnswer);
            correctAnswerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            correctAnswerLabel.setForeground(PRIMARY_COLOR);
            correctAnswerLabel.setBounds(30, 75, 580, 25);
            questionPanel.add(correctAnswerLabel);

            mainContainer.add(questionPanel);

            yPosition += 135; // Espace entre les questions
            questionNumber++;
        }

        mainContainer.setPreferredSize(new Dimension(680, yPosition + 20));

        // ScrollPane pour le défilement
        JScrollPane scrollPane = new JScrollPane(mainContainer);
        scrollPane.setBounds(40, 80, 720, 480);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(BACKGROUND_COLOR);

        add(scrollPane);
    }
}
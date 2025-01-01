package net.projet.ui.etudiant;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class QuestionsPanel {

    JLabel title;
    JPanel cardPanel;
    JButton next, previous;
    private static int nbrQuestion = 0;

    // Define the colors
    private final Color primaryColor = new Color(79, 120, 229);
    private final Color primaryDark = new Color(2, 81, 171);
    private final Color backgroundColor = new Color(245, 247, 251);
    private final Color textColor = new Color(55, 65, 81);

    public QuestionsPanel() {
        JFrame frame = new JFrame();
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.getContentPane().setBackground(backgroundColor);

        title = new JLabel("QCM Exam");
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setForeground(textColor);
        title.setBounds(320, 10, 200, 50);
        frame.add(title);

        cardPanel = new JPanel(new CardLayout());
        cardPanel.setBorder(BorderFactory.createLineBorder(primaryDark, 2));
        cardPanel.setBounds(90, 100, 600, 300);
        cardPanel.setBackground(backgroundColor);

        for (int i = 0; i < 5; i++) {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBackground(Color.white);

            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Add padding above the question label
            panel.add(Box.createRigidArea(new Dimension(0, 10)));

            JLabel questionLabel = new JLabel("Question " + (i + 1));
            questionLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
            questionLabel.setForeground(textColor);
            questionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            questionLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
            panel.add(questionLabel);

            panel.add(Box.createRigidArea(new Dimension(0, 30)));

            ButtonGroup buttonGroup = new ButtonGroup();
            for (int j = 0; j < 3; j++) {
                JRadioButton jRadioButton = new JRadioButton("Option " + (j + 1));
                jRadioButton.setBackground(Color.white);
                jRadioButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
                jRadioButton.setPreferredSize(new Dimension(200, 30));
                jRadioButton.setForeground(textColor);
                buttonGroup.add(jRadioButton);
                panel.add(jRadioButton);

                if (j < 2) {
                    panel.add(Box.createRigidArea(new Dimension(0, 10)));
                }
            }

            String panelId = "Panel" + i;
            cardPanel.add(panel, panelId);
        }

        next = new JButton("Next");
        next.setBounds(450, 460, 100, 30);
        next.setBackground(primaryColor);
        next.setForeground(Color.WHITE);
        next.setFocusPainted(false);
        frame.add(next);

        previous = new JButton("Previous");
        previous.setBounds(250, 460, 100, 30);
        previous.setBackground(primaryColor);
        previous.setForeground(Color.WHITE);
        previous.setFocusPainted(false);
        previous.setEnabled(false);
        frame.add(previous);

        CardLayout cl = (CardLayout) cardPanel.getLayout();
        cl.show(cardPanel, "Panel0");

        frame.add(cardPanel);
        frame.setVisible(true);

        next.addActionListener(e -> {
            nbrQuestion++;
            cl.show(cardPanel, "Panel" + nbrQuestion);
            if (nbrQuestion == 4)
                next.setEnabled(false);
            else
                previous.setEnabled(true);
        });

        previous.addActionListener(e -> {
            nbrQuestion--;
            cl.show(cardPanel, "Panel" + nbrQuestion);
            if (nbrQuestion == 0)
                previous.setEnabled(false);
            else
                next.setEnabled(true);
        });
    }

    public static void main(String[] arg) {
        new QuestionsPanel();
    }
}

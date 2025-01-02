package net.projet.ui.login;

import net.projet.entity.User;
import net.projet.enums.Roles;
import net.projet.services.UserService;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.LineBorder;


public class InscrirePanel extends JPanel {
    private final UserService userService;
    private final Color primaryColor = new Color(79, 120, 229);
    private final Color primaryDark = new Color(2, 81, 171);
    private final Color backgroundColor = new Color(245, 247, 251);
    private final Color textColor = new Color(55, 65, 81);

    public InscrirePanel(JPanel cardPanel) {
        userService = new UserService();

        this.setBounds(0, 0, 800, 600);
        this.setLayout(null);
        this.setBackground(backgroundColor);


        JLabel title = new JLabel("Inscription");
        title.setFont(new Font("Segoe UI", Font.BOLD, 35));
        title.setForeground(primaryDark);
        title.setBounds(300, 30, 200, 40);
        this.add(title);


        JLabel labelNom = new JLabel("Nom :");
        labelNom.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        labelNom.setForeground(primaryColor);
        labelNom.setBounds(100, 100, 100, 30);
        this.add(labelNom);

        JTextField fieldNom = new JTextField(20);
        fieldNom.setBounds(220, 100, 400, 30);
        fieldNom.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        fieldNom.setBorder(new LineBorder(textColor, 1));
        this.add(fieldNom);


        JLabel labelPrenom = new JLabel("Prénom :");
        labelPrenom.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        labelPrenom.setForeground(primaryColor);
        labelPrenom.setBounds(100, 150, 100, 30);
        this.add(labelPrenom);

        JTextField fieldPrenom = new JTextField(20);
        fieldPrenom.setBounds(220, 150, 400, 30);
        fieldPrenom.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        fieldPrenom.setBorder(new LineBorder(textColor, 1));
        this.add(fieldPrenom);


        JLabel labelEmail = new JLabel("Email :");
        labelEmail.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        labelEmail.setForeground(primaryColor);
        labelEmail.setBounds(100, 200, 100, 30);
        this.add(labelEmail);

        JTextField fieldEmail = new JTextField(20);
        fieldEmail.setBounds(220, 200, 400, 30);
        fieldEmail.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        fieldEmail.setBorder(new LineBorder(textColor, 1));
        this.add(fieldEmail);


        JLabel labelPassword = new JLabel("Mot de passe :");
        labelPassword.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        labelPassword.setForeground(primaryColor);
        labelPassword.setBounds(100, 250, 120, 30);
        this.add(labelPassword);

        JPasswordField fieldPassword = new JPasswordField(20);
        fieldPassword.setBounds(220, 250, 400, 30);
        fieldPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        fieldPassword.setBorder(new LineBorder(textColor, 1));
        this.add(fieldPassword);


        JLabel labelRole = new JLabel("Rôle :");
        labelRole.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        labelRole.setForeground(primaryColor);
        labelRole.setBounds(100, 300, 100, 30);
        this.add(labelRole);

        JComboBox<String> comboRole = new JComboBox<>(new String[]{"PROFESSEUR", "ETUDIANT"});
        comboRole.setBounds(220, 300, 400, 30);
        comboRole.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        this.add(comboRole);

        // Button
        JButton buttonInscrire = new JButton("S'inscrire");
        buttonInscrire.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        buttonInscrire.setForeground(Color.WHITE);
        buttonInscrire.setBackground(primaryDark);
        buttonInscrire.setFocusPainted(false);
        buttonInscrire.setBorderPainted(false);
        buttonInscrire.setContentAreaFilled(true);
        buttonInscrire.setOpaque(true);
        buttonInscrire.setBounds(320, 380, 150, 40);
        this.add(buttonInscrire);

        // Button Action Listener
        buttonInscrire.addActionListener(e -> {
            char[] password = fieldPassword.getPassword();
            User user = new User(fieldNom.getText(), fieldPrenom.getText(), String.valueOf(password),
                    fieldEmail.getText(), Roles.valueOf(String.valueOf(comboRole.getSelectedItem())));

            if (fieldNom.getText().isEmpty() || fieldPrenom.getText().isEmpty() || String.valueOf(password).isEmpty()
                    || fieldEmail.getText().isEmpty() || String.valueOf(comboRole.getSelectedItem()).isEmpty()) {
                JOptionPane.showMessageDialog(this, "Remplir tous les champs", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (userService.addUser(user)) {
                JOptionPane.showMessageDialog(this, "Compte ajouté avec succès", "Inscription", JOptionPane.INFORMATION_MESSAGE);
                CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
                cardLayout.show(cardPanel,"login");
            } else {
                JOptionPane.showMessageDialog(this, "Erreur dans l'ajout de compte", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}

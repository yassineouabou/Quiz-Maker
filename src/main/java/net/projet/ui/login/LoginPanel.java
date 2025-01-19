package net.projet.ui.login;

import net.projet.entity.User;
import net.projet.enums.Roles;
import net.projet.exceptions.UserNotFoundException;
import net.projet.services.UserService;
import net.projet.ui.etudiant.HomePanel;
import net.projet.ui.professorUI.ProfessorInterface;
import net.projet.util.DataBaseConnection;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

public class LoginPanel extends JPanel {
    private JLabel email_label, password_label;
    private JTextField email_text;
    private JPasswordField password_text;
    private JButton connecter_btn, create_btn;
    private final UserService userService;

    public LoginPanel(JPanel cardPanel,JFrame parentFrame) {
        userService = new UserService();

        this.setBounds(0, 0, 800, 600);
        this.setLayout(null);
        this.setBackground(new Color(245, 247, 251));

        JLabel title = new JLabel("Connexion");
        title.setFont(new Font("Segoe UI", Font.BOLD, 40));
        title.setForeground(new Color(79, 120, 229));
        title.setBounds(270, 50, 200, 40);
        this.add(title);

        email_label = new JLabel("Email :");
        email_label.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        email_label.setForeground(new Color(55, 65, 81));
        email_label.setBounds(100, 150, 100, 30);
        this.add(email_label);

        email_text = new JTextField();
        email_text.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        email_text.setBorder(new LineBorder(new Color(200, 200, 200), 1));
        email_text.setBounds(250, 150, 350, 35);
        this.add(email_text);

        password_label = new JLabel("Mot de passe :");
        password_label.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        password_label.setForeground(new Color(55, 65, 81));
        password_label.setBounds(100, 200, 150, 30);
        this.add(password_label);

        password_text = new JPasswordField();
        password_text.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        password_text.setBorder(new LineBorder(new Color(200, 200, 200), 1));
        password_text.setBounds(250, 200, 350, 35);
        this.add(password_text);

        connecter_btn = new JButton("Se Connecter");
        connecter_btn.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        connecter_btn.setForeground(Color.WHITE);
        connecter_btn.setBackground(new Color(79, 120, 229));
        connecter_btn.setFocusPainted(false);
        connecter_btn.setBorderPainted(false);
        connecter_btn.setBounds(150, 300, 150, 40);
        this.add(connecter_btn);

        create_btn = new JButton("S'inscrire");
        create_btn.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        create_btn.setForeground(Color.WHITE);
        create_btn.setBackground(new Color(2, 81, 171));
        create_btn.setFocusPainted(false);
        create_btn.setBorderPainted(false);
        create_btn.setBounds(420, 300, 150, 40);
        this.add(create_btn);


        CardLayout cl = (CardLayout) cardPanel.getLayout();
        create_btn.addActionListener(e -> cl.show(cardPanel, "inscrire"));

        connecter_btn.addActionListener(e -> {
            String email = email_text.getText();
            char[] pwd = password_text.getPassword();
            String password = String.valueOf(pwd);

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Remplir tous les champs", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    User user = userService.login(email, password);
                    if(user.getRole().equals(Roles.ETUDIANT)){
                        JPanel homePanel =new HomePanel(cardPanel,user,parentFrame);
                        cardPanel.add(homePanel,"home");
                        cl.show(cardPanel,"home");
                    }

                    else if(user.getRole().equals(Roles.PROFESSEUR)){
                        JPanel prof=new ProfessorInterface(user);
                        cardPanel.add(prof,"prof");
                        cl.show(cardPanel,"prof");
                    }

                } catch (UserNotFoundException er) {
                    JOptionPane.showMessageDialog(this, er.getMessage(), "Erreur de connexion", JOptionPane.ERROR_MESSAGE);
                } catch (Exception er) {
                    JOptionPane.showMessageDialog(this, "Une erreur inattendue s'est produite : " + er.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}


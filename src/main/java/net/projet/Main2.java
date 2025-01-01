package net.projet;

import net.projet.ui.login.InscrirePanel;
import net.projet.ui.login.LoginPanel;

import javax.swing.*;
import java.awt.*;

public class Main2 {
    public static void main(String[] args) {
        JFrame frame;
        JPanel cardPanel,login_panel,inscrire_panel;


        frame = new JFrame();

        frame.setSize(800,600);
        frame.setLayout(null);

        cardPanel = new JPanel(new CardLayout());
        cardPanel.setBounds(0, 0, 800, 600);

        login_panel = new LoginPanel(cardPanel);
        inscrire_panel = new InscrirePanel();

        cardPanel.add(login_panel,"login");
        cardPanel.add(inscrire_panel,"inscrire");


        CardLayout cl = (CardLayout) cardPanel.getLayout();
        cl.show(cardPanel, "login");

        frame.add(cardPanel);
        frame.setVisible(true);

    }
}
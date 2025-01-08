package net.projet;

import net.projet.ui.login.InscrirePanel;
import net.projet.ui.login.LoginPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainY {
    public static void main(String[] args) {
        JFrame frame;
        JPanel cardPanel,login_panel,inscrire_panel,homePanel,questionsPanel;

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(800,600);
        frame.setLayout(null);

        cardPanel = new JPanel(new CardLayout());
        cardPanel.setBounds(0, 0, 800, 600);

        login_panel = new LoginPanel(cardPanel,frame);
        inscrire_panel = new InscrirePanel(cardPanel);


        cardPanel.add(login_panel,"login");
        cardPanel.add(inscrire_panel,"inscrire");

        

        CardLayout cl = (CardLayout) cardPanel.getLayout();
        cl.show(cardPanel, "login");
        frame.add(cardPanel);




        frame.setVisible(true);

    }
}
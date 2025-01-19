package net.projet.ui.professorUI;


import net.projet.entity.Result;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;

public class ExamResults extends JPanel {
    private final Color primaryColor = new Color(79, 120, 229);
    private final Color primaryDark = new Color(2, 81, 171);
    private final Color backgroundColor = new Color(245, 247, 251);
    private final Color RETURN_BUTTON_COLOR = new Color(128, 128, 128);

    private final Font CONTENT_FONT = new Font("Segoe UI", Font.PLAIN, 13);

    ArrayList<Result> results;
    JTable results_table;
    DefaultTableModel tableModel;
    JPanel parent;
    JLabel title;
    JButton return_btn;

    ExamResults(ArrayList<Result> results, JPanel parent) {
        this.setLayout(null);
        this.setBackground(backgroundColor);
        this.parent=parent;
        this.results=results;


        title=new JLabel("Exam Results");
        title.setFont(new Font("Segoe UI",Font.BOLD,25));
        title.setBounds(10,20,200,40);
        title.setForeground(primaryColor);
        title.setBackground(primaryDark);
        this.add(title);

        return_btn=createReturnButton();
        return_btn.setBounds(650, 20, 120, 30);
        this.add(return_btn);

        tableModel=new DefaultTableModel(new Object[] {"student","score","grade"},0);
        fill_table();
        results_table=new JTable(tableModel);
        results_table.setBounds(10,100,760,500);
        results_table.setRowHeight(25);
        JScrollPane pan=new JScrollPane(results_table);
        pan.setBounds(10,100,760,500);

        JTableHeader header = results_table.getTableHeader();
        header.setBackground(primaryColor);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));

        this.add(pan);
    }
    private void fill_table(){
        float note;
        String grade;
        if(!results.isEmpty()) {
            for (Result result : results) {
                note = result.getNote();
                grade = note > 10 ? "bien" : (note == 10 ? "passable" : "non validé");
                tableModel.addRow(new Object[]{result.getEtudiant().getNom(), note, grade});
            }
        }
    }

    private JButton createReturnButton() {
        JButton returnButton = new JButton("Retour");
        returnButton.setBackground(RETURN_BUTTON_COLOR);
        returnButton.setForeground(Color.WHITE);
        returnButton.setFont(CONTENT_FONT);
        returnButton.setFocusPainted(false);
        returnButton.setBorderPainted(false);
        returnButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        returnButton.addActionListener(e -> {
            CardLayout cardLayout = (CardLayout) parent.getLayout();
            cardLayout.show(parent, "managequizpage");
        });
        return returnButton;
    }
}

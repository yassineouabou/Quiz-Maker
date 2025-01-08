package net.projet.ui.etudiant;

import net.projet.entity.Result;
import net.projet.services.ResultService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ResultPanel {
    private ResultService resultService;
    DefaultTableModel tableModel;
    JTable table;
    JScrollPane sp;

    public ResultPanel() {
        resultService = new ResultService();
        JFrame frame = new JFrame("JTable Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        String[] columnNames = {"Exam", "Note"};
        tableModel = new DefaultTableModel(columnNames, 0);
        ArrayList<Result> results = resultService.getAllEtudiantResult(5L);

        for (Result result : results) {
            tableModel.addRow(new Object[]{result.getExam().getNom(), result.getNote()});
        }

        table = new JTable(tableModel);

        // Customize header style
        table.getTableHeader().setBackground(Color.BLUE);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 17));

        // Customize row style
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.BOLD, 15));

        // Center alignment for all columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Set different color for "Note" column based on value
        table.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Set background color based on the value in the "Note" column
                if (value instanceof Number) {
                    double note = ((Number) value).doubleValue();
                    if (note < 10) {
                        c.setBackground(Color.RED); // Low score - red
                        c.setForeground(Color.WHITE);
                    } else {
                        c.setBackground(Color.GREEN); // High score - green
                        c.setForeground(Color.BLACK);
                    }
                }
                return c;
            }
        });

        sp = new JScrollPane(table);
        sp.setSize(600, 400);
        sp.setLocation(25, 70);

        frame.add(sp);

        frame.setVisible(true);
    }

    public static void main(String[] arg){
        ResultPanel resultPanel = new ResultPanel();
    }
}


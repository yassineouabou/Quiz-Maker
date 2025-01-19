package net.projet.ui.etudiant;

import net.projet.entity.Result;
import net.projet.entity.User;
import net.projet.services.ResultService;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ResultPanel extends JPanel {
    private final Color PRIMARY_COLOR = new Color(51, 153, 255);  // Bleu clair
    private final Color RETURN_BUTTON_COLOR = new Color(128, 128, 128); // Gris pour le bouton retour
    private final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 14);
    private final Font CONTENT_FONT = new Font("Segoe UI", Font.PLAIN, 13);

    private final JTable resultTable;
    private final DefaultTableModel tableModel;
    private final User user;
    private final JPanel cardPanel;

    public ResultPanel(User user, JPanel cardPanel) {
        this.user = user;
        this.cardPanel = cardPanel;

        setLayout(null);
        setSize(800, 600);

        // Initialize table
        tableModel = new DefaultTableModel(new String[]{"Correction", "Examen", "Note"}, 0) ;
        resultTable = createTable();

        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setBounds(20, 70, 750, 450);
        add(scrollPane);

        // Create and position components
        JLabel titleLabel = new JLabel("Résultats des examens");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setBounds(20, 20, 300, 30);
        add(titleLabel);

        // Add return button
        JButton returnButton =new JButton("Retour");
        returnButton.setBounds(650, 20, 120, 30);
        returnButton.setBackground(RETURN_BUTTON_COLOR);
        returnButton.setForeground(Color.WHITE);
        returnButton.setFont(CONTENT_FONT);
        returnButton.addActionListener(e -> {
            CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
            cardLayout.show(cardPanel, "home");
        });
        add(returnButton);

        // Add click listener for "Voir"
        addTableClickListener();

        // Load data
        loadResultData();
    }


    private JTable createTable() {
        JTable table = new JTable(tableModel);
        // Style header
        JTableHeader header = table.getTableHeader();
        header.setBackground(PRIMARY_COLOR);
        header.setForeground(Color.WHITE);
        header.setFont(HEADER_FONT);
        // Style table
        table.setFont(CONTENT_FONT);
        table.setRowHeight(35);
        return table;
    }

    private void loadResultData() {
        ResultService resultService = new ResultService();
        List<Result> results = resultService.getAllEtudiantResult(user.getId());

        for (Result result : results) {
            tableModel.addRow(new Object[]{
                    "Voir", result.getExam().getNom(), result.getNote()
            });
        }
    }

    private void addTableClickListener() {
        resultTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = resultTable.rowAtPoint(e.getPoint());
                int column = resultTable.columnAtPoint(e.getPoint());
                if (column == 0) { // Check if the "Correction" column is clicked
                    ResultService resultService = new ResultService();
                    List<Result> results = resultService.getAllEtudiantResult(user.getId());
                    Result selectedResult = results.get(row);
                    // Show correction panel
                    showCorrectionPanel(selectedResult);
                }
            }
        });
    }

    private void showCorrectionPanel(Result result) {
        CorrectionPanel correctionPanel = new CorrectionPanel(result, user, cardPanel);
        cardPanel.add(correctionPanel, "correction");
        CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
        cardLayout.show(cardPanel, "correction");
    }
}

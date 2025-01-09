package net.projet.ui.etudiant;

import net.projet.entity.Result;
import net.projet.entity.User;
import net.projet.services.ResultService;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ResultPanel extends JPanel {
    private static final Color PRIMARY_COLOR = new Color(51, 153, 255);  // Bleu clair
    private static final Color SUCCESS_COLOR = new Color(76, 175, 80);   // Vert
    private static final Color ERROR_COLOR = new Color(244, 67, 54);     // Rouge
    private static final Color BUTTON_COLOR = new Color(255, 165, 0);    // Orange pour le bouton "Voir"
    private static final Color RETURN_BUTTON_COLOR = new Color(128, 128, 128); // Gris pour le bouton retour
    private static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font CONTENT_FONT = new Font("Segoe UI", Font.PLAIN, 13);

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
        tableModel = createTableModel();
        resultTable = createTable();

        // Create and position components
        JLabel titleLabel = new JLabel("Résultats des examens");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setBounds(20, 20, 300, 30);
        add(titleLabel);

        // Add return button
        JButton returnButton = createReturnButton();
        returnButton.setBounds(650, 20, 120, 30);
        add(returnButton);

        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setBounds(20, 70, 750, 450);
        add(scrollPane);

        // Load data
        loadResultData();
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
            CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
            cardLayout.show(cardPanel, "home");
        });
        return returnButton;
    }

    private DefaultTableModel createTableModel() {
        return new DefaultTableModel(
                new String[]{"Correction", "Examen", "Note"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0;
            }
        };
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
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        // Configure columns
        configureColumns(table);

        return table;
    }

    private void configureColumns(JTable table) {
        // Center alignment renderer
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Configure each column
        TableColumnModel columnModel = table.getColumnModel();

        // Correction column with button
        TableColumn correctionColumn = columnModel.getColumn(0);
        correctionColumn.setPreferredWidth(100);
        correctionColumn.setCellRenderer(new ButtonRenderer());
        correctionColumn.setCellEditor(new ButtonEditor());

        // Exam name column
        TableColumn examColumn = columnModel.getColumn(1);
        examColumn.setPreferredWidth(300);
        examColumn.setCellRenderer(centerRenderer);

        // Grade column with conditional formatting
        TableColumn gradeColumn = columnModel.getColumn(2);
        gradeColumn.setPreferredWidth(100);
        gradeColumn.setCellRenderer(new GradeCellRenderer());
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

    private class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setFocusPainted(false);
            setBorderPainted(false);
            setBackground(BUTTON_COLOR);
            setForeground(Color.WHITE);
            setFont(CONTENT_FONT);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value.toString());
            return this;
        }
    }

    private class ButtonEditor extends DefaultCellEditor {
        private final JButton button;

        public ButtonEditor() {
            super(new JCheckBox());
            button = new JButton();
            button.setOpaque(true);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setBackground(BUTTON_COLOR);
            button.setForeground(Color.WHITE);
            button.setFont(CONTENT_FONT);

            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            button.setText(value.toString());
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            Result result = getResultForRow(resultTable.getSelectedRow());
            showCorrectionPanel(result);
            return button.getText();
        }

        private Result getResultForRow(int row) {
            ResultService resultService = new ResultService();
            List<Result> results = resultService.getAllEtudiantResult(user.getId());
            return results.get(row);
        }

        private void showCorrectionPanel(Result result) {
            CorrectionPanel correctionPanel = new CorrectionPanel(result, user, cardPanel);
            cardPanel.add(correctionPanel, "correction");
            CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
            cardLayout.show(cardPanel, "correction");
        }
    }

    private class GradeCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);

            if (value instanceof Number) {
                double grade = ((Number) value).doubleValue();
                if (grade < 10) {
                    c.setBackground(ERROR_COLOR);
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(SUCCESS_COLOR);
                    c.setForeground(Color.WHITE);
                }
            }

            setHorizontalAlignment(SwingConstants.CENTER);
            return c;
        }
    }
}
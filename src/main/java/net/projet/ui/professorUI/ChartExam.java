package net.projet.ui.professorUI;

import net.projet.entity.Exam;
import net.projet.entity.Result;
import net.projet.services.ExamService;
import net.projet.services.ResultService;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChartExam extends JPanel {
    private final Color RETURN_BUTTON_COLOR = new Color(128, 128, 128); // Gris pour le bouton retour
    private final Color backgroundColor = new Color(245, 247, 251);
    private final Font CONTENT_FONT = new Font("Segoe UI", Font.PLAIN, 13);

    private int passable, bon, mauvais;
    private ArrayList<Result> results;
    private JFreeChart chart;
    private ChartPanel chartPanel;
    JButton return_btn;
    JPanel parent;
    public ChartExam(ArrayList<Result> results, JPanel parent) {



        this.results=results;
        this.parent=parent;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(null);
        setBackground(backgroundColor);
        this.return_btn=createReturnButton();
        this.return_btn.setBounds(650, 20, 120, 30);
        this.add(return_btn);
        if (results.isEmpty()) {
            showError("Aucun examen trouvé pour ce professeur");
            return;
        }

        updateChartData();
        chartPanel = new ChartPanel(chart);
        chartPanel.setBounds(0,60,800, 500);
        chartPanel.setBackground(backgroundColor);

        add(chartPanel);

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
//    private JComboBox<Exam> createExamComboBox(List<Exam> exams) {
//        DefaultComboBoxModel<Exam> model = new DefaultComboBoxModel<>(exams.toArray(new Exam[0]));
//        JComboBox<Exam> examComboBox = new JComboBox<>(model);
//
//        examComboBox.setRenderer(new DefaultListCellRenderer() {
//            @Override
//            public Component getListCellRendererComponent(JList<?> list, Object value,
//                                                          int index, boolean isSelected, boolean cellHasFocus) {
//                if (value instanceof Exam) {
//                    value = ((Exam) value).getNom();
//                }
//                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
//            }
//        });
//
//        examComboBox.addActionListener(e -> {
//            Exam selectedExam = (Exam) examComboBox.getSelectedItem();
//            if (selectedExam != null) {
//                selectedId = selectedExam.getId();
//                updateChartData();
//                chartPanel.setChart(chart);
//            }
//        });
//
//        examComboBox.setPreferredSize(new Dimension(200, 25));
//        return examComboBox;
//    }

    private void updateChartData() {
        calculateStatistics(this.results);
        DefaultPieDataset dataset = createDataset();

        chart = ChartFactory.createPieChart(
                "Répartition des Notes",
                dataset,
                true,
                true,
                false
        );
        chart.setBackgroundPaint(backgroundColor);

        // Customize the plot to show numbers
        PiePlot plot = (PiePlot) chart.getPlot();
        // Show both percentage and value: {0} = name, {1} = value, {2} = percentage
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
                "{0}: {1} étudiants ({2})",
                new DecimalFormat("0"),      // Format for number of students
                new DecimalFormat("0.0%")    // Format for percentage
        ));

        // Customize colors
        plot.setSectionPaint("Bon (>10/20)", new Color(46, 204, 113));     // Green
        plot.setSectionPaint("Passable (10/20)", new Color(241, 196, 15)); // Yellow
        plot.setSectionPaint("Insuffisant (<10/20)", new Color(231, 76, 60)); // Red

        // Additional customization
        plot.setLabelBackgroundPaint(new Color(255, 255, 255, 180)); // Semi-transparent white
        plot.setLabelOutlinePaint(null); // No outline
        plot.setLabelShadowPaint(null);  // No shadow
        plot.setBackgroundPaint(new Color(243, 251, 255));
    }

    private void calculateStatistics(List<Result> results) {
        passable = 0;
        bon = 0;
        mauvais = 0;

        for (Result result : results) {
            double note = result.getNote();
            if (note < 10) {
                mauvais++;
            } else if (note > 10) {
                bon++;
            } else {
                passable++;
            }
        }
    }

    private DefaultPieDataset createDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Bon (>10/20)", bon);
        dataset.setValue("Passable (10/20)", passable);
        dataset.setValue("Insuffisant (<10/20)", mauvais);
        return dataset;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }


}
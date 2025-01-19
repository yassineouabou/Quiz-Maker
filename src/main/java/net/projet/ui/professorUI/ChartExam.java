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
import java.util.List;

public class ChartExam extends JFrame {
    private final ExamService examService;
    private final ResultService resultService;
    private int passable, bon, mauvais;
    private Long selectedId;
    private JFreeChart chart;
    private ChartPanel chartPanel;

    public ChartExam(String title, Long professorId) {
        super(title);
        this.examService = new ExamService();
        this.resultService = new ResultService();

        initializeUI(professorId);
    }

    private void initializeUI(Long professorId) {
        setLayout(new BorderLayout());

        List<Exam> exams = examService.findByProfId(professorId);
        if (exams.isEmpty()) {
            showError("Aucun examen trouvé pour ce professeur");
            return;
        }

        selectedId = exams.get(0).getId();

        JComboBox<Exam> examComboBox = createExamComboBox(exams);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(new JLabel("Sélectionner un examen : "));
        topPanel.add(examComboBox);

        updateChartData();
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));

        add(topPanel, BorderLayout.NORTH);
        add(chartPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

    private JComboBox<Exam> createExamComboBox(List<Exam> exams) {
        DefaultComboBoxModel<Exam> model = new DefaultComboBoxModel<>(exams.toArray(new Exam[0]));
        JComboBox<Exam> examComboBox = new JComboBox<>(model);

        examComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof Exam) {
                    value = ((Exam) value).getNom();
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });

        examComboBox.addActionListener(e -> {
            Exam selectedExam = (Exam) examComboBox.getSelectedItem();
            if (selectedExam != null) {
                selectedId = selectedExam.getId();
                updateChartData();
                chartPanel.setChart(chart);
            }
        });

        examComboBox.setPreferredSize(new Dimension(200, 25));
        return examComboBox;
    }

    private void updateChartData() {
        List<Result> results = resultService.getAllByExamId(selectedId);
        calculateStatistics(results);
        DefaultPieDataset dataset = createDataset();

        chart = ChartFactory.createPieChart(
                "Répartition des Notes",
                dataset,
                true,
                true,
                false
        );

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChartExam chart = new ChartExam("Statistiques des Examens", 6L);
            chart.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            chart.setVisible(true);
        });
    }
}
package net.projet.ui.professorUI;

import net.projet.entity.Exam;
import net.projet.entity.Result;
import net.projet.entity.User;
import net.projet.services.ExamService;
import net.projet.services.ResultService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Manage_quizzes extends  JPanel{
    private final Color primaryColor = new Color(79, 120, 229);
    private final Color primaryDark = new Color(2, 81, 171);
    private final Color backgroundColor = new Color(245, 247, 251);
    private final Color textColor = new Color(55, 65, 81);
    private final Color RETURN_BUTTON_COLOR = new Color(128, 128, 128);
    private final Font CONTENT_FONT = new Font("Segoe UI", Font.PLAIN, 13);


    User prof;
    JTable quizzes_table;
    DefaultTableModel table_model;
    JLabel title;
    ExamService examService;
    ArrayList <Exam> prof_exams;
    ArrayList <JButton> Results_btns, Stats_btns;
    String[] column_names =new String[] {"quiz_name","quiz_Code","view_results","view_statistics"};
    JPanel parent;
    JButton return_btn;
    public Manage_quizzes(JPanel parent, User prof){

//        JPanel Header_panel=new JPanel();
//        Header_panel.setLayout(new BorderLayout());
//        Header_panel.setSize(600,100);

        title=new JLabel("Manage Quizzes");
        title.setFont(new Font("Segoe UI",Font.BOLD,25));
        title.setBounds(20,20,200,40);

        title.setForeground(primaryColor);
        title.setBackground(primaryDark);
        this.add(title);
        //Header_panel.add(BorderLayout.WEST,title);


        this.setBackground(backgroundColor);
        this.setLayout(null);


        this.parent=parent;
        this.prof= prof;
        this.return_btn=createReturnButton();
        this.return_btn.setBounds(650, 20, 120, 30);
        //Header_panel.add(BorderLayout.EAST,return_btn);
        this.add(return_btn);
        //this.add(BorderLayout.NORTH,Header_panel);
        examService=new ExamService();
        prof_exams=examService.findByProfId(this.prof.getId());

        Results_btns=new ArrayList<>();
        Stats_btns=new ArrayList<>();

        table_model= new DefaultTableModel(column_names,0)
                {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return column==2 || column==3;
                    }
                };


        show_exams();
        quizzes_table=new JTable(table_model);

        quizzes_table.getColumn("view_results").setCellRenderer(new ButtonRenderer());
        quizzes_table.getColumn("view_statistics").setCellRenderer(new ButtonRenderer());

        quizzes_table.getColumn("view_results").setCellEditor(new ButtonEditor());
        quizzes_table.getColumn("view_statistics").setCellEditor(new ButtonEditor());

        quizzes_table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        quizzes_table.setRowHeight(45);
        quizzes_table.setShowGrid(false);

        //quizzes_table.setIntercellSpacing(new Dimension(0, 0));

        JTableHeader header = quizzes_table.getTableHeader();
        header.setBackground(primaryColor);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JScrollPane table_scroll=new JScrollPane(quizzes_table);
        add(table_scroll);

        quizzes_table.setBounds(0,100,800,500);
        JScrollPane pan=new JScrollPane(quizzes_table);
        pan.setBounds(0,100,800,500);
        this.add(pan);




    }

    private void show_exams(){

        for (Exam exam : prof_exams) {
            table_model.addRow(new Object[] {exam.getNom(),exam.getCodeUnque(),"result","stat"});

        }

    }
    private class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
            setFocusPainted(false);
            setBorderPainted(false);
            setBackground(new Color(230, 248, 255));

        }

        @Override
        public Component getTableCellRendererComponent(JTable table,Object value ,boolean isSelected, boolean hasFocus, int row, int column){
            String iconPath;
            Image resizedImage;
            System.out.println("this is: "+(String) value);
            if(column==2){

                iconPath="src/main/resources/images/View Results.png";
                ImageIcon createIcon = new ImageIcon(iconPath);
                resizedImage= createIcon.getImage().getScaledInstance(65,65, Image.SCALE_SMOOTH);
            }
            else {

                iconPath="src/main/resources/images/pie-chart.png";
                ImageIcon createIcon = new ImageIcon(iconPath);
                resizedImage = createIcon.getImage().getScaledInstance(40,40, Image.SCALE_SMOOTH);
            }
            ImageIcon resizedIcon = new ImageIcon(resizedImage);
            setIcon(resizedIcon);
            return this;
        }
    }
    private class ButtonEditor extends DefaultCellEditor {

        JButton button;

        public ButtonEditor() {
            super(new JCheckBox());
            button =new JButton();
            button.setOpaque(true);



            button.addActionListener(e-> fireEditingStopped());


        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

            return super.getTableCellEditorComponent(table, value, isSelected, row, column);
        }

        @Override
        public Object getCellEditorValue() {

            ArrayList <Result> results=getResultForRow(quizzes_table.getSelectedRow());
            int selected_row=quizzes_table.getSelectedColumn();
            if(selected_row==2)
            ShowResultsPanel(results);
            else ShowChartPanel(results);
            return button.getText();
        }

        private ArrayList<Result> getResultForRow(int row) {
            ResultService resultService = new ResultService();
            return resultService.getAllByExamId(prof_exams.get(row).getId());
        }
        private void ShowResultsPanel(ArrayList <Result> results){
            ExamResults examresultsPanel=new ExamResults(results,parent);
            parent.add(examresultsPanel, "examResults");
            CardLayout cardLayout = (CardLayout) parent.getLayout();
            cardLayout.show(parent, "examResults");

        }
        private  void ShowChartPanel(ArrayList <Result> results){
            ChartExam examChartPanel=new ChartExam(results,parent);
            parent.add(examChartPanel, "examStats");
            CardLayout cardLayout = (CardLayout) parent.getLayout();
            cardLayout.show(parent, "examStats");
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
            cardLayout.show(parent, "profhomepage");
        });
        return returnButton;
    }

}

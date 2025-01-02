package net.projet.entity;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private Long id;
    private String text;
    private String options;
    private Exam exam;


    public Question(Long id, String text, String options) {
        this.id = id;
        this.text = text;
        this.options = options;
    }

    public Question(String text, String options, Exam exam) {
        this.text = text;
        this.options = options;
        this.exam = exam;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }
}

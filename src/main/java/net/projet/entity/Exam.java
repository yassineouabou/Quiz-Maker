package net.projet.entity;

import java.util.List;

public class Exam {
    private Long id;
    private String nom;
    private User prof;
    private String codeUnque;
    private List<Question> questions;

    public Exam(String nom, User prof, String codeUnque, List<Question> questions) {
        this.nom = nom;
        this.prof = prof;
        this.codeUnque = codeUnque;
        this.questions = questions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public User getProf() {
        return prof;
    }

    public void setProf(User prof) {
        this.prof = prof;
    }

    public String getCodeUnque() {
        return codeUnque;
    }

    public void setCodeUnque(String codeUnque) {
        this.codeUnque = codeUnque;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}

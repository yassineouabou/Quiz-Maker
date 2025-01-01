package net.projet.entity;

import java.util.List;
import java.util.UUID;
public class Exam {
    private Long id;
    private String nom;
    private User prof;
    private String codeUnque;
    private List<Question> questions;

    public Exam(String nom, User prof ) {
        this.nom = nom;
        this.prof = prof;
        this.codeUnque =UUID.randomUUID().toString().substring(0,20);;

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

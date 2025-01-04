package net.projet.entity;

import java.util.List;
import java.util.UUID;
public class Exam {
    private Long id;
    private String nom;
    private User prof;
    private String temps;
    private String codeUnique;
    private List<Question> questions;


    public Exam(Long id, String nom, User prof,String temps,String codeUnique, List<Question> questions) {
        this.id = id;
        this.nom = nom;
        this.prof = prof;
        this.temps = temps;
        this.codeUnique = codeUnique;
        this.questions = questions;
    }

    public Exam(String nom, User prof,String temps , String codeUnique) {
        this.nom = nom;
        this.temps = temps;
        this.prof = prof;

        this.codeUnique =codeUnique;

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
        return codeUnique;
    }

    public void setCodeUnque(String codeUnque) {
        this.codeUnique = codeUnque;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getTemps() {
        return temps;
    }

    public void setTemps(String temps) {
        this.temps = temps;
    }
}

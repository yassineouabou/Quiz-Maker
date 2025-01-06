package net.projet.entity;

public class Result {
    private Long id;
    private User etudiant;
    private Exam exam;
    private float note;

    public Result(Long id, User etudiant, Exam exam, float note) {
        this.id = id;
        this.etudiant = etudiant;
        this.exam = exam;
        this.note = note;
    }

    public Result(User etudiant, Exam exam, float note) {
        this.etudiant = etudiant;
        this.exam = exam;
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(User etudiant) {
        this.etudiant = etudiant;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }
}

package net.projet.entity;

public class EtudiantReponse {
    private Long id;
    private User etudiant;
    private Question question;
    private String selectedOption;


    public EtudiantReponse(Long id, User etudiant, Question question, String selectedOption) {
        this.id = id;
        this.etudiant = etudiant;
        this.question = question;
        this.selectedOption = selectedOption;
    }

    public EtudiantReponse(User etudiant, Question question, String selectedOption) {
        this.etudiant = etudiant;
        this.question = question;
        this.selectedOption = selectedOption;
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

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }
}

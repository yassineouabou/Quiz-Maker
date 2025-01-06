package net.projet.services;

import net.projet.dao.Questiondoa;
import net.projet.entity.EtudiantReponse;
import net.projet.entity.Question;

import java.sql.SQLException;

public class QuestionService {
    Questiondoa questiondoa;

    public QuestionService() {
        questiondoa = new Questiondoa();
    }

    public boolean createQuestion(Question question) throws SQLException {
        return questiondoa.createQuestion(question);
    }

    public Question findById(Long id){
        return questiondoa.findById(id);
    }

}

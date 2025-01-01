package net.projet.services;

import net.projet.dao.Questiondoa;
import net.projet.entity.Question;

import java.sql.SQLException;

public class QuestionService {
    Questiondoa questiondoa;

    public QuestionService() {
        questiondoa = new Questiondoa();
    }

    public boolean createQuestion(Question question) throws SQLException {
        StringBuilder options = new StringBuilder();

        for (String option : question.getOptions()) {
            options.append(option).append(";#;");
        }
        return questiondoa.createQuestion(question.getText(), options.toString(),question.getExam().getId());
    }
}

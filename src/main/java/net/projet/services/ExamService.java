package net.projet.services;

import net.projet.dao.Examdoa;
import net.projet.entity.Exam;

import java.sql.SQLException;

public class ExamService {
    Examdoa examdoa;

    public ExamService() {
        examdoa = new Examdoa();
    }
    public boolean createExam(Exam exam) throws SQLException {
        int examId=examdoa.CreateExam(exam.getNom(),exam.getCodeUnque(),exam.getProf().getId());
        exam.setId((long) examId);
        return examId>0;
    }

}
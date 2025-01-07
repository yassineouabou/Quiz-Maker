package net.projet.services;

import net.projet.dao.Examdoa;
import net.projet.entity.Exam;



public class ExamService {
    Examdoa examdoa;

    public ExamService() {
        examdoa = new Examdoa();
    }
    public Long createExam(Exam exam) {
        return examdoa.CreateExam(exam);
    }

    public Exam findExamByCodeUnique(String codeUnique){
        return examdoa.findExamByCodeUnique(codeUnique);
    }

    public Exam findById(Long examId){
        return examdoa.findById(examId);
    }

}
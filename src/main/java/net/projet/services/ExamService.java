package net.projet.services;

import net.projet.dao.Examdoa;
import net.projet.entity.Exam;

import java.util.ArrayList;


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

    public ArrayList<Exam> findByProfId(Long profId){
        return examdoa.findByProfId(profId);
    }

}
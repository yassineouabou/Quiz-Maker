package net.projet.exceptions;

public class ExamNotFoundException extends RuntimeException{
    public ExamNotFoundException(String message){
        super(message);
    }
}

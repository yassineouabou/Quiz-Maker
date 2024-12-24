package net.projet.exceptions;

public class TestException extends RuntimeException {
    public TestException(String message){
        super("this is a Test Exception");
    }

}

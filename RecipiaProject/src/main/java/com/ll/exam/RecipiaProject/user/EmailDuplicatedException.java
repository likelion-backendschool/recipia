package com.ll.exam.RecipiaProject.user;

public class EmailDuplicatedException extends RuntimeException {
    public EmailDuplicatedException(String msg){
        super(msg);
    }
}

package com.ll.exam.RecipiaProject.user;

public class UsernameDuplicatedException extends RuntimeException {
    public UsernameDuplicatedException(String msg){
        super(msg);
    }
}

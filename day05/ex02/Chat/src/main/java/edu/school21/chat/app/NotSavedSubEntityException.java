package edu.school21.chat.app;

public class NotSavedSubEntityException extends RuntimeException{
    public NotSavedSubEntityException(String message) {
        super(message);
    }
}

package ru.otus.homework.reflections.exception;

public class AnnotationNotFoundException extends RuntimeException {

    public AnnotationNotFoundException(String message) {
        super(message);
    }
}

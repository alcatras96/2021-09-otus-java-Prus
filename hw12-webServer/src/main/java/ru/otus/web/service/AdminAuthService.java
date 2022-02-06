package ru.otus.web.service;

public interface AdminAuthService {
    boolean authenticate(String login, String password);
}

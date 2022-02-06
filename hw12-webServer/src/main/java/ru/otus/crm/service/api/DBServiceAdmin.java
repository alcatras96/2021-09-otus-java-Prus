package ru.otus.crm.service.api;

import ru.otus.crm.model.Admin;

import java.util.Optional;

public interface DBServiceAdmin {

    Optional<Admin> getAdmin(String login);
}

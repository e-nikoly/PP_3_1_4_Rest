package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Collection;

public interface RoleService {
    void add(Role role);

    Collection<Role> getAll();

    void delete(Long id);

    Role getById(Long id);

    Role getRoleByName(String input_role);
}

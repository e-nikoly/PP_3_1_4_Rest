package ru.kata.spring.boot_security.demo.service;



import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    void add(Long id, User user, String role);

    void removeUserById(Long id);

    void saveUser(User user, String role);

    List<User> listUsers();

    User showUser(Long id);

    public User showUserByName(String name);
}

package ru.kata.spring.boot_security.demo.service;



import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    void add(User user);

    void removeUserById(Long id);

    void saveUser(User user);

    List<User> listUsers();

    User showUser(Long id);

    public User showUserByName(String name);
}

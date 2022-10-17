package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {

    void add(User user);

    void removeUserById(Long  id);

    void saveUser(User user);

    List<User> listUsers();

    User showUser(Long id);

    User showUserByName(String name);
}

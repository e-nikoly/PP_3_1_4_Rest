package ru.kata.spring.boot_security.demo.dao;


import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(Long id, User user) {
        entityManager.merge(user);
    }

    @Override
    public void removeUserById(Long  id) {
        entityManager.remove(showUser(id));
    }

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public List<User> listUsers() {
        return entityManager.createQuery("SELECT user FROM User user", User.class).getResultList();
    }

    @Override
    public User showUser(Long  id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User showUserByName(String name) {
        return entityManager.createQuery("SELECT u from User u where u.name = :name", User.class).setParameter("name", name).getSingleResult();
    }

}

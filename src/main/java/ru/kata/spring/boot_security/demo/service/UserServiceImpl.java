package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserDao userDao;
    private RoleService roleService;

    @Autowired
    public void setPasswordEncoder() {
    }
    @Autowired
    public UserServiceImpl(UserDao userDao, RoleService roleService) {
        this.userDao = userDao;
        this.roleService = roleService;
    }
    public UserServiceImpl() {
    }

    @Transactional
    @Override
    public void add(User user) {
        Set<Role> roles;
        Set<Role> updateRoles = user.getRoles();
        roles = userDao.showUserByName(user.getName()).getRoles();
        if (updateRoles.contains(null)) {
            roles = userDao.showUserByName(user.getName()).getRoles();
        } else if (updateRoles.stream().anyMatch(r -> r.getName().equals("ADMIN"))) {
            roles.add(roleService.getRoleByName("ADMIN"));
        } else if (updateRoles.stream().noneMatch(r -> r.getName().equals("ADMIN"))) {
            roles.clear();
            roles.add(roleService.getRoleByName("USER"));
        }
        user.setRoles(roles);
        User changePass = userDao.showUser(user.getId());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (changePass.getPassword().equals(user.getPassword())) {
            user.setPassword(user.getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userDao.add(user);
    }

    @Transactional
    @Override
    public void removeUserById(Long id) {
        userDao.removeUserById(id);
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        Set<Role> newRoles = user.getRoles();
        Set<Role> roles = new HashSet<>();
        if (newRoles.stream().anyMatch(r -> r.getName().equals("ADMIN"))) {
            roles.add(roleService.getRoleByName("ADMIN"));
            roles.add(roleService.getRoleByName("USER"));
        } else {
            roles.add(roleService.getRoleByName("USER"));
        }
        user.setRoles(roles);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.saveUser(user);
    }
    @Override
    public List<User> listUsers() {
        return userDao.listUsers();
    }
    @Transactional

    @Override
    public User showUser(Long id) {
        return userDao.showUser(id);
    }

    @Override
    @Transactional
    public User showUserByName(String name) {
        return userDao.showUserByName(name);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = showUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getRoles());
    }
}

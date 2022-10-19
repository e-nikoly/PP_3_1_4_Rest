package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Set;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api/v1/admin")
public class AdminRestController {

    public final UserService userService;
    private final RoleService roleService;

    public AdminRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<User>> showAllUsers() {
        return new ResponseEntity<>(userService.listUsers(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        userService.saveUser(user);
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<>(user, responseHeaders, HttpStatus.CREATED);
    }

    @GetMapping(value = "form/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> showUserById(@PathVariable Long id) {
        try {
            User user = userService.showUser(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<User> delete(@PathVariable Long id) {
        userService.removeUserById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<User> update(@RequestBody User user) {
        userService.add(user);
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<>(user, responseHeaders, HttpStatus.OK);
    }
    @GetMapping("/roles")
    public ResponseEntity<Set<Role>> showAllRoles() {
        HttpHeaders responseHeaders = new HttpHeaders();
        Set<Role> roles = (Set<Role>) roleService.getAll();
        return new ResponseEntity<>(roles, responseHeaders, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<User> showUser(Authentication authentication) {
        return new ResponseEntity<>((User) authentication.getPrincipal(), HttpStatus.OK);
    }

}

package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public void usersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public String showUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.showUser(id));
        return "user";
    }

    @GetMapping("/user")
    public String toUserPage(Principal principal, Model model) {
        model.addAttribute("user", userService.showUserByName(principal.getName()));
        return "user";
    }
}
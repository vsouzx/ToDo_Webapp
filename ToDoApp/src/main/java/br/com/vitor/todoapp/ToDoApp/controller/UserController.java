package br.com.vitor.todoapp.ToDoApp.controller;

import br.com.vitor.todoapp.ToDoApp.service.UserService;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.vitor.todoapp.ToDoApp.model.User;
import br.com.vitor.todoapp.ToDoApp.repository.TaskRepository;
import br.com.vitor.todoapp.ToDoApp.repository.UserRepository;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/process_register")
    public String processRegistration(User user) {
        return userService.processRegistration(user);
    }

    @GetMapping(value = "/list_users")
    public String viewUsersList(Model model, Authentication auth) {
        return userService.viewUsersList(model, auth);
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteUser(User user) {
        return userService.deleteUser(user);
    }

    @GetMapping(value = "/edit/{id}")
    public String editUserForm(@PathVariable("id") Long id, Model model, Authentication auth) {
        return userService.editUserForm(id, model, auth);
    }

    @PostMapping(value = "/edit/confirmation")
    public String editUser(User user) {
        return userService.editUser(user);
    }
}

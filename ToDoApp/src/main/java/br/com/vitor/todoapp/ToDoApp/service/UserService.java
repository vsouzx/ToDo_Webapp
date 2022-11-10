package br.com.vitor.todoapp.ToDoApp.service;

import br.com.vitor.todoapp.ToDoApp.model.CustomUserDetails;
import br.com.vitor.todoapp.ToDoApp.model.Status;
import br.com.vitor.todoapp.ToDoApp.model.Task;
import br.com.vitor.todoapp.ToDoApp.model.User;
import br.com.vitor.todoapp.ToDoApp.repository.TaskRepository;
import br.com.vitor.todoapp.ToDoApp.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final TaskRepository taskRepository;

    private final BCryptPasswordEncoder encoder;

    public UserService(TaskRepository taskRepository,
                       UserRepository userRepository,
                       BCryptPasswordEncoder encoder) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public String processRegistration(User user){
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRole("ROLE_USER");
        user.setEnabled(true);
        userRepository.save(user);

        return "/user/register_success.html";
    }

    public String viewUsersList(Model model, Authentication auth){
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        String nomeUsuario = user.getUser().getFirstName();

        List<User> usersList = userRepository.findAll();
        model.addAttribute("usersList", usersList);
        model.addAttribute("username", nomeUsuario);
        return "/user/usersList";
    }

    public String deleteUser(User user){
        taskRepository.deleteAllByUser(user.getId());
        userRepository.deleteById(user.getId());

        return "redirect:/list_users";
    }

    public String editUserForm(Long id, Model model, Authentication auth){
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        String nomeUsuario = user.getUser().getFirstName();

        Optional<User> usuario = userRepository.findById(id);

        model.addAttribute("username", nomeUsuario);
        model.addAttribute("user", usuario.get());
        return "user/edit";
    }

    public String editUser(User user){
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);

        return "redirect:/list_users";
    }
}

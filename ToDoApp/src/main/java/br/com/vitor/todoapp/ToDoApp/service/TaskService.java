package br.com.vitor.todoapp.ToDoApp.service;

import br.com.vitor.todoapp.ToDoApp.model.CustomUserDetails;
import br.com.vitor.todoapp.ToDoApp.model.Status;
import br.com.vitor.todoapp.ToDoApp.model.Task;
import br.com.vitor.todoapp.ToDoApp.model.User;
import br.com.vitor.todoapp.ToDoApp.repository.TaskRepository;
import br.com.vitor.todoapp.ToDoApp.repository.UserRepository;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public String menuTasks(Model model, Authentication auth, String status){
        List<Task> tasks = new ArrayList<Task>();

        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        String nomeUsuario = user.getUser().getFirstName();
        String email = user.getUser().getEmail();

        if(status.equals("home")){
            tasks = taskRepository.findbyUsername(email);
        }
        if(!(status.equals("home"))){
            tasks = taskRepository.findbyUsernameAndStatus(email, Status.valueOf(status.toUpperCase(Locale.ROOT)));
        }

        if(tasks.isEmpty()) {
            model.addAttribute("username", nomeUsuario);
            model.addAttribute("tasks", tasks);
            model.addAttribute("url", status);
            return "/tasks/emptyTask";
        }

        model.addAttribute("username", nomeUsuario);
        model.addAttribute("tasks", tasks);
        model.addAttribute("url", status);
        return "/tasks/home";
    }

    public String novaTaskTela(Model model, Authentication auth){
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        String nomeUsuario = user.getUser().getFirstName();

        model.addAttribute("task", new Task());
        model.addAttribute("username", nomeUsuario);
        return "/tasks/new";
    }

    public String processandoNovaTask(Task task, Authentication auth){
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        String email = user.getUser().getEmail();

        User usuario = userRepository.findByEmail(email);

        task.setUser(usuario);
        task.setStatus(Status.TODO);

        taskRepository.save(task);

        return "/tasks/register_success";
    }

    public String editarTaskTela(Model model, Authentication auth, Long id){
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        String nomeUsuario = user.getUser().getFirstName();

        Optional<Task> task = taskRepository.findById(id);

        model.addAttribute("task", task.get());
        model.addAttribute("username", nomeUsuario);
        return "/tasks/edit";
    }

    public String processandoTaskEditada(Task task, Authentication auth){
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        String username = user.getUser().getEmail();

        User usuario = userRepository.findByEmail(username);
        task.setUser(usuario);
        taskRepository.save(task);

        return "redirect:/tasks/home";
    }

    public String deletaTask(Task task){
        taskRepository.deleteById(task.getId());
        return "redirect:/tasks/home";
    }
}

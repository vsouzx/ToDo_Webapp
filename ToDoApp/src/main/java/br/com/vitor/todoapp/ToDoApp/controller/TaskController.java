package br.com.vitor.todoapp.ToDoApp.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.vitor.todoapp.ToDoApp.model.Status;
import br.com.vitor.todoapp.ToDoApp.model.Task;
import br.com.vitor.todoapp.ToDoApp.model.User;
import br.com.vitor.todoapp.ToDoApp.repository.TaskRepository;
import br.com.vitor.todoapp.ToDoApp.repository.UserRepository;

@Controller
@RequestMapping("tasks")
public class TaskController {
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("home")
	public String showTasks(Model model, Principal principal) {
		
		String username = principal.getName();
		
		List<Task> tasks = taskRepository.findbyUsername(username);
		
		String url ="all";
		
		
		if(tasks == null || tasks.size() == 0) {
			model.addAttribute("username", username);
			model.addAttribute("tasks", tasks);
			return "tasks/emptyTask";
		}
		
		model.addAttribute("username", username);
		model.addAttribute("tasks", tasks);
		model.addAttribute("url", url);
		return "tasks/home";
	}
	
	@GetMapping("todo")
	public String showTasksToDo(Model model, Principal principal) {
		
		String username = principal.getName();
		
		List<Task> tasks = taskRepository.findbyUsernameAndStatus(username, Status.TO_DO);
		
		String url ="todo";
		
		if(tasks == null || tasks.size() == 0) {
			model.addAttribute("username", username);
			model.addAttribute("tasks", tasks);
			return "tasks/emptyTask";
		}
		
		model.addAttribute("username", username);
		model.addAttribute("tasks", tasks);
		model.addAttribute("url", url);
		return "tasks/home";
	}
	@GetMapping("done")
	public String showTasksDone(Model model, Principal principal) {
		
		String username = principal.getName();
		
		List<Task> tasks = taskRepository.findbyUsernameAndStatus(username, Status.DONE);
		
		String url ="done";
		if(tasks == null || tasks.size() == 0) {
			model.addAttribute("username", username);
			model.addAttribute("tasks", tasks);
			return "tasks/emptyTask";
		}
		
		model.addAttribute("username", username);
		model.addAttribute("tasks", tasks);
		model.addAttribute("url", url);
		return "tasks/home";
	}
	
	@GetMapping("new")
	public String newTask(Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("task", new Task());
		
		model.addAttribute("username", username);
		return "tasks/new";
	}
	
	@PostMapping("register_task")
	public String processRegistration(Task task, Principal principal) {
		
		String username = principal.getName();
		
		User user = userRepository.findByEmail(username);
		
		task.setUser(user);
		task.setStatus(Status.TO_DO);
		
		taskRepository.save(task);
		
		return "tasks/register_success";
	}
	
	@GetMapping("edit/{id}")
	public String editTaskForm(@PathVariable("id") Long id, Model model, Principal principal)	 {
		
		
		Optional<Task> task = taskRepository.findById(id);
		
		model.addAttribute("task", task);
		
		String username = principal.getName();
		model.addAttribute("username", username);
		return "tasks/edit";
	}
	
	@PostMapping("process_edit")
	public String process_edit(Task task, Principal principal)	 {
		String username = principal.getName();
		
		User user = userRepository.findByEmail(username);
		
		task.setUser(user);	
		taskRepository.save(task);
		return "redirect:/tasks/home";
	}
	
	@GetMapping("delete/{id}")
	public String process_delete(Task task)	 {
		
		taskRepository.deleteById(task.getId());
		
		return "redirect:/tasks/home";
	}
}

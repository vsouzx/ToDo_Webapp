package br.com.vitor.todoapp.ToDoApp.controller;

import br.com.vitor.todoapp.ToDoApp.service.TaskService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.vitor.todoapp.ToDoApp.model.Task;

@Controller
@RequestMapping(value = "/tasks")
public class TaskController {
	
	private final TaskService taskService;

	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	@GetMapping(value = "/{status}")
	public String showTasks(Model model, Authentication auth, @PathVariable("status") String status){
		return taskService.menuTasks(model, auth, status);
	}
	
	@GetMapping(value = "/new")
	public String newTask(Model model, Authentication auth){
		return taskService.novaTaskTela(model, auth);
	}
	
	@PostMapping(value = "/register_task")
	public String processRegistration(Task task, Authentication auth){
		return taskService.processandoNovaTask(task, auth);
	}
	
	@GetMapping(value = "/edit/{id}")
	public String editTaskForm(@PathVariable("id") Long id, Model model, Authentication auth){
		return taskService.editarTaskTela(model, auth, id);
	}
	
	@PostMapping(value = "/process_edit")
	public String process_edit(Task task, Authentication auth){
		return taskService.processandoTaskEditada(task, auth);
	}
	
	@GetMapping(value = "/delete/{id}")
	public String process_delete(Task task){
		return taskService.deletaTask(task);
	}
}

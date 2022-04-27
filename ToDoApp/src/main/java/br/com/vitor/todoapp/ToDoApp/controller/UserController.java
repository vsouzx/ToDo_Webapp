package br.com.vitor.todoapp.ToDoApp.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TaskRepository taskRepository;
	
	@PostMapping("process_register")
	public String processRegistration(User user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		String encodedPassword = encoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		user.setRole("ROLE_USER");
		user.setEnabled(true);
		
		userRepository.save(user);
		
		return "user/register_success.html";
	}
	
	@GetMapping("list_users")
	public String viewUsersList(Model model, Principal principal) {
		String username = principal.getName();
		
		List<User> usersList = userRepository.findAll();
		model.addAttribute("usersList", usersList);
		model.addAttribute("username", username);
		return "user/usersList";
	}

	@GetMapping("delete/{id}")
	public String deleteUser(User user) {
		
		taskRepository.deleteAllByUser(user.getId());
		userRepository.deleteById(user.getId());
		
		return "redirect:/list_users";
	}
	
	@GetMapping("edit/{id}")
	public String editUserForm(@PathVariable("id") Long id, Model model, Principal principal) {
		
		Optional<User> user = userRepository.findById(id);
		
		String username = principal.getName();
		model.addAttribute("username", username);
		model.addAttribute("user", user);
		return "user/edit";
	}
	
		@PostMapping("edit/confirmation")
		public String editUser(User user) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			
		String encodedPassword = encoder.encode(user.getPassword());
		user.setPassword(encodedPassword);	
		userRepository.save(user);
		
		return "redirect:/list_users";
	}
}

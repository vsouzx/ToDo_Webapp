package br.com.vitor.todoapp.ToDoApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.vitor.todoapp.ToDoApp.model.User;

@Controller
@RequestMapping
public class AppController {

	@GetMapping("home")
	public String viewHomePage() {
		return "index";
	}
	
	@GetMapping("register")
	public String viewSignUpPage(Model model) {
		model.addAttribute("user", new User());
		return "user/register.html";
	}
	
	@GetMapping("login")
	public String viewLoginPage() {
		return "user/login.html";
	}
	
}

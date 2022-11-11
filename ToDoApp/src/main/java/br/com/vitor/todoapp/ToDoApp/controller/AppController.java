package br.com.vitor.todoapp.ToDoApp.controller;

import br.com.vitor.todoapp.ToDoApp.dto.RecaptchaResponse;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.vitor.todoapp.ToDoApp.model.User;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping
public class AppController {

	private final RestTemplate restTemplate;

	@Value("${recaptcha.key}")
	private String recaptchaKey;

	@Value("${recaptcha.secret}")
	private String recaptchaSecret;

	@Value("${recaptcha.secret}")
	private String recaptchaUrl;

	public AppController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@PostMapping(value = "/login")
	public String login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String gRecaptchaResponse = request.getParameter("g-recaptcha-response");

		if(!verifyRecaptcha(gRecaptchaResponse)){
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
		verifyRecaptcha(gRecaptchaResponse);
		return "/user/login.html";
	}

	@GetMapping(value = "/home")
	public String viewHomePage() {
		return "/index";
	}
	
	@GetMapping(value = "/register")
	public String viewSignUpPage(Model model) {
		model.addAttribute("user", new User());
		return "/user/register.html";
	}
	
	@GetMapping(value = "/login")
	public String viewLoginPage() {
		return "/user/login.html";
	}

	private boolean verifyRecaptcha(String gRecaptchaResponse) {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("secret", recaptchaSecret);
		map.add("response", gRecaptchaResponse);

		HttpEntity<MultiValueMap<String, String>> request =  new HttpEntity<>(map);

		RecaptchaResponse response = restTemplate.postForObject(recaptchaUrl, request, RecaptchaResponse.class);

		if(response.getErrorCodes() != null){
			for(String error : response.getErrorCodes()){
				System.out.println("\t" + error);
			}
		}
		System.out.println(response);
		return response.isSuccess();
	}
}

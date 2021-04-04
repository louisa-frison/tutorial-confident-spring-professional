package com.louisa.web;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.louisa.web.forms.LoginForm;

@Controller
public class WebsiteController {


	@GetMapping("/")
	public String
	index(Model model, @RequestParam(required = false, defaultValue = "stranger") String username) {
		model.addAttribute("username", username); 
		model.addAttribute("currentDate", new Date()); 
		return "index.html";
	}
	
	@GetMapping("/login")
	public String getLoginForm(Model model) {
		LoginForm lf = new LoginForm(); 
		lf.setUsername("Louisa");
		model.addAttribute("loginForm", lf); 
		return "login.html";
	}
	
	@PostMapping("/login")
	public String processLoginForm(@ModelAttribute @Valid LoginForm loginForm , BindingResult result , Model model) {

		if (result.hasErrors()) {
			return "login.html"; 
		}
		if (loginForm.getUsername().equals(loginForm.getPassword())) {
			return "redirect:/?username=" + loginForm.getUsername() ; 
		}
		model.addAttribute("invalidCredentials", true); 
		return "login.html";
	}
}

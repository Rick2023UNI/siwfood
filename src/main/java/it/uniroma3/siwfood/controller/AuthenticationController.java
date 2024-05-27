package it.uniroma3.siwfood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siwfood.model.Cook;
import it.uniroma3.siwfood.model.Credentials;
import it.uniroma3.siwfood.service.CookService;
import it.uniroma3.siwfood.service.CredentialsService;
import it.uniroma3.siwfood.service.RecipeService;

@Controller 
public class AuthenticationController {
	@Autowired CookService cookService;
	@Autowired CredentialsService credentialsService;
	@Autowired PasswordEncoder passwordEncoder;
	@Autowired RecipeService recipeService;
	
	
	@GetMapping("/login")
	  public String formLoginCook(Model model) {
		    return "login.html";
	}
	
	@PostMapping("/login")
	public String loginCook(@RequestParam("credentials") Credentials credentials) {
		
		    return "index.html";
	}
	
	@GetMapping("/register")
	  public String formRegisterCook(Model model) {
		model.addAttribute("credentials", new Credentials());
		return "register.html";
	}
	
	@PostMapping("/register")
	public String registerCook(@ModelAttribute("credentials") Credentials credentials) {
			credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));
			credentialsService.save(credentials);
		    return "/login";
	}
	
	@GetMapping("/success")
	  public String success(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("recipes", this.recipeService.findAll());
		if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("admin"))) {
			return "admin/index.html";
		}
		else {
			return "index.html";
		}
	}
	
}

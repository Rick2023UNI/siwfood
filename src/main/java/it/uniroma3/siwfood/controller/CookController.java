package it.uniroma3.siwfood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siwfood.model.Cook;
import it.uniroma3.siwfood.service.CookService;

@Controller 
public class CookController {
	@Autowired CookService cookService;
	
	@GetMapping("/newCook")
	public String addCook(Model model) {
		model.addAttribute("cook", new Cook());
		return "formNewCook.html";
	}
	
	@PostMapping("/cook")
	public String newRecipe(@ModelAttribute("cook") Cook cook) {
		this.cookService.save(cook);
		return "redirect:cook/"+cook.getId();
	}
	
	@GetMapping("/cook/{id}")
	public String getCook(@PathVariable("id") Long id, Model model) {
	    model.addAttribute("cook", this.cookService.findById(id));
	    model.addAttribute("recipes", this.cookService.findById(id).getRecipes());
	    return "cook.html";
	  }
}

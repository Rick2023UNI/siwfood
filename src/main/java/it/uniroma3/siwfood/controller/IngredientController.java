package it.uniroma3.siwfood.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siwfood.service.ImageService;
import it.uniroma3.siwfood.service.IngredientService;
import it.uniroma3.siwfood.service.QuantityService;
import it.uniroma3.siwfood.service.RecipeService;

@Controller 
public class IngredientController {
	@Autowired IngredientService ingredientService;
	@Autowired ImageService imageService;
	@Autowired QuantityService quantityService;
	@Autowired RecipeService recipeService;

	@GetMapping("admin/manageIngredients")
	public String manageIngredients(Model model) {
		model.addAttribute("ingredients", this.ingredientService.findAll());		    
		return "admin/manageIngredients.html";
	}	

	@GetMapping("admin/removeIngredient/{id}")
	public String removeIngredient(@PathVariable("id") Long id,
			Model model) {
		return manageIngredients(model);
	}
}

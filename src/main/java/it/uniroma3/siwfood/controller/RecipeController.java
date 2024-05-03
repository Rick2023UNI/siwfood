package it.uniroma3.siwfood.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siwfood.model.Quantity;
import it.uniroma3.siwfood.model.Recipe;
import it.uniroma3.siwfood.service.IngredientService;
import it.uniroma3.siwfood.service.RecipeService;

@Controller 
public class RecipeController {
	@Autowired RecipeService recipeService;
	@Autowired IngredientService ingredientService;
	
	@GetMapping("/")
    public String index(Model model) {
		model.addAttribute("recipes", this.recipeService.findAll());
		return "index.html";
	}
	
	@GetMapping("/newRecipe")
	public String addRecipe(Model model) {
		model.addAttribute("recipe", new Recipe());
		return "formNewRecipe.html";
	}
	
	@PostMapping("/recipe")
	public String newRecipe(@ModelAttribute("recipe") Recipe recipe) {
		Date today=new Date();
		recipe.setPublicationDate(today);
		this.recipeService.save(recipe);
		return "redirect:recipe/"+recipe.getId();
	}
	
	@GetMapping("/recipe/{id}")
	public String getRecipe(@PathVariable("id") Long id, Model model) {
	    model.addAttribute("recipe", this.recipeService.findById(id));
	    model.addAttribute("quantities", this.recipeService.findById(id).getQuantities());
	    return "recipe.html";
	}
	
	@PostMapping("/addQuantity/{idRecipe}")
	public String addQuantity(@PathVariable("idRecipe") Long idRecipe, @ModelAttribute("quantity") Quantity quantity) {
		Recipe recipe=this.recipeService.findById(idRecipe);
		if (this.ingredientService.existsByName("test")) {
			recipe.addQuantity(quantity);
		}
		return "redirect:recipe/"+idRecipe;
	}
	
}

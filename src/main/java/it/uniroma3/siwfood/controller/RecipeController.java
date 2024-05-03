package it.uniroma3.siwfood.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siwfood.model.Ingredient;
import it.uniroma3.siwfood.model.Quantity;
import it.uniroma3.siwfood.model.Recipe;
import it.uniroma3.siwfood.service.IngredientService;
import it.uniroma3.siwfood.service.QuantityService;
import it.uniroma3.siwfood.service.RecipeService;

@Controller 
public class RecipeController {
	@Autowired RecipeService recipeService;
	@Autowired IngredientService ingredientService;
	@Autowired QuantityService quantityService;
	
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
	
	@PostMapping("/addQuantity/{id}")
	public String addQuantity(Model model, @PathVariable("id") Long id, @RequestParam("name") String nameParam, @RequestParam("quantity") String quantityParam) {
		Recipe recipe=this.recipeService.findById(id);
		System.out.println(nameParam);
		System.out.println(quantityParam);
		Quantity quantity=new Quantity();
		quantity.setQuantity(quantityParam);
		if (this.ingredientService.existsByName(nameParam)) {
			quantity.setIngredient(this.ingredientService.findByName(nameParam));
			recipe.addQuantity(quantity);
		}
		else {
			Ingredient ingredient=new Ingredient();
			ingredient.setName(nameParam);
			quantity.setIngredient(ingredient);
			recipe.addQuantity(quantity);
			this.ingredientService.save(ingredient);
		}
		this.quantityService.save(quantity);
		this.recipeService.save(recipe);
		//return "/recipe/"+recipe.getId();
		
		model.addAttribute("recipe", this.recipeService.findById(id));
	    model.addAttribute("quantities", this.recipeService.findById(id).getQuantities());
	    return "recipe.html";
	}
	
}

package it.uniroma3.siwfood.controller;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siwfood.model.Recipe;
import it.uniroma3.siwfood.model.Cook;
import it.uniroma3.siwfood.model.Image;
import it.uniroma3.siwfood.model.Ingredient;
import it.uniroma3.siwfood.model.Quantity;
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
	
	@PostMapping("/admin/ingredient")
	public String newIngredient(@ModelAttribute("ingredient") Ingredient ingredient,
			@RequestParam("fileImage") MultipartFile multipartFile) throws IOException {
		//Caricamento dell'immagine
		String fileName=StringUtils.cleanPath(multipartFile.getOriginalFilename());
		//Primo salvataggio per far assegnare all'ingrediente un id
		this.ingredientService.save(ingredient);
		//Impostazione del nome del file all'id dell'ingrediente, mantenendo l'estensione del file originale
		fileName=ingredient.getId()+fileName.substring(fileName.lastIndexOf('.'));
		Image image=new Image();
		image.setFolder("ingredient");
		this.imageService.save(image);
		
		image.uploadImage(fileName, multipartFile);
		ingredient.setImage(image);
		this.imageService.save(image);
		this.ingredientService.save(ingredient);
		return "redirect:/admin/manageIngredients";
	}
	
	@GetMapping("/admin/newIngredient")
	public String addRecipe(Model model) {
		model.addAttribute("ingredient", new Ingredient());
		return "admin/formNewIngredient.html";
	}
	

	@GetMapping("admin/removeIngredient/{id}")
	public String removeIngredient(@PathVariable("id") Long id,
			Model model) {
		Ingredient ingredient=this.ingredientService.findById(id);
		ingredient.getImage().delete();
		List<Quantity> quantities=ingredient.getQuantities();
		for (Quantity quantity : quantities) {
			Recipe recipe=quantity.getRecipe();
			recipe.removeQuantity(quantity);
			this.recipeService.save(recipe);
			this.quantityService.delete(quantity);
		}
		this.imageService.delete(ingredient.getImage());
		this.ingredientService.delete(ingredient);
		return "redirect:/admin/manageIngredients";
	}
	
	@GetMapping("/admin/formUpdateIngredient/{id}")
	public String formUpdateIngredient(@PathVariable("id") Long id, Model model) {
			model.addAttribute("ingredient", this.ingredientService.findById(id));
		return "admin/formUpdateIngredient.html";
	}

	@PostMapping("/admin/updateIngredient/{id}")
	public String updateIngredient(@PathVariable("id") Long id,
			@ModelAttribute("ingredient") Ingredient ingredientUpdated, 
			@RequestParam("fileImage") MultipartFile multipartFile) throws IOException {
		Ingredient ingredient=this.ingredientService.findById(id);
		//Caricamento delle immagini
		String fileName=StringUtils.cleanPath(multipartFile.getOriginalFilename());
		if (fileName!="") {
			//Eliminazione vecchia immagine
			Image image=ingredient.getImage();
			image.delete();
			
			fileName=ingredient.getId()+fileName.substring(fileName.lastIndexOf('.'));
			image.setFolder("ingredient");
			this.imageService.save(image);
			
			image.uploadImage(fileName, multipartFile);
			ingredient.setImage(image);
			this.imageService.save(image);
			this.ingredientService.save(ingredient);
		}
		ingredient.updateTo(ingredientUpdated);
		this.ingredientService.save(ingredient);
		return "redirect:/admin/formUpdateIngredient/"+ingredient.getId();
	}
	
	@PostMapping("admin/manageIngredients")
	public String searchIngredients(@RequestParam String name, Model model) {
		model.addAttribute("searchName", name);
		model.addAttribute("ingredients", this.ingredientService.findByNameContaining(name));
		return "admin/manageIngredients.html";
	}
}

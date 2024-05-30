package it.uniroma3.siwfood.controller;


import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siwfood.model.Cook;
import it.uniroma3.siwfood.model.Image;
import it.uniroma3.siwfood.model.Recipe;
import it.uniroma3.siwfood.service.CredentialsService;
import it.uniroma3.siwfood.service.ImageService;
import it.uniroma3.siwfood.service.IngredientService;
import it.uniroma3.siwfood.service.QuantityService;
import it.uniroma3.siwfood.service.RecipeService;

@Controller 
public class ImageController {
	@Autowired ImageService imageService;
	@Autowired CredentialsService credentialsService;
	@Autowired RecipeService recipeService;
	
	@GetMapping("/removeImage/{idRecipe}/{idImage}")
	public String removeImage(@PathVariable("idRecipe") Long idRecipe, 
			@PathVariable("idImage") Long idImage, Model model) {
		//Cuoco corrente
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Cook cook=credentialsService.getCredentials(user.getUsername()).getCook();
		if (cook.equals(this.recipeService.findById(idRecipe).getCook()) || (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("admin")))) {
			Recipe recipe=this.recipeService.findById(idRecipe);
			Image image=this.imageService.findById(idImage);
			recipe.removeImage(image);
			File file = new File("./images/recipe/"+recipe.getId()+"/"+image.getFileName());
			file.delete();
			
			this.recipeService.save(recipe);
			this.imageService.delete(image);

			return "redirect:/recipe/"+recipe.getId();
		}
		else {
			return "redirect:/recipe/"+idRecipe;
		}
	}
}

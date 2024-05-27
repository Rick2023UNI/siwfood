package it.uniroma3.siwfood.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siwfood.model.Cook;
import it.uniroma3.siwfood.model.Image;
import it.uniroma3.siwfood.model.Ingredient;
import it.uniroma3.siwfood.model.Quantity;
import it.uniroma3.siwfood.model.Recipe;
import it.uniroma3.siwfood.service.ImageService;
import it.uniroma3.siwfood.service.IngredientService;
import it.uniroma3.siwfood.service.QuantityService;
import it.uniroma3.siwfood.service.RecipeService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
@Controller 
public class RecipeController {
	@Autowired RecipeService recipeService;
	@Autowired IngredientService ingredientService;
	@Autowired QuantityService quantityService;
	@Autowired ImageService imageService;
	
	@GetMapping("/")
    public String index(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("recipes", this.recipeService.findAll());
		if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("admin"))) {
			return "admin/index.html";
		}
		else {
			return "index.html";
		}
	}
	
	@GetMapping("/admin/")
    public String indexAdmin(Model model) {
		model.addAttribute("recipes", this.recipeService.findAll());
		return "admin/index.html";
	}
	
	@GetMapping("/newRecipe")
	public String addRecipe(Model model) {
		model.addAttribute("recipe", new Recipe());
		return "formNewRecipe.html";
	}

	@PostMapping(value="/recipe")
	public String newRecipe(@ModelAttribute("recipe") Recipe recipe, 
			@RequestParam("fileImage") MultipartFile[] multipartFiles) throws IOException {
		Date today=new Date();
		recipe.setPublicationDate(today);
		//Primo salvataggio per far assegnare alla ricetta un id
		this.recipeService.save(recipe);
		for (MultipartFile multipartFile : multipartFiles) {
			//Caricamento delle immagini
			String fileName=StringUtils.cleanPath(multipartFile.getOriginalFilename());
			/*Evita tentativo di caricare il file vuoto causato
			 dall'ultimo input che viene aggiunto in automatico
			 ed è sempre vuoto
			 */
			if (fileName!="") {
				Image image=new Image();
				image.setFileName(fileName);
				image.setFolder("recipe/"+recipe.getId());
				recipe.addImage(image);
				this.imageService.save(image);
				//File location
				String uploadDir="./images/recipe/"+recipe.getId();
				Path uploadPath = Paths.get(uploadDir);
				System.out.println();
				
				if (!Files.exists(uploadPath)) {
					try {
						Files.createDirectories(uploadPath);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					InputStream inputStream = multipartFile.getInputStream();
					Path filePath = uploadPath.resolve(fileName);
					Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					throw new IOException("Could not save the upload file: " + fileName);
				}
				//
			}
		}
		this.recipeService.save(recipe);
		return "redirect:recipe/"+recipe.getId();
	}
	
	@GetMapping("/recipe/{id}")
	public String getRecipe(@PathVariable("id") Long id, Model model) {
	    model.addAttribute("recipe", this.recipeService.findById(id));
	    model.addAttribute("quantities", this.recipeService.findById(id).getQuantities());
	    model.addAttribute("images", this.recipeService.findById(id).getImages());
	    return "recipe.html";
	}
	
	@PostMapping(value="/addQuantity/{id}")
	public String addQuantity(Model model, @PathVariable("id") Long id, @RequestParam("name") String nameParam, 
			@RequestParam("quantity") String quantityParam, 
			@RequestParam("fileImage") MultipartFile multipartFile) throws IOException {
		
		Recipe recipe=this.recipeService.findById(id);
		
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
			
			//Caricamento dell'immagine
			String fileName=StringUtils.cleanPath(multipartFile.getOriginalFilename());
			//Primo salvataggio per far assegnare all'ingrediente un id
			this.ingredientService.save(ingredient);
			//Impostazione del nome del file all'id dell'ingrediente, mantenendo l'estensione del file originale
			fileName=ingredient.getId()+fileName.substring(fileName.lastIndexOf('.'));
			Image image=new Image();
			image.setFileName(fileName);
			image.setFolder("ingredient");
			recipe.addImage(image);
			this.imageService.save(image);
			//Percorso del file
			String uploadDir="./images/ingredient/";
			Path uploadPath = Paths.get(uploadDir);
			System.out.println();
			
			if (!Files.exists(uploadPath)) {
				try {
					Files.createDirectories(uploadPath);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				InputStream inputStream = multipartFile.getInputStream();
				Path filePath = uploadPath.resolve(fileName);
				Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				throw new IOException("Could not save the upload file: " + fileName);
			}
			//
			ingredient.setImage(image);
			this.ingredientService.save(ingredient);
		}
		this.quantityService.save(quantity);
		this.recipeService.save(recipe);
		
		return "redirect:/recipe/"+recipe.getId();
	}
	
	@GetMapping("/removeQuantity/{idRecipe}/{idQuantity}")
	  public String removeQuantity(@PathVariable("idRecipe") Long idRecipe, 
			  @PathVariable("idQuantity") Long idQuantity, Model model) {
		    Recipe recipe=this.recipeService.findById(idRecipe);
		    recipe.removeQuantity(this.quantityService.findById(idQuantity));
		    this.recipeService.save(recipe);
		    
		    return "redirect:/recipe/"+recipe.getId();
	}	
	
	@GetMapping("/formUpdateRecipe/{id}")
	  public String formUpdateCook(@PathVariable("id") Long id, Model model) {
		    model.addAttribute("recipe", this.recipeService.findById(id));
		    //To load image if present
		    return "formUpdateRecipe.html";
		  }
	
	@PostMapping("/updateRecipe/{id}")
	public String updateRecipe(@PathVariable("id") Long id,
			@ModelAttribute("recipe") Recipe recipeUpdated, 
			@RequestParam("fileImage") MultipartFile multipartFile) throws IOException {
		Recipe recipe=this.recipeService.findById(id);
		//Caricamento dell'immagine
		String fileName=StringUtils.cleanPath(multipartFile.getOriginalFilename());
		Image image=new Image();
		image.setFileName(fileName);
		recipe.addImage(image);
		this.imageService.save(image);
		//Percorso del file
		String uploadDir="./images/recipe/"+recipe.getId();
		Path uploadPath = Paths.get(uploadDir);
		System.out.println();
		
		if (!Files.exists(uploadPath)) {
			try {
				Files.createDirectories(uploadPath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			InputStream inputStream = multipartFile.getInputStream();
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new IOException("Could not save the upload file: " + fileName);
		}
		//
		recipe.updateTo(recipeUpdated);
		this.recipeService.save(recipe);
		return "redirect:/recipe/"+recipe.getId();
	}
	
	@GetMapping("admin/manageRecipes")
	  public String manageRecipes(Model model) {
			model.addAttribute("recipes", this.recipeService.findAll());		    
		    return "admin/manageRecipes.html";
	}	
	
	@GetMapping("admin/removeRecipe/{id}")
	  public String removeRecipe(@PathVariable("id") Long id,
			  Model model) {
		    Recipe recipe=this.recipeService.findById(id);
		    this.recipeService.delete(recipe);
		    
		    return manageRecipes(model);
	}	
	
	@GetMapping("/searchRecipes")
	  public String formSearchRecipes(Model model) {
			model.addAttribute("recipes", this.recipeService.findByNameStartingWith(""));   
		    return "searchRecipes.html";
	}	
	
	@PostMapping("/searchRecipes")
	  public String searchRecipes(@ModelAttribute("recipe") Recipe recipe, @RequestParam String name, Model model) {
			model.addAttribute("recipes", this.recipeService.findByNameStartingWith(name));   
		    return "searchRecipes.html";
	}	
}

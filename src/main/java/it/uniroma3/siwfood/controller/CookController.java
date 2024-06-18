package it.uniroma3.siwfood.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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
import it.uniroma3.siwfood.model.Credentials;
import it.uniroma3.siwfood.model.Image;
import it.uniroma3.siwfood.model.Recipe;
import it.uniroma3.siwfood.service.CookService;
import it.uniroma3.siwfood.service.ImageService;

@Controller 
public class CookController {
	@Autowired CookService cookService;
	@Autowired ImageService imageService;

	@GetMapping("/admin/newCook")
	public String addCook(Model model) {
		model.addAttribute("cook", new Cook());
		return "admin/formNewCook.html";
	}

	@PostMapping("/cook")
	public String newCook(@ModelAttribute("cook") Cook cook,
			@RequestParam("fileImage") MultipartFile multipartFile) throws IOException {
		//Primo salvataggio per far assegnare al cuoco un id
		this.cookService.save(cook);
		//Caricamento dell'immagine
		String fileName=StringUtils.cleanPath(multipartFile.getOriginalFilename());
		Image image=new Image();
		//Impostazione del nome del file all'id dell'ingrediente e dell'estensione originale del file
		fileName=cook.getId()+fileName.substring(fileName.lastIndexOf('.'));
		image.setFileName(fileName);
		image.setFolder("cook");
		cook.setPhoto(image);
		this.imageService.save(image);
		this.cookService.save(cook);
		image.uploadImage(fileName, multipartFile);
		this.cookService.save(cook);
		return "redirect:/cook/"+cook.getId();
	}

	@GetMapping("/cook/{id}")
	public String getCook(@PathVariable("id") Long id, Model model) {
		model.addAttribute("cook", this.cookService.findById(id));
		model.addAttribute("recipes", this.cookService.findById(id).getRecipes());
		return "cook.html";
	}

	@GetMapping("/admin/formUpdateCook/{id}")
	public String formUpdateCook(@PathVariable("id") Long id, Model model) {
		model.addAttribute("cook", this.cookService.findById(id));
		//Da caricare un'immagine se presente
		return "admin/formUpdateCook.html";
	}

	@PostMapping("/admin/updateCook/{id}")
	public String updateCook(@PathVariable("id") Long id,
			@ModelAttribute("cook") Cook cookUpdated,
			@RequestParam("fileImage") MultipartFile multipartFile) throws IOException {
		Cook cook=this.cookService.findById(id);
		//Caricamento dell'immagine
		String fileName=StringUtils.cleanPath(multipartFile.getOriginalFilename());
		Image image=new Image();
		image.setFileName(fileName);
		cook.setPhoto(image);
		this.imageService.save(image);
		image.uploadImage(fileName, multipartFile);
		cook.updateTo(cookUpdated);
		this.cookService.save(cook);
		return "redirect:/cook/"+cook.getId();
	}

	@GetMapping("/cooks")
	public String showCooks(Model model) {
		model.addAttribute("cooks", this.cookService.findAll());
		return "cooks.html";
	}

	@GetMapping("admin/manageCooks")
	public String manageCooks(Model model) {
		model.addAttribute("cooks", this.cookService.findAll());		    
		return "admin/manageCooks.html";
	}	

	@GetMapping("admin/removeCook/{id}")
	public String removeCook(@PathVariable("id") Long id,
			Model model) {
		Cook cook=this.cookService.findById(id);
		this.cookService.delete(cook);

		return manageCooks(model);
	}	
}

package it.uniroma3.siwfood.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
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
import it.uniroma3.siwfood.service.CredentialsService;
import it.uniroma3.siwfood.service.ImageService;
import it.uniroma3.siwfood.validator.CredentialsValidator;
import jakarta.validation.Valid;

@Controller 
public class CookController {
	@Autowired CookService cookService;
	@Autowired ImageService imageService;
	@Autowired CredentialsService credentialsService;
	
	@Autowired PasswordEncoder passwordEncoder;
	//Validazione
	@Autowired CredentialsValidator credentialsValidator;

	@GetMapping("/admin/newCook")
	public String addCook(Model model) {
		model.addAttribute("credentials", new Credentials());
		model.addAttribute("cook", new Cook());
		return "admin/formNewCook.html";
	}

	@PostMapping("/admin/cook")
	public String newCook(@Valid @ModelAttribute("credentials") Credentials credentials,
			@ModelAttribute("cook") Cook cook,
			BindingResult bindingResult,
			@RequestParam("fileImage") MultipartFile multipartFile) throws IOException {
		//Validazione
		this.credentialsValidator.validate(credentials, bindingResult);
		if (!bindingResult.hasErrors()) {
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
			
			credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));
			credentials.setCook(cook);
			credentialsService.save(credentials);
			return "redirect:/admin/manageCooks";
		} 
		else {
			return "redirect:/admin/newCook";
		}
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
		fileName=cook.getId()+fileName.substring(fileName.lastIndexOf('.'));
		Image image=cook.getPhoto();
		image.delete();
		image.setFileName(fileName);
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

		return "redirect:/admin/manageCooks";
	}
	
	@PostMapping("/cooks")
	public String searchCooks(@RequestParam String name, @RequestParam String surname, Model model) {
		ArrayList<Cook> cooksByName=(ArrayList<Cook>) this.cookService.findByNameContaining(name);
		ArrayList<Cook> cooksBySurname=(ArrayList<Cook>) this.cookService.findBySurnameContaining(surname);
		cooksBySurname.retainAll(cooksByName);
		Cook cook=new Cook();
		cook.setName(name);
		cook.setSurname(surname);
		model.addAttribute("searchNameSurname", cook);
		model.addAttribute("cooks", cooksBySurname);
		return "cooks.html";
	}
	
	@PostMapping("admin/manageCooks")
	public String searchManageCooks(@RequestParam String name, @RequestParam String surname, Model model) {
		ArrayList<Cook> cooksByName=(ArrayList<Cook>) this.cookService.findByNameContaining(name);
		ArrayList<Cook> cooksBySurname=(ArrayList<Cook>) this.cookService.findBySurnameContaining(surname);
		cooksBySurname.retainAll(cooksByName);
		model.addAttribute("cooks", cooksBySurname);
		Cook cook=new Cook();
		cook.setName(name);
		cook.setSurname(surname);
		model.addAttribute("searchNameSurname", cook);
		return "admin/manageCooks.html";
	}

}

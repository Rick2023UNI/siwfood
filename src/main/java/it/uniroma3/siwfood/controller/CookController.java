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
import it.uniroma3.siwfood.model.Image;
import it.uniroma3.siwfood.service.CookService;
import it.uniroma3.siwfood.service.ImageService;

@Controller 
public class CookController {
	@Autowired CookService cookService;
	@Autowired ImageService imageService;
	
	@GetMapping("/newCook")
	public String addCook(Model model) {
		model.addAttribute("cook", new Cook());
		return "formNewCook.html";
	}
	

//	@PostMapping(value = "/cook", params = {"cook"})
//	public String newCook(@ModelAttribute("cook") Cook cook) {
//		this.cookService.save(cook);
//		return "redirect:cook/"+cook.getId();
//	}
//	
//	@PostMapping(value = "/cook", params = {"cook", "fileImage"})
	@PostMapping("/cook")
	public String newCook(@ModelAttribute("cook") Cook cook,
			@RequestParam("fileImage") MultipartFile multipartFile) throws IOException {
			//Primo salvataggio per far assegnare al cuoco un id
			this.cookService.save(cook);
			//Caricamento dell'immagine
				String fileName=StringUtils.cleanPath(multipartFile.getOriginalFilename());
				Image image=new Image();
				image.setFileName(fileName);
				//Impostazione del nome del file all'id dell'ingrediente e dell'estensione originale del file
				fileName=cook.getId()+fileName.substring(fileName.lastIndexOf('.'));
				cook.setPhoto(image);
				this.imageService.save(image);
				this.cookService.save(cook);
				//Percorso del file
				String uploadDir="./images/cook/";
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
		this.cookService.save(cook);
		return "redirect:/cook/"+cook.getId();
	}
	
	@GetMapping("/cook/{id}")
	public String getCook(@PathVariable("id") Long id, Model model) {
	    model.addAttribute("cook", this.cookService.findById(id));
	    model.addAttribute("recipes", this.cookService.findById(id).getRecipes());
	    return "cook.html";
	  }
	
	@GetMapping("/formUpdateCook/{id}")
	  public String formUpdateCook(@PathVariable("id") Long id, Model model) {
		    model.addAttribute("cook", this.cookService.findById(id));
		    //Da caricare un'immagine se presente
		    return "formUpdateCook.html";
		  }

	@PostMapping("/updateCook/{id}")
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
				//Percorso del file
				String uploadDir="./images/cook/"+cook.getId();
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
		cook.updateTo(cookUpdated);
		this.cookService.save(cook);
		return "redirect:/cook/"+cook.getId();
	}
	
	@GetMapping("/cooks")
	public String showCooks(Model model) {
		model.addAttribute("cooks", this.cookService.findAll());
		return "cooks.html";
	}
}

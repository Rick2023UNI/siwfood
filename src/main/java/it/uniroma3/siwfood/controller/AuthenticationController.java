package it.uniroma3.siwfood.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siwfood.model.Cook;
import it.uniroma3.siwfood.model.Credentials;
import it.uniroma3.siwfood.model.Image;
import it.uniroma3.siwfood.service.CookService;
import it.uniroma3.siwfood.service.CredentialsService;
import it.uniroma3.siwfood.service.ImageService;
import it.uniroma3.siwfood.service.RecipeService;

@Controller 
public class AuthenticationController {
	@Autowired CookService cookService;
	@Autowired CredentialsService credentialsService;
	@Autowired PasswordEncoder passwordEncoder;
	@Autowired RecipeService recipeService;

	@Autowired ImageService imageService;


	@GetMapping("/login")
	public String formLoginCook(Model model) {
		return "authentication/login.html";
	}

	@PostMapping("/login")
	public String loginCook(@RequestParam("credentials") Credentials credentials) {

		return "index.html";
	}

	@GetMapping("/register")
	public String formRegisterCook(Model model) {
		model.addAttribute("credentials", new Credentials());
		model.addAttribute("cook", new Cook());
		return "authentication/register.html";
	}

	@PostMapping("/register")
	public String registerCook(@ModelAttribute("credentials") Credentials credentials,
			@ModelAttribute("cook") Cook cook,
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

		credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));
		credentials.setCook(cook);
		credentialsService.save(credentials);

		return "redirect:/login";
	}

	@GetMapping("/success")
	public String success(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("recipes", this.recipeService.findAll());
		if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("admin"))) {
			return "admin/index.html";
		}
		else {
			return "index.html";
		}
	}

}

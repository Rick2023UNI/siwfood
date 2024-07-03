package it.uniroma3.siwfood.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
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
import it.uniroma3.siwfood.validator.CredentialsValidator;
import it.uniroma3.siwfood.validator.MultipartFileValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class AuthenticationController {
	@Autowired
	CookService cookService;
	@Autowired
	CredentialsService credentialsService;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	RecipeService recipeService;

	@Autowired
	ImageService imageService;

	// Validazione
	@Autowired
	CredentialsValidator credentialsValidator;
	@Autowired
	MultipartFileValidator multipartFileValidator;

	@GetMapping("/login")
	public String formLoginCook(Model model) {
		return "authentication/login.html";
	}

	@PostMapping("/login")
	public String loginCook(@RequestParam("credentials") Credentials credentials) {
		return "redirect:/";
	}

	@GetMapping("/register")
	public String formRegisterCook(Model model) {
		model.addAttribute("credentials", new Credentials());
		model.addAttribute("cook", new Cook());
		return "authentication/register.html";
	}

	@PostMapping("/register")
	public String registerCook(@Valid @ModelAttribute("credentials") Credentials credentials,
			BindingResult bindingResult, @ModelAttribute("cook") Cook cook,
			@RequestParam("fileImage") MultipartFile multipartFile) throws IOException {
		// Validazione
		this.credentialsValidator.validate(credentials, bindingResult);
		this.multipartFileValidator.validate(multipartFile, bindingResult);
		if (!bindingResult.hasErrors()) {
			// Primo salvataggio per far assegnare al cuoco un id
			this.cookService.save(cook);
			// Caricamento dell'immagine
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			// Impostazione del nome del file all'id dell'ingrediente e dell'estensione
			// originale del file
			fileName = cook.getId() + fileName.substring(fileName.lastIndexOf('.'));
			Image image = new Image();
			image.setFolder("cook");
			image.setFileName(fileName);
			this.imageService.save(image);
			image.uploadImage(fileName, multipartFile);

			cook.setPhoto(image);
			this.cookService.save(cook);

			credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));
			credentials.setCook(cook);
			credentialsService.save(credentials);

			return "authentication/login.html";
		} else {
			return "authentication/register.html";
		}
	}

	@GetMapping("/success")
	public String success(Model model) {
		return "redirect:/";
	}

	@GetMapping("/login-error")
	public String loginError(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		String errorMessage = null;
		if (session != null) {
			AuthenticationException ex = (AuthenticationException) session
					.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
			if (ex != null) {
				errorMessage = ex.getMessage();
			}
		}
		model.addAttribute("errorMessage", errorMessage);
		return "authentication/login.html";
	}

}

package it.uniroma3.siwfood.controller;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import it.uniroma3.siwfood.model.Cook;
import it.uniroma3.siwfood.service.CookService;
import it.uniroma3.siwfood.service.CredentialsService;

@ControllerAdvice
public class GlobalController {

	@Autowired CookService cookService;
	@Autowired CredentialsService credentialsService;

	@ModelAttribute("userDetails")
	public UserDetails getUser() {
		UserDetails user = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		return user;
	}

	@ModelAttribute("cookDetails")
	public Cook getCook() {
		UserDetails user = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		Cook cook=null;
		if (user!=null) {
			cook=credentialsService.getCredentials(user.getUsername()).getCook();
		}
		return cook;
	}
}


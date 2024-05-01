package it.uniroma3.siwfood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwfood.repository.RecipeRepository;

@Service
public class RecipeService {
	@Autowired
	private RecipeRepository RecipeRepository;
}

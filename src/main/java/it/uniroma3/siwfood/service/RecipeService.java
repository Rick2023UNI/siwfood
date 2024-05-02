package it.uniroma3.siwfood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwfood.model.Recipe;
import it.uniroma3.siwfood.repository.RecipeRepository;

@Service
public class RecipeService {
	@Autowired
	private RecipeRepository recipeRepository;
	
	public Iterable<Recipe> findAll() {
		return recipeRepository.findAll();
	}
	
	public void save(Recipe recipe) {
		recipeRepository.save(recipe);	
	}
	
	public Recipe findById(Long id) {
		return recipeRepository.findById(id).get();
	}
}

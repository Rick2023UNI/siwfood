package it.uniroma3.siwfood.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileSystemUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwfood.model.Ingredient;
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

	public void delete(Recipe recipe) throws IOException {
		try {
			FileUtils.deleteDirectory(new File("./images/recipe/"+recipe.getId()));
		} catch (IOException e) {
			throw new IOException("Could not delete the recipe's folder located at: " + "./images/recipe/"+recipe.getId());
		}
		recipeRepository.delete(recipe);	
	}

	public Iterable<Recipe> findByNameContaining(String name) {
		return recipeRepository.findByNameContaining(name);
	}
}

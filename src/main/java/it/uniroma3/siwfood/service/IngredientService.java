package it.uniroma3.siwfood.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwfood.model.Ingredient;
import it.uniroma3.siwfood.model.Recipe;
import it.uniroma3.siwfood.repository.IngredientRepository;

@Service
public class IngredientService {
	@Autowired
	private IngredientRepository ingredientRepository;

	public Iterable<Ingredient> findAll() {
		return ingredientRepository.findAll();
	}

	public void save(Ingredient ingredient) {
		ingredientRepository.save(ingredient);
	}

	public Ingredient findById(Long id) {
		return ingredientRepository.findById(id).get();
	}

	public Ingredient findByName(String name) {
		return ingredientRepository.findByName(name).get();
	}

	public boolean existsByName(String name) {
		return ingredientRepository.existsByName(name);
	}

	public void delete(Ingredient ingredient) {
		ingredient.getImage().delete();
		ingredientRepository.delete(ingredient);
	}

	public Iterable<Ingredient> findAllById(List<Long> ingredientsIds) {
		return ingredientRepository.findAllById(ingredientsIds);
	}

	public Iterable<Ingredient> findByNameContaining(String name) {
		return ingredientRepository.findByNameContaining(name);
	}

}

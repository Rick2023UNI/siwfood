package it.uniroma3.siwfood.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwfood.model.Ingredient;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
	
	//Da verificare se funzionante
	Optional<Ingredient> findByName(String name);

}

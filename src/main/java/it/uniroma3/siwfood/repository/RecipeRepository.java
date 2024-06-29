package it.uniroma3.siwfood.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siwfood.model.Ingredient;
import it.uniroma3.siwfood.model.Quantity;
import it.uniroma3.siwfood.model.Recipe;

import java.util.List;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

	Iterable<Recipe> findByNameContaining(String name);

}

package it.uniroma3.siwfood.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwfood.model.Image;
import it.uniroma3.siwfood.model.Recipe;

import java.util.List;

public interface ImageRepository extends CrudRepository<Image, Long> {

}
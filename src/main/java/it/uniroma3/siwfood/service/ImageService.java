package it.uniroma3.siwfood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwfood.model.Image;
import it.uniroma3.siwfood.model.Recipe;
import it.uniroma3.siwfood.repository.ImageRepository;
import it.uniroma3.siwfood.repository.RecipeRepository;

@Service
public class ImageService {
	@Autowired
	private ImageRepository imageRepository;
	
	public void save(Image image) {
		imageRepository.save(image);	
	}
	
	public Image findById(Long id) {
		return imageRepository.findById(id).get();
	}
}


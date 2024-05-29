package it.uniroma3.siwfood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwfood.model.Ingredient;
import it.uniroma3.siwfood.model.Quantity;
import it.uniroma3.siwfood.repository.QuantityRepository;
import jakarta.transaction.Transactional;

@Service
public class QuantityService {
	@Autowired
	private QuantityRepository quantityRepository;

	public void save(Quantity quantity) {
		quantityRepository.save(quantity);	
	}

	public Quantity findById(Long id) {
		return quantityRepository.findById(id).get();
	}
}

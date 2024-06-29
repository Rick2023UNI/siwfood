package it.uniroma3.siwfood.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwfood.repository.CookRepository;
import it.uniroma3.siwfood.model.Cook;

@Service
public class CookService {
	@Autowired
	private CookRepository cookRepository;

	public Iterable<Cook> findAll() {
		return cookRepository.findAll();
	}

	public void save(Cook cook) {
		cookRepository.save(cook);	
	}

	public void delete(Cook cook) {
		cookRepository.delete(cook);	
	}

	public Cook findById(Long id) {
		return cookRepository.findById(id).get();
	}

	public Iterable<Cook> findByNameContaining(String name) {
		return cookRepository.findByNameContaining(name);
	}

	public Iterable<Cook> findBySurnameContaining(String surname) {
		return cookRepository.findBySurnameContaining(surname);
	}

}

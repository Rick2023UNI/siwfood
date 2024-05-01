package it.uniroma3.siwfood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwfood.repository.CookRepository;

@Service
public class CookService {
	@Autowired
	private CookRepository cookRepository;
}

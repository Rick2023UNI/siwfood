package it.uniroma3.siwfood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwfood.model.Credentials;
import it.uniroma3.siwfood.repository.CredentialsRepository;

@Service
public class CredentialsService {
	@Autowired
	private CredentialsRepository credentialsRepository;

	public void save(Credentials credentials) {
		credentialsRepository.save(credentials);
	}

	public Credentials getCredentials(Long id) {
		return credentialsRepository.findById(id).get();
	}

	public Credentials getCredentials(String username) {
		return credentialsRepository.findByUsername(username).get();
	}

	public boolean existsByUsername(String username) {
		return credentialsRepository.existsByUsername(username);
	}
}

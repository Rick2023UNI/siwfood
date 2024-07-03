package it.uniroma3.siwfood.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwfood.model.Credentials;

import java.util.List;
import java.util.Optional;

public interface CredentialsRepository extends CrudRepository<Credentials, Long> {

	Optional<Credentials> findByUsername(String username);

	boolean existsByUsername(String username);

}

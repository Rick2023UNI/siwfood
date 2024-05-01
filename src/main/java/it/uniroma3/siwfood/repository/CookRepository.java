package it.uniroma3.siwfood.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwfood.model.Cook;
import java.util.List;

public interface CookRepository extends CrudRepository<Cook, Long> {

}

package it.uniroma3.siwfood.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwfood.model.Quantity;

public interface QuantityRepository extends CrudRepository<Quantity, Long> {

}

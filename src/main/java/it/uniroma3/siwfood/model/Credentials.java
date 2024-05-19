package it.uniroma3.siwfood.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Credentials {
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	private String username;
	
	private String password;
	
	private String role;
	
	@OneToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	private Cook cook;

}

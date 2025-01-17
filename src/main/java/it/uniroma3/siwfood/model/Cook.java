package it.uniroma3.siwfood.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

@Entity
public class Cook {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank(message = "Il nome è richiesto")
	private String name;

	@NotBlank(message = "Il cognome è richiesto")
	private String surname;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;

	@OneToOne
	private Image photo;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "cook")
	private Credentials credentials;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	// Method that updates the cook
	public void updateTo(Cook cookUpdated) {
		this.setName(cookUpdated.getName());
		this.setSurname(cookUpdated.getSurname());
		this.setBirthday(cookUpdated.getBirthday());
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public Image getPhoto() {
		return photo;
	}

	public void setPhoto(Image photo) {
		this.photo = photo;
	}

	public List<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}

	@OneToMany(mappedBy = "cook", cascade = CascadeType.ALL)
	private List<Recipe> recipes;

	@Override
	public boolean equals(Object o) {
		Cook cook = (Cook) o;
		return cook.getId() == this.getId();
	}
}

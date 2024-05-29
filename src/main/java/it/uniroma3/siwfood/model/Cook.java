package it.uniroma3.siwfood.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Cook {
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

	//Method that updates the cook
	public void updateTo(Cook cookUpdated) {
		this.setName(cookUpdated.getName());
		this.setSurname(cookUpdated.getSurname());
		this.setBirthday(cookUpdated.getBirthday());
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private String surname;

	@DateTimeFormat(pattern ="yyyy-MM-dd")
	private Date birthday;

	@OneToOne
	private Image photo;

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

	@OneToMany(mappedBy = "cook")
	private List<Recipe> recipes;

	@Override
	public boolean equals(Object o) {
		Cook cook=(Cook) o;
		return cook.getId()==this.getId();
	}
}

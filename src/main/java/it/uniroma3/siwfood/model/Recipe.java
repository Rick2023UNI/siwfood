package it.uniroma3.siwfood.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Recipe {

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public List<Quantity> getQuantities() {
		return quantities;
	}

	public void setQuantities(List<Quantity> quantities) {
		this.quantities = quantities;
	}

	public Cook getCook() {
		return cook;
	}

	public void setCook(Cook cook) {
		this.cook = cook;
	}

	public void addQuantity(Quantity quantity) {
		if (this.quantities==null) {
			this.quantities= new ArrayList<Quantity>();
		}
		if (!(this.quantities.contains(quantity))) {
			this.quantities.add(quantity);
		}
	}

	public void removeQuantity(Quantity byId) {
		this.quantities.remove(this.quantities.indexOf(byId));
	}

	public void addImage(Image image) {
		if (this.images==null) {
			this.images= new ArrayList<Image>();
		}
		if (!(this.images.contains(image))) {
			this.images.add(image);
		}

	}

	public void removeImage(Image image) {
		this.images.remove(this.images.indexOf(image));
	}

	//Method that updates the recipe
	public void updateTo(Recipe recipeUpdated) {
		this.setName(recipeUpdated.getName());
		this.setDescription(recipeUpdated.getDescription());
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	private String description;

	private Date publicationDate;

	@OneToMany
	private List<Image> images;

	@ManyToOne
	private Cook cook;
	
	//Eliminazione ingrediente
	@OneToMany(mappedBy="recipe", cascade=CascadeType.ALL)
	private List<Quantity> quantities;
}

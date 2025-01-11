package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Category name cannot be null")
    @Column(nullable = false)
    private String name;


    @NotNull(message = "Category desc cannot be null")
    @Column(nullable = true)
    private String description;
    
    @NotNull(message = "Category img cannot be null")
    @Column(name="img_path", nullable = false)
    private String imgPath;
//
//    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
//    @JsonBackReference
//    private List<Product> products;

    // Default constructor
    public Category() {}
    
    

	public Category(Long id, @NotNull(message = "Category name cannot be null") String name,
		@NotNull(message = "Category img cannot be null") String imgPath) {
	super();
	this.id = id;
	this.name = name;
	this.imgPath = imgPath;
}



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

	public String getImgPath() {
		return imgPath;
	}
	


	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getDescrption() {
		return description;
	}

	public void setDescription(String desc) {
		this.description = desc;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", imgPath=" + imgPath + "]";
	}

    
    
}

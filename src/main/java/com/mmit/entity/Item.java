package com.mmit.entity;

import java.io.Serializable;
import javax.persistence.*;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.LAZY;

/**
 * Entity implementation class for Entity: Item
 *
 */
@Entity
@NamedQuery(name="Item.getItems" ,query="SELECT i FROM Item i ")
@NamedQuery(name="Item.searchByPrice" ,query="SELECT i FROM Item i WHERE i.price>= :val1 AND i.price<=:val2")

public class Item implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_id")
	private int id;
	@Column(name="name")
	private String name;
	@Column(name="price")
	private int price;

	@ManyToOne(optional = false, cascade = PERSIST)
	@JoinColumn(name = "category_id", referencedColumnName = "id")
	private Category category;
	private static final long serialVersionUID = 1L;
	
	
	
	

	public Category getCategory() {
		return category;
	}



	public void setCategory(Category category) {
		this.category = category;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public int getPrice() {
		return price;
	}



	public void setPrice(int price) {
		this.price = price;
	}



	public Item() {
		super();
	}
   
}

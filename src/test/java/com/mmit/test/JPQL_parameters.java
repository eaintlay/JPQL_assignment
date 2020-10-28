package com.mmit.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mmit.entity.Item;
import com.mmit.entity.dto.ItemCategory;
import com.mmit.entity.dto.ItemPriceDTO;

class JPQL_parameters {
	
	private static EntityManagerFactory emf;
	private EntityManager em;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		emf=Persistence.createEntityManagerFactory("jpa-em-transaction");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		emf.close();
	}

	@BeforeEach
	void setUp() throws Exception {
		em=emf.createEntityManager();
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
	}
	@Test
	void testForNew_Op() {
		String sql="SELECT new com.mmit.entity.dto.ItemCategory(i,i.category.name) FROM Item i";
		TypedQuery<ItemCategory> query=em.createQuery(sql,ItemCategory.class);
		
		List<ItemCategory> items=query.getResultList();
		items.forEach(i->{
			System.out.println(i.getItem().getName()+"\t"+i.getCategoryName());
		});
		
	}
	//@Test
	void testForJoin() {
		//input catName="juices"
		//find items under catName
		String sql="SELECT new com.mmit.entity.dto.ItemPriceDTO(i.price,i.name) FROM Item i WHERE i.category.name= :catName";
		TypedQuery<ItemPriceDTO> query=em.createQuery(sql, ItemPriceDTO.class);
		query.setParameter("catName", "Juices");
		List<ItemPriceDTO> items=query.getResultList();
		items.forEach(i->{
			System.out.println(i.getName()+"\t"+i.getPrice());
		});
	}
	
	//@Test
	void testForIN() {
		TypedQuery<Item> query=em.createQuery("SELECT i FROM Item i WHERE i.category.id IN :ids", Item.class);
		
		List<Integer> values=Arrays.asList(1,2);
		
		query.setParameter("ids", values);
		
		List<Item> items=query.getResultList();
		
		items.forEach(i->{
			System.out.println(i.getName()+"\t"+i.getCategory().getId());
		});
		
		
		
		
	}
	//@Test
	void testForBetween_And() {
		TypedQuery<Item> query=em.createNamedQuery("Item.searchByPrice", Item.class);
		query.setParameter("val1", 1000);
		query.setParameter("val2", 2000);
		
		List<Item> items=query.getResultList();
		items.forEach(i->{
			System.out.println(i.getName()+"\t"+i.getPrice()+i.getCategory().getName());
		});
	}
	
	//@Test
	void testForLikeOperator() {
		TypedQuery<String> query=em.createQuery("SELECT i.name FROM Item i WHERE i.name LIKE : name",String.class);
		
        query.setParameter("name", "%o%");
        
        List<String> names=query.getResultList();
		
		names.forEach(s->System.out.println(s));
		
	}
	

	//@Test
	void testForAggr_Fun() {
		TypedQuery<Integer> query=em.createQuery("SELECT MAX(i.price) FROM Item i",Integer.class);
		
		Integer max_price=query.getSingleResult();
		
		System.out.println("Max Price: "+max_price);
	}

	//@Test
	void testForNameParameter() {
		TypedQuery<Item> query=em.createQuery("SELECT i FROM Item i WHERE i.price> ?1",Item.class);
		//query.setParameter("pri", 1000);
		query.setParameter(1, 1000);
		
		List<Item> items=query.getResultList();
		
		items.forEach(i->{
			System.out.println(i.getId()+"\t"+i.getName()+"\t"+i.getPrice());
		});
		
		
	}

}

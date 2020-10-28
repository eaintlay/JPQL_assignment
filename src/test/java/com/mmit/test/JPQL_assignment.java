package com.mmit.test;

import static org.junit.jupiter.api.Assertions.*;


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

class JPQL_assignment {
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
	void testForCategory() {
		TypedQuery<Integer> query=em.createQuery("SELECT MAX(i.price) FROM Item i",Integer.class);
		
        Integer max_price=query.getSingleResult();
		
		System.out.println("Max Price: "+max_price);
		
		
		TypedQuery<Item> query1=em.createQuery("SELECT i FROM Item i ",Item.class);
		
		
		List<Item> items=query1.getResultList();
		
		items.forEach(i->{
			System.out.println(i.getName());
		});
	}

}

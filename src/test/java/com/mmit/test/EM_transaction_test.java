package com.mmit.test;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.mmit.entity.Category;
import com.mmit.entity.Item;

@TestMethodOrder(OrderAnnotation.class)

class EM_transaction_test {
	
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
	@Order(1)
	void test() {
		em.getTransaction().begin();
		
		//insert item to db
		
		//create item
		Item item=new Item();
		item.setName("Orange");
		item.setPrice(500);
		
		Category c=new Category();
		c.setName("Juice");
		
		//em.persist(c);
		
		item.setCategory(c);
		
		//persist
		em.persist(item);
		
		//commit
		em.getTransaction().commit();
	}
	@Test
	@Order(2)
	void test2() {
		em.getTransaction().begin();
		
		Item item=new Item();
		item.setName("Cola");
		item.setPrice(500);
		
		//Category c=em.find(Category.class, 1);
		Category c=new Category();
		c.setName("Snack");
		
		item.setCategory(c);
		
		em.persist(item);
		
		em.getTransaction().commit();
	}
	//@Test
	//@Order(3)
	void test1() {
		System.out.println("............Test1............");
		Item item=em.find(Item.class,1);
		System.out.println("Id: "+item.getId());
		em.clear();
		System.out.println("cateName: "+item.getCategory().getName());
	}
	//@Test
	//@Order(3)
	void testForUpdate() {
		System.out.println(".......Test for update.......");
		em.getTransaction().begin();
		Item item=em.find(Item.class, 1);//manage state
		
		em.clear();//detach state
		
		item.setPrice(1000);
		
		em.merge(item);
		
		em.getTransaction().commit();
	}
	@Test
	@Order(3)
	void testForDelete() {
		System.out.println("...........Test for delete...........");
		
		em.getTransaction().begin();
		
		Item item=em.find(Item.class, 1);
		
		em.remove(item);
		
		em.getTransaction().commit();
		
		//Item item1=em.find(Item.class, 1);// null
		
		//System.out.println("item1 name: "+item1.getName());
		
	}

}

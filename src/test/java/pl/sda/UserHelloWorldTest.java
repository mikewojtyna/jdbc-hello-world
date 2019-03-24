package pl.sda;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.*;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserHelloWorldTest {

	private EntityManager entityManager;
	private EntityManagerFactory factory;

	@BeforeEach
	void setUp() {
		factory = Persistence
			.createEntityManagerFactory("hello" + "-world");
		entityManager = factory.createEntityManager();
	}

	@AfterEach
	void tearDown() {
		factory.close();
	}

	@DisplayName("show how to persist entity in jpa")
	@Test
	void test() throws Exception {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(new User("Bartek", 34));
		transaction.commit();

		TypedQuery<User> query = entityManager
			.createQuery("SELECT u FROM User AS u", User.class);
		List<User> resultList = query.getResultList();

		assertThat(resultList).hasSize(1);
	}

	@DisplayName("show how to query in jpa")
	@Test
	void test1() throws Exception {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(new User("Denys", 33));
		entityManager.persist(new User("Marcin", 77));
		entityManager.persist(new User("Bożena", 18));
		transaction.commit();

		TypedQuery<User> query = entityManager
			.createQuery("SELECT u " + "FROM User AS u WHERE u" +
				".name = 'Bożena'", User.class);

		List<User> allMatchingUsers = query.getResultList();

		assertThat(allMatchingUsers).hasSize(1)
			.extracting(User::getName).containsOnly("Bożena");
	}

	@DisplayName("show how to create and find user with multiple " +
		"addresses")
	@Test
	void test2() throws Exception {
		User user = new User("Denys", 33);
		Address address = new Address("Sniadeckich", "Bydgoszcz",
			"Polska");
		Address secondAddress = new Address("Uniwersalna", "Dnipro",
			"Ukraina");
		Address thirdAddress = new Address("Slonieczan", "Poznan",
			"Polska");

		user.addAddress(address);
		user.addAddress(secondAddress);
		user.addAddress(thirdAddress);

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(user);
		//entityManager.persist(address);
		//entityManager.persist(secondAddress);
		//entityManager.persist(thirdAddress);
		transaction.commit();

		User foundUser = entityManager.find(User.class, user.getId());

		assertThat(foundUser.getAddresses()).hasSize(3);
	}
}

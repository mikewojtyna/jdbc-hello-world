package pl.sda;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;

public class EmployeeDaoTest {

	private EmployeeDao employeeDao;
	private Connection connection;

	@BeforeEach
	void setup() throws SQLException {
		connection = initDb();
		employeeDao = new JdbcEmployeeDao(connection);
	}

	@AfterEach
	void clean() throws SQLException {
		connection.close();
	}

	@DisplayName("should add new employee")
	@Test
	void test() throws Exception {
		// given
		Employee leszek = new Employee("Leszek");

		// when
		employeeDao.add(leszek);

		// then
		Collection<Employee> allEmployees = employeeDao
			.findByName("Leszek");
		Assertions.assertThat(allEmployees).containsOnly(leszek);
	}

	@DisplayName("should find employee by names")
	@Test
	void test1() throws Exception {
		// given
		Employee leszek0 = new Employee("Leszek");
		Employee bartek = new Employee("Bartek");
		Employee jakub = new Employee("Jakub");
		Employee leszek1 = new Employee("Leszek");
		employeeDao.add(leszek0);
		employeeDao.add(bartek);
		employeeDao.add(jakub);
		employeeDao.add(leszek1);

		// when
		Collection<Employee> allEmployees = employeeDao
			.findByName("Leszek");

		// then
		Assertions.assertThat(allEmployees)
			.containsOnly(leszek0, leszek1);
	}

	@DisplayName("should delete employee")
	@Test
	void test2() throws Exception {
		// given
		Employee michal = new Employee("Michal");
		Employee rafal = new Employee("Rafal");
		employeeDao.add(michal);
		int id = employeeDao.add(rafal);

		// when
		employeeDao.delete(id);

		// then
		Assertions.assertThat(employeeDao.findByName("Rafal"))
			.isEmpty();
	}

	private Connection initDb() throws SQLException {
		Connection connection = DriverManager
			.getConnection("jdbc:h2" + ":mem:");
		connection.createStatement()
			.execute("CREATE TABLE employee " + "(id int PRIMARY " + "KEY AUTO_INCREMENT" + ", name " + "varchar(30))");
		return connection;
	}
}

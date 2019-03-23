package pl.sda;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class JdbcEmployeeDao implements EmployeeDao {
	private static final String INSERT_EMPLOYEE_STATEMENT =
		"INSERT INTO " + "employee (name) VALUES (?)";
	private static final String FIND_EMPLOYEE_STATEMENT =
		"SELECT name " + "FROM employee WHERE name = ?";

	private static final String DELETE_EMPLOYEE_STATEMENT =
		"DELETE FROM " + "employee WHERE id = ?";

	private Connection connection;

	public JdbcEmployeeDao(Connection connection) {
		this.connection = connection;
	}

	@Override
	public int add(Employee employee) {
		try (PreparedStatement insertStatement = connection
			.prepareStatement(INSERT_EMPLOYEE_STATEMENT,
				Statement.RETURN_GENERATED_KEYS)) {
			insertStatement.setString(1, employee.getName());
			insertStatement.execute();
			ResultSet generatedKeys = insertStatement
				.getGeneratedKeys();
			generatedKeys.next();
			int generatedId = generatedKeys.getInt("id");
			return generatedId;
		}
		catch (SQLException e) {
			throw new RuntimeException("Failed to add employee.",
				e);
		}
	}

	@Override
	public void delete(int id) {
		try (PreparedStatement deleteStatement = connection
			.prepareStatement(DELETE_EMPLOYEE_STATEMENT)) {
			deleteStatement.setInt(1, id);
			deleteStatement.execute();
		}
		catch (SQLException e) {
			throw new RuntimeException("Failed to delete employee" + ".", e);
		}

	}

	@Override
	public void update(int id, Employee newEmployee) {

	}

	@Override
	public Collection<Employee> findByName(String name) {
		try (PreparedStatement findStatement = connection
			.prepareStatement(FIND_EMPLOYEE_STATEMENT)) {
			findStatement.setString(1, name);
			ResultSet resultSet = findStatement.executeQuery();
			Collection<Employee> employeedList = new ArrayList();
			while (resultSet.next()) {
				employeedList.add(new Employee(resultSet
					.getString("name")));
			}
			return employeedList;
		}
		catch (SQLException e) {
			throw new RuntimeException("Failed to find employee.",
				e);
		}
	}
}

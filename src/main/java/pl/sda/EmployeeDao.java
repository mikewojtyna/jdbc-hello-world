package pl.sda;

import java.util.Collection;

public interface EmployeeDao {
	int add(Employee employee);

	void delete(int id);

	void update(int id, Employee newEmployee);

	Collection<Employee> findByName(String name);
}

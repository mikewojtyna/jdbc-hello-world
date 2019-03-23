package pl.sda;

import java.util.Objects;

public class Employee {
	private String name;

	public Employee(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Employee{" + "name='" + name + '\'' + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Employee employee = (Employee) o;
		return Objects.equals(name, employee.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	public String getName() {
		return name;
	}
}

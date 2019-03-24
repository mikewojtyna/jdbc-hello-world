package pl.sda;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
public class User {
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private int age;

	@OneToMany(cascade = CascadeType.PERSIST)
	private Collection<Address> addresses;

	protected User() {
	}

	public User(String name, int age) {
		this.name = name;
		this.age = age;
		addresses = new ArrayList<>();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		User user = (User) o;
		return id == user.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public String getName() {
		return name;
	}

	public void addAddress(Address address) {
		addresses.add(address);
	}

	public int getId() {
		return id;
	}

	public Collection<Address> getAddresses() {
		return addresses;
	}
}


package pl.sda;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Address {
	@Id
	@GeneratedValue
	private int id;
	private String street;
	private String city;
	private String country;

	protected Address() {
	}

	public Address(String street, String city, String country) {
		this.street = street;
		this.city = city;
		this.country = country;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Address address = (Address) o;
		return id == address.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}

package erp.application.employee.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Embeddable
public class Address {
	
	@NotNull
	@Size(max=100)
	private String country;
	@NotNull
	@Size(max=100)
	private String county;
	@NotNull
	@Size(max=100)
	private String city;
	@NotNull
	@Size(max=100)
	private String street;
	@NotNull
	@Size(max=100)
	private String number;
	
	public Address() {
		
	}

	public Address(String country, String county, String city, String street, String number) {
		super();
		this.country = country;
		this.county = county;
		this.city = city;
		this.street = street;
		this.number = number;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "Address [country=" + country + ", county=" + county + ", city=" + city + ", street=" + street
				+ ", number=" + number + "]";
	}


}

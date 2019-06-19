package com.automationpractice.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CityStateLookupResponse")
//@XmlType( propOrder = { "name", "city", "permanent", "special" } )
public class LocationData {
	private String state;
	private String zipCode;
	private String id;
	private String Zip5;
	private String city;

	public String getZipCode() {
		return this.zipCode;
	}

	@XmlElement(name = "ZipCode")
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getId() {
		return this.id;
	}

	@XmlAttribute(name = "ID")
	public void setId(String id) {
		this.id = id;
	}

	public String getZip() {
		return this.Zip5;
	}

	@XmlElement(name = "Zip5")
	public void setZip(String Zip5) {
		this.Zip5 = Zip5;
	}

	@XmlElement(name = "City")
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@XmlElement(name = "State")
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

}

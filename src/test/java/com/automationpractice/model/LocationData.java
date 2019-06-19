package com.automationpractice.model;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "CityStateLookupResponse")
public class LocationData {
	private String zip;
	private String city;
	private String state;

	public String getZip() {
		return zip;
	}

	@XmlElement(name = "Zip5")
	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCity() {
		return city;
	}

	@XmlElement(name = "City")
	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	@XmlElement(name = "State")
	public void setState(String state) {
		this.state = state;
	}

}

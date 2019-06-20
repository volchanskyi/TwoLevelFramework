package com.automationpractice.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "CityStateLookupResponse")

public class LocationData {
	@Element(name = "State")
	private String state;
	@Element(name = "ZipCode", required = false)
	private String zipCode;
	@Attribute(name = "ID", required = false)
	private String id;
	@Element(name = "Zip5")
	private String postalCode;
	@Element(name = "City")
	private String city;

	public String getZipCode() {
		return this.zipCode;
	}

	public String getZip() {
		return this.postalCode;
	}

	public String getCity() {
		return this.city;
	}

	public String getState() {
		return this.state;
	}

}

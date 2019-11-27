package com.automationpractice.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "CityStateLookupResponse")

public class LocationData implements LigalLocationDataInterface {
	@Element(name = "State", required = false)
	private String state;
	@Element(name = "ZipCode", required = false)
	private String zipCode;
	@Attribute(name = "ID", required = false)
	private int id;
	@Element(name = "Zip5", required = false)
	private String postalCode;
	@Element(name = "City", required = false)
	private String city;
	@Element(name = "Error", required = false)
	private boolean error;
	@Element(name = "Description", required = false)
	private String description;
	@Element(name = "Number", required = false)
	private int number;
	@Element(name = "Source", required = false)
	private String source;
	@Element(name = "HelpFile", required = false)
	private String helpFile;
	@Element(name = "HelpContext", required = false)
	private String helpContext;

	@Override
	public String getZipCode() {
		return this.zipCode;
	}

	@Override
	public String getZip() {
		return this.postalCode;
	}

	@Override
	public String getCity() {
		return this.city;
	}

	@Override
	public String getState() {
		return this.state;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	//States String format converter
	//Introduce map for states. examp. CA = CALIFORNIA, FL = FLORIDA
}

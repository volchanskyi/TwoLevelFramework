package com.automationpractice.datagenerators;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automationpractice.model.LocationData;

public class LocationDataHelper {

	protected static final Logger LOCATION_DATA_GEN_LOGGER = LoggerFactory.getLogger(LocationDataHelper.class);
	private final String USPS_CITY_STATE_LOOKUP = "http://production.shippingapis.com/ShippingAPITest.dll?API=CityStateLookup";

	public String[] getLocationData(String validFormatPostalCode) {
		try {
			InputStream xml = Request.Post(USPS_CITY_STATE_LOOKUP)
					.addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9")
					.bodyForm(Form.form().add("API", "CityStateLookup")
							.add("XML",
									"<CityStateLookupRequest USERID=\"559REMOT6381\"><ZipCode ID=\"0\"><Zip5>"
											+ validFormatPostalCode + "</Zip5></ZipCode></CityStateLookupRequest>")
							.build())
					.execute().returnContent().asStream();
			Serializer serializer = new Persister();
			LocationData parsedXml = serializer.read(LocationData.class, xml);
			String[] locationData = { parsedXml.getZip(), parsedXml.getCity(), parsedXml.getState() };
			return locationData;
		} catch (IOException e) {
			LOCATION_DATA_GEN_LOGGER.error(e.toString() + "Is ZIP valid?");
		} catch (Exception e) {
			LOCATION_DATA_GEN_LOGGER.error(e.toString() + "Is ZIP valid?");
		}
		return null;

	}

}

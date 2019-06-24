package com.automationpractice.appmanager;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.automationpractice.model.LocationData;

public class SoapHelper extends ApplicationManagerHelper {

	// String userId = getProperty("web.baseUrl");

	public static String[] getLocationData(String validFormatPostalCode) {
		try {
			InputStream xml = Request.Post("http://production.shippingapis.com/ShippingAPITest.dll?API=CityStateLookup")
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
			APP_MANAGER_LOGGER.error(e.toString() + "Is ZIP valid?");
		} catch (Exception e) {
			APP_MANAGER_LOGGER.error(e.toString() + "Is ZIP valid?");
		}
		return null;

	}

}

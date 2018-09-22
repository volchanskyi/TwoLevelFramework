package com.automationpractice.appmanager;

import java.net.MalformedURLException;

import org.openqa.selenium.By;

import com.automationpractice.model.LigalCredentials;
import com.automationpractice.model.Products;

public class PdpHelper extends HelperBase {

	public PdpHelper(ApplicationManager app) throws MalformedURLException {
		super(app);

	}

	public PdpHelper navigateToPdpUsingExistedCredentials(LigalCredentials creds, Products product) {
		wd.get(useProperty("web.baseUrl"));
		click(By.cssSelector(useProperty("locator.createAccBtn(RP)")));
		type(By.cssSelector(useProperty("locator.newUserNameField")), creds.getEmail());
		type(By.cssSelector(useProperty("locator.newUserNameField")), creds.getPassword());
		navigateTo(useProperty("web.baseUrl") + "index.php?id_product=" + product.getId() + "&controller=product");
		return this;

	}

	public HelperBase addProductToWishList() {
		click(By.cssSelector(useProperty("locator.addToWishListButton")));
		return this;
	}

	public boolean verifyWithOverlay(String overlay) {
		String activeOverlay = getActiveOverlay(By.cssSelector(useProperty("locator.newUserNameField")), overlay);
		return activeOverlay.equals(overlay);
	}

}

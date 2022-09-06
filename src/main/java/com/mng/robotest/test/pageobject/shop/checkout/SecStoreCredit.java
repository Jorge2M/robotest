package com.mng.robotest.test.pageobject.shop.checkout;

import org.openqa.selenium.WebElement;

import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecStoreCredit extends PageBase {

	private static final String XPATH_STORE_CREDIT_BLOCK = "//div[@class='customer-balance']";
	private static final String XPATH_STORE_CREDIT_OPTION = XPATH_STORE_CREDIT_BLOCK + "/div[@class[contains(.,'customer-balance-option')]]";
	private static final String XPATH_IMPORTE_STORE_CREDIT = "//p[@class='customer-balance-title']"; 
	
	public void selectSaldoEnCuenta() {
		click(XPATH_STORE_CREDIT_OPTION).exec();
	}

	public boolean isVisible() {
		return state(Visible, XPATH_STORE_CREDIT_BLOCK).check();
	}
	
	public boolean isChecked() {
		boolean isChecked = false;
		if (isVisible()) {
			WebElement optionStoreC = getElement(XPATH_STORE_CREDIT_OPTION);
			if (optionStoreC!=null && optionStoreC.getAttribute("class").contains("-checked")) {
				isChecked = true;
			}
		}
		return isChecked;
	}
	
	public float getImporte() {
		float precioFloat = -1;
		if (state(Visible, XPATH_IMPORTE_STORE_CREDIT).check()) {
			String precioTotal = getElement(XPATH_IMPORTE_STORE_CREDIT).getText();
			precioFloat = ImporteScreen.getFloatFromImporteMangoScreen(precioTotal);
		}
		return precioFloat;
	}	
}

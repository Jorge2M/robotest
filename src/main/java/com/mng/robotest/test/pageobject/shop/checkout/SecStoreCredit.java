package com.mng.robotest.test.pageobject.shop.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecStoreCredit extends PageBase {

	private static final String XPathStoreCreditBlock = "//div[@class='customer-balance']";
	private static final String XPathStoreCreditOption = XPathStoreCreditBlock + "/div[@class[contains(.,'customer-balance-option')]]";
	private static final String XPathImporteStoreCredit = "//p[@class='customer-balance-title']"; 

	public SecStoreCredit(WebDriver driver) {
		super(driver);
	}
	
	public void selectSaldoEnCuenta() {
		click(By.xpath(XPathStoreCreditOption)).exec();
	}

	public boolean isVisible() {
		return (state(Visible, By.xpath(XPathStoreCreditBlock)).check());
	}
	
	public boolean isChecked() {
		boolean isChecked = false;
		if (isVisible()) {
			WebElement optionStoreC = driver.findElement(By.xpath(XPathStoreCreditOption));
			if (optionStoreC!=null && optionStoreC.getAttribute("class").contains("-checked")) {
				isChecked = true;
			}
		}
		
		return isChecked;
	}
	
	public float getImporte() {
		float precioFloat = -1;
		if (state(Visible, By.xpath(XPathImporteStoreCredit)).check()) {
			String precioTotal = driver.findElement(By.xpath(XPathImporteStoreCredit)).getText();
			precioFloat = ImporteScreen.getFloatFromImporteMangoScreen(precioTotal);
		}
		
		return precioFloat;
	}	
}

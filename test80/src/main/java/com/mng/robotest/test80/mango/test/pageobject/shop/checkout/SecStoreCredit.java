package com.mng.robotest.test80.mango.test.pageobject.shop.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecStoreCredit {

	static String XPathStoreCreditBlock = "//div[@class='customer-balance']";
	static String XPathStoreCreditOption = XPathStoreCreditBlock + "/div[@class[contains(.,'customer-balance-option')]]";
	static String XPathImporteStoreCredit = "//p[@class='customer-balance-title']"; 

	public static void selectSaldoEnCuenta(WebDriver driver) {
		click(By.xpath(XPathStoreCreditOption), driver).exec();
	}

    /**
     * @param driver
     * @return si es visible el bloque correspondiente al pago mediante Store Credit (Saldo en Cuenta)
     */
    public static boolean isVisible(WebDriver driver) {
    	return (state(Visible, By.xpath(XPathStoreCreditBlock), driver).check());
    }
    
    /**
     * @return si está marcado o no el radio del bloque correspondiente al pago mediante Store Credit (Saldo en Cuenta)
     */
    public static boolean isChecked(WebDriver driver) {
        boolean isChecked = false;
        if (isVisible(driver)) {
            WebElement optionStoreC = driver.findElement(By.xpath(XPathStoreCreditOption));
            if (optionStoreC!=null && optionStoreC.getAttribute("class").contains("-checked")) {
                isChecked = true;
            }
        }
        
        return isChecked;
    }
    
    /**
     * @param driver
     * @return el importe correspondiente al saldo en cuenta
     */
    public static float getImporte(WebDriver driver) {
        float precioFloat = -1;
        if (state(Visible, By.xpath(XPathImporteStoreCredit), driver).check()) {
            String precioTotal = driver.findElement(By.xpath(XPathImporteStoreCredit)).getText();
            precioFloat = ImporteScreen.getFloatFromImporteMangoScreen(precioTotal);
        }
        
        return precioFloat;
    }    
}

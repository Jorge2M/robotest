package com.mng.robotest.test80.mango.test.pageobject.shop.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;


public class SecStoreCredit extends WebdrvWrapp {

    static String XPathStoreCreditBlock = "//div[@class='customer-balance']";
    static String XPathStoreCreditOption = XPathStoreCreditBlock + "/div[@class[contains(.,'customer-balance-option')]]";
    static String XPathImporteStoreCredit = "//p[@class='customer-balance-title']"; 
    
    /**
     * Clickamos el bloque de Saldo en Cuenta
     * @param driver
     */
    public static void selectSaldoEnCuenta(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathStoreCreditOption));
    }
    
    /**
     * @param driver
     * @return si es visible el bloque correspondiente al pago mediante Store Credit (Saldo en Cuenta)
     */
    public static boolean isVisible(WebDriver driver) {
        return (isElementVisible(driver, By.xpath(XPathStoreCreditBlock)));
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
        if (isElementVisible(driver, By.xpath(XPathImporteStoreCredit))) {
            String precioTotal = driver.findElement(By.xpath(XPathImporteStoreCredit)).getText();
            precioFloat = ImporteScreen.getFloatFromImporteMangoScreen(precioTotal);
        }
        
        return precioFloat;
    }    
}

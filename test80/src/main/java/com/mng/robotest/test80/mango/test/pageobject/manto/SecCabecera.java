package com.mng.robotest.test80.mango.test.pageobject.manto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecCabecera {

	static String XPathLitTienda = "//td/span[@class='txt8BDis']";
	static String XPathButtonSelTienda = "//input[@type='submit' and @value[contains(.,'Seleccionar tienda')]]";
	static String XPathLinkVolverMenu = "//a[text()[contains(.,'volver al menu')]] | //a/img[@src='/images/logo-mango.png']";

	public static String getLitTienda(WebDriver driver) {
		String litTienda = "";
		if (state(Present, By.xpath(XPathLitTienda), driver).check()) {
			litTienda = driver.findElement(By.xpath(XPathLitTienda)).getText();    
		}
		return litTienda;
	}

    public static void clickButtonSelTienda(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonSelTienda));
    }
    
    /**
     * Selección del link "Volver al menú" y esperar los segundos especificados
     */
    public static void clickLinkVolverMenuAndWait(WebDriver driver, int seconds) throws Exception {
        //clickAndWaitLoad(driver, By.xpath(XPathLinkVolverMenu));
        waitClickAndWaitLoad(driver, seconds, By.xpath(XPathLinkVolverMenu));
        waitForPageLoaded(driver, seconds);
    }
}

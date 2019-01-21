package com.mng.robotest.test80.mango.test.pageobject.shop.modales;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

@SuppressWarnings("javadoc")
public class ModalDetalleMisCompras extends WebdrvWrapp {

    static String XPathModalInfoArticulo = "//div[@id='productDetailsContent']";
    static String XPathAspaForClose = "//button[@class[contains(.,'icofav-cerrar')]]";
    static String XPathBuscarTallaTiendaButton = "//div[@id='findInShop']";
    static String XPathReferenciaArticuloModal = "//li[@class='reference']";
    
    public static boolean isVisible(WebDriver driver) {
        return (isElementVisible(driver, By.xpath(XPathModalInfoArticulo))); 
    }
    
    public static void clickAspaForClose(WebDriver driver) {
        driver.findElement(By.xpath(XPathAspaForClose)).click();
    }
    
    public static void clickBuscarTallaTiendaButton (WebDriver driver) throws Exception {
    	clickAndWaitLoad(driver, By.xpath(XPathBuscarTallaTiendaButton), 3);
    }

	public static boolean isReferenciaValidaModal(String idArticulo, WebDriver driver) {
		String idArticuloModal = driver.findElement(By.xpath(XPathReferenciaArticuloModal)).getText();
		idArticuloModal = idArticuloModal.replace(" ", "");
		return idArticulo.replace(" ", "").equals(idArticuloModal);
	}
}

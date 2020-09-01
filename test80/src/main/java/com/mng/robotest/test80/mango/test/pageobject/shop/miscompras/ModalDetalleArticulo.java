package com.mng.robotest.test80.mango.test.pageobject.shop.miscompras;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class ModalDetalleArticulo extends PageObjTM {

    private static String XPathModalInfoArticulo = "//div[@id='productDetailsContent']";
    private static String XPathAspaForClose = "//button[@class[contains(.,'icofav-cerrar')]]";
    private static String XPathBuscarTallaTiendaButton = "//div[@id='findInShop']";
    private static String XPathContainerItem = "//div[@class='container-item']";
    private static String XPathContainerDescription = XPathContainerItem + "//div[@class[contains(.,'container-description-title')]]";
    private static String XPathReferencia = XPathContainerDescription + "//li[@class='reference']";
    private static String XPathNombre = XPathContainerDescription + "//li[1]";
    private static String XPathPrecio = XPathContainerDescription + "//ul/li[2]";
    
    private ModalDetalleArticulo(WebDriver driver) {
    	super(driver);
    }
    public static ModalDetalleArticulo getNew(WebDriver driver) {
    	return new ModalDetalleArticulo(driver);
    }
    
    public boolean isVisible() {
    	return (state(Visible, By.xpath(XPathModalInfoArticulo)).check());
    }
    
    public void clickAspaForClose() {
        driver.findElement(By.xpath(XPathAspaForClose)).click();
    }
    
    public void clickBuscarTallaTiendaButton() {
    	click(By.xpath(XPathBuscarTallaTiendaButton)).waitLoadPage(3).exec();
    }
    
    public String getReferencia() {
        return (driver.findElement(By.xpath(XPathReferencia)).getText().replaceAll("\\D+",""));
    }
    
    public String getNombre() {
        return (driver.findElement(By.xpath(XPathNombre)).getText());
    }
    
    public String getPrecio() {
        return (driver.findElement(By.xpath(XPathPrecio)).getText());
    }

	public boolean isReferenciaValidaModal(String idArticulo) {
		String idArticuloModal = driver.findElement(By.xpath(XPathReferencia)).getText();
		idArticuloModal = idArticuloModal.replace(" ", "");
		return idArticulo.replace(" ", "").equals(idArticuloModal);
	}
}

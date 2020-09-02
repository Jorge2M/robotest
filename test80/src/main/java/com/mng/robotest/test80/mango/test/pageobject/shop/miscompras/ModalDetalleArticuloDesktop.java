package com.mng.robotest.test80.mango.test.pageobject.shop.miscompras;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class ModalDetalleArticuloDesktop extends ModalDetalleArticulo {

    private static String XPathModalInfoArticulo = "//div[@id='productDetailsContent']";
    private static String XPathAspaForClose = "//button[@class[contains(.,'icofav-cerrar')]]";
    private static String XPathBuscarTallaTiendaButton = "//div[@id='findInShop']";
    private static String XPathContainerItem = "//div[@class='container-item']";
    private static String XPathContainerDescription = XPathContainerItem + "//div[@class[contains(.,'container-description-title')]]";
    private static String XPathReferencia = XPathContainerDescription + "//li[@class='reference']";
    private static String XPathNombre = XPathContainerDescription + "//li[1]";
    private static String XPathPrecio = XPathContainerDescription + "//ul/li[2]";
    
    public ModalDetalleArticuloDesktop(WebDriver driver) {
    	super(driver);
    }
    
    @Override
    public boolean isVisible() {
    	return (state(Visible, By.xpath(XPathModalInfoArticulo)).check());
    }
    @Override
    public void clickAspaForClose() {
        driver.findElement(By.xpath(XPathAspaForClose)).click();
    }
    @Override
    public String getReferencia() {
        return (driver.findElement(By.xpath(XPathReferencia)).getText().replaceAll("\\D+",""));
    }
    @Override
    public String getNombre() {
        return (driver.findElement(By.xpath(XPathNombre)).getText());
    }
    @Override
    public String getPrecio() {
        return (driver.findElement(By.xpath(XPathPrecio)).getText());
    }
    @Override
	public boolean isReferenciaValidaModal(String idArticulo) {
		String idArticuloModal = driver.findElement(By.xpath(XPathReferencia)).getText();
		idArticuloModal = idArticuloModal.replace(" ", "");
		return idArticulo.replace(" ", "").equals(idArticuloModal);
	}

    public void clickBuscarTallaTiendaButton() {
    	click(By.xpath(XPathBuscarTallaTiendaButton)).waitLoadPage(3).exec();
    }
}

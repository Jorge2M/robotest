package com.mng.robotest.test80.mango.test.pageobject.shop.miscompras;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class ModalDetalleArticuloMobile extends ModalDetalleArticulo {

    private static String XPathModalInfoArticulo = "//div[@class[contains(.,'_2FacL')]]";
    private static String XPathAspaForClose = "//i[@class[contains(.,'icon-outline-close')]]";
    private static String XPathReferencia = "//div[@class[contains(.,'YKRc7')]]";
    private static String XPathNombre = XPathModalInfoArticulo + "/div/div[@class='sg-subtitle']";
    private static String XPathPrecio = XPathModalInfoArticulo + "//div[@class[contains(.,'wO2G0')]]";
    
    public ModalDetalleArticuloMobile(WebDriver driver) {
    	super(driver);
    }
    
    @Override
    public boolean isVisible() {
    	return (state(Visible, By.xpath(XPathModalInfoArticulo)).wait(2).check());
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
}

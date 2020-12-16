package com.mng.robotest.test80.mango.test.pageobject.shop.miscompras;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class ModalDetalleArticuloDesktop extends ModalDetalleArticulo {

    private static String XPathModalInfoArticulo = "//div[@class[contains(.,'_3lBiU')]]"; //React
    private static String XPathAspaForClose = "//button[@class[contains(.,'A9wId')]]"; //React
    //private static String XPathBuscarTallaTiendaButton = "//div[@id='findInShop']";
    private static String XPathContainerItem = "//div[@class[contains(.,'_2KZf3')]]"; //React
    private static String XPathContainerDescription = XPathContainerItem + "//div[@class[contains(.,'_3pzeS')]]"; //React
    private static String XPathReferencia = XPathContainerDescription + "//div/div[3]";
    private static String XPathNombre = XPathContainerDescription + "//div[@class[contains(.,'sg-headline')]]";
    private static String XPathPrecio = XPathContainerDescription + "//div/div[2]/div[not(@class='_1LGHV')]"; //React
    
    public ModalDetalleArticuloDesktop(WebDriver driver) {
    	super(driver);
    }
    
    @Override
    public boolean isVisible(int maxSeconds) {
    	return (state(Visible, By.xpath(XPathModalInfoArticulo)).wait(maxSeconds).check());
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

    public boolean isInvisible(int maxSeconds) {
    	return (state(Invisible, By.xpath(XPathModalInfoArticulo)).wait(maxSeconds).check());
    }
    

//    public void clickBuscarTallaTiendaButton() {
//    	click(By.xpath(XPathBuscarTallaTiendaButton)).waitLoadPage(3).exec();
//    }
}

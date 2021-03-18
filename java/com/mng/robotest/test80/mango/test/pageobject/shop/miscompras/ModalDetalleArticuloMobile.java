package com.mng.robotest.test80.mango.test.pageobject.shop.miscompras;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class ModalDetalleArticuloMobile extends ModalDetalleArticulo {

    //private static String XPathModalInfoArticulo = "//div[@class[contains(.,'_3DqIY')]]"; //React
    private static String XPathAspaForClose = "//i[@class[contains(.,'icon-outline-close')]]";
    private static String XPathReferencia = "//*[@data-testid='myPurchases.detail.reference']";
    private static String XPathNombre = "//div[@class='sg-subtitle']";
    private static String XPathPrecio = "//*[@data-testid[contains(.,'product.paidPrice')]]"; 
    
    public ModalDetalleArticuloMobile(WebDriver driver) {
    	super(driver);
    }
    
    @Override
    public boolean isVisible(int maxSeconds) {
    	return (state(Visible, By.xpath(XPathReferencia)).wait(maxSeconds).check());
    }
    @Override
    public boolean isInvisible(int maxSeconds) {
    	return (state(Invisible, By.xpath(XPathReferencia)).wait(maxSeconds).check());
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
    	PageObjTM.state(State.Visible, By.xpath(XPathNombre), driver).wait(1).check();
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

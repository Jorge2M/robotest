package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.mercadopago;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.utils.otras.Channel;
import com.mng.testmaker.webdriverwrapper.WebdrvWrapp;

public abstract class PageMercpagoDatosTrj extends WebdrvWrapp {
	
	final WebDriver driver; 
	
    final static String XPathInputNumTarj = "//input[@name='cardNumber']";
    final static String XPathInputFecCaducidad = "//input[@name='cardExpiration']";
    final static String XPathInputCvc = "//input[@id='securityCode']";
    final static String XPathBotonPagar = "//input[@type='submit']";
    
    public enum TypePant {inputDataTrjNew, inputCvcTrjSaved} 
    
    abstract public boolean isPageUntil(int maxSecondsToWait);
    abstract public void sendCvc(String cvc);
    abstract public void sendCaducidadTarj(String fechaVencimiento);
    
    PageMercpagoDatosTrj(WebDriver driver) {
    	this.driver = driver;
    }
    
    public static PageMercpagoDatosTrj newInstance(Channel channel, WebDriver driver) {
    	switch (channel) {
    	case desktop:
    		return (PageMercpagoDatosTrjDesktop.newInstance(driver));
    	case movil_web:
    	default:
    		return (PageMercpagoDatosTrjMobil.newInstance(driver));
    	}
    }
    
    public void sendNumTarj(String numTarjeta) {
        driver.findElement(By.xpath(XPathInputNumTarj)).sendKeys(numTarjeta);
    }
    
    public TypePant getTypeInput() {
    	if (WebdrvWrapp.isElementVisible(driver, By.xpath(XPathInputNumTarj))) {
    		return TypePant.inputDataTrjNew;
    	}
    	
    	return TypePant.inputCvcTrjSaved;
    }
}

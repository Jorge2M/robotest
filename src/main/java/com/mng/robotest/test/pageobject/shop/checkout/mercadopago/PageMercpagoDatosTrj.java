package com.mng.robotest.test.pageobject.shop.checkout.mercadopago;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public abstract class PageMercpagoDatosTrj extends PageBase {
	
	static final String XPathInputNumTarj = "//input[@name='cardNumber']";
	static final String XPathInputFecCaducidad = "//input[@name='cardExpiration']";
	static final String XPathInputCvc = "//input[@id='securityCode']";
	static final String XPathBotonPagar = "//input[@type='submit']";
	
	public enum TypePant {inputDataTrjNew, inputCvcTrjSaved} 
	
	public abstract boolean isPageUntil(int maxSecondsToWait);
	public abstract void sendCvc(String cvc);
	public abstract void sendCaducidadTarj(String fechaVencimiento);
	
	PageMercpagoDatosTrj(WebDriver driver) {
		super(driver);
	}
	
	public static PageMercpagoDatosTrj newInstance(Channel channel, WebDriver driver) {
		switch (channel) {
		case desktop:
			return (PageMercpagoDatosTrjDesktop.newInstance(driver));
		case mobile:
		default:
			return (PageMercpagoDatosTrjMobil.newInstance(driver));
		}
	}
	
	public void sendNumTarj(String numTarjeta) {
		driver.findElement(By.xpath(XPathInputNumTarj)).sendKeys(numTarjeta);
	}
	
	public TypePant getTypeInput() {
		if (state(Visible, By.xpath(XPathInputNumTarj), driver).check()) {
			return TypePant.inputDataTrjNew;
		}
		
		return TypePant.inputCvcTrjSaved;
	}
}

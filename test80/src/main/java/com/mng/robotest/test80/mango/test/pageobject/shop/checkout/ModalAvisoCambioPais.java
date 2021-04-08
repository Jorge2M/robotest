package com.mng.robotest.test80.mango.test.pageobject.shop.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class ModalAvisoCambioPais extends PageObjTM {

	private final AppEcom app;
	
	private final static String XPathModalShop = "//div[@aria-labelledby[contains(.,'changeCountryModal')]]";
	private final static String XPathButtonConfCambioShop = XPathModalShop + "//button[@name='continue']";
	
	private final static String XPathModalOutlet = "//div[@class[contains(.,'modalDirEnvio')]]";
	private final static String XPathButtonConfCambioOutlet = XPathModalOutlet + "//a[@class[contains(.,'modalConfirmar')]]";
	
	public ModalAvisoCambioPais(AppEcom app, WebDriver driver) {
		super(driver);
		this.app = app;
	}
	
	private String getXPathModal() {
		if (app==AppEcom.outlet) {
			return XPathModalOutlet;
		}
		return XPathModalShop;
	}
	
	private String getXPathButtonConfCambio() {
		if (app==AppEcom.outlet) {
			return XPathButtonConfCambioOutlet;
		}
		return XPathButtonConfCambioShop;	
	}

	public boolean isVisibleUntil(int maxSeconds) {
		By byModal = By.xpath(getXPathModal());
		return (state(Visible, byModal).wait(maxSeconds).check());
	}

	public boolean isInvisibleUntil(int maxSeconds) {
		By byModal = By.xpath(getXPathModal());
		return (state(Invisible, byModal).wait(maxSeconds).check());
	}

	public void clickConfirmarCambio() {
		waitForPageLoaded(driver);
		By byConfirmar = By.xpath(getXPathButtonConfCambio());
		moveToElement(byConfirmar, driver);
		click(byConfirmar).exec();
	}
}

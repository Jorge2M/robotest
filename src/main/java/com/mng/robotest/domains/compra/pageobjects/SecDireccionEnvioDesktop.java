package com.mng.robotest.domains.compra.pageobjects;

import org.openqa.selenium.By;
import com.mng.robotest.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecDireccionEnvioDesktop extends PageBase {

	private static final String XPATH_SECTION_NEW = "//micro-frontend[@id='checkoutDeliveryAddressDesktop']";
	private static final String XPATH_EDIT_DIRECCION_BUTTON_NEW = XPATH_SECTION_NEW + "//button";
	private static final String XPATH_NOMBRE_ENVIO_NEW = XPATH_SECTION_NEW + "//div[@class[contains(.,'tpavy')]]"; 
	private static final String XPATH_DIRECCION_ENVIO_NEW = "//*[@data-testid='checkout.delivery.address']";
	
	//TODO eliminar cuando suba a pro la nueva versión
	private static final String XPATH_SECTION_OLD = "//div[@class[contains(.,'bloqueEnvio')]]";
	private static final String XPATH_EDIT_DIRECCION_BUTTON_OLD = XPATH_SECTION_OLD + "//span[@class[contains(.,'cambiarDatosEnvio')]]";
	private static final String XPATH_NOMBRE_ENVIO_OLD = XPATH_SECTION_OLD + "//div[@class[contains(.,'nombreEnvio')]]";
	private static final String XPATH_DIRECCION_ENVIO_OLD = "//div[@class[contains(.,'direccionEnvio')]]";
	private static final String XPATH_POBLACION_ENVIO_OLD = "//div[@class[contains(.,'poblacionEnvio')]]";
	private static final String XPATH_PROVINCIA_ENVIO_OLD = "//div[@class[contains(.,'provinciaEnvio')]]";

	private enum XPath {
		SECTION(XPATH_SECTION_NEW, XPATH_SECTION_OLD), 
		EDIT_DIRECCION_BUTTON(XPATH_EDIT_DIRECCION_BUTTON_NEW, XPATH_EDIT_DIRECCION_BUTTON_OLD), 
		NOMBRE_ENVIO(XPATH_NOMBRE_ENVIO_NEW, XPATH_NOMBRE_ENVIO_OLD),
		DIRECCION_ENVIO(XPATH_DIRECCION_ENVIO_NEW, XPATH_DIRECCION_ENVIO_OLD);
	
		private String xpathNew;
		private String xpathOld;
		private XPath(String xpathNew, String xpathOld) {
			this.xpathNew = xpathNew;
			this.xpathOld = xpathOld;
		}
		
		public String getXPathNew() {
			return xpathNew;
		}
		public String getXPathOld() {
			return xpathOld;
		}
	} 
	

	private String getXPath(XPath xpath) {
		if (isNewSection()) {
			return xpath.getXPathNew();
		}
		return xpath.getXPathOld();
	}
	
	private boolean isNewSection() {
		return state(Visible, XPath.SECTION.getXPathNew()).check();
	}
	
	public void clickEditDireccion() {
		waitLoadPage(); //For avoid StaleElementReferenceException
		click(getXPath(XPath.EDIT_DIRECCION_BUTTON)).exec();
	}

	public String getTextNombreEnvio() {
		return (driver.findElement(By.xpath(getXPath(XPath.NOMBRE_ENVIO))).getText());
	}
	
	public String getTextDireccionEnvio() {
		if (isNewSection()) {
			String xpathDireccion = getXPath(XPath.DIRECCION_ENVIO);
			if (state(Present, xpathDireccion).check()) {
				return getElement(xpathDireccion).getText();
			}
			return "";
		}
		if (state(Present, XPATH_DIRECCION_ENVIO_OLD).check()) {
			return (
				getElement(XPATH_DIRECCION_ENVIO_OLD).getText() + ", " +
				getElement(XPATH_POBLACION_ENVIO_OLD).getText() + ", " +
				getElement(XPATH_PROVINCIA_ENVIO_OLD).getText());
		}
		return "";
	}
}

package com.mng.robotest.domains.registro.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageRegistroAddressData extends PageObjTM {

	private static final String XPATH_TITLE_ADDRESS_STEPS = "//div[@class[contains(.,'addressData')]]";
	private static final String XPATH_DESPLEGABLE_PAISES = "//select[@id[contains(.,'pais')]]";
	private static final String XPATH_BUTTON_FINALIZAR = "//div[@class[contains(.,'registerStepsModal')]]//div[@class='submitContent']//input[@type='submit']";
	private static final String XPATH_ERROR_INPUT_DIRECCION = "//div[@id[contains(.,'cfDir1ErrorLabel')]]";	

	public PageRegistroAddressData(WebDriver driver) {
		super(driver);
	}
	
	private String getXPath_optionPaisSelected(String codigoPais) {
		return ("//select[@id[contains(.,'pais')]]/option[@selected='selected' and @value='" + codigoPais + "']");
	}

	public boolean isPageUntil(int secondsWait) {
		return (state(Present, By.xpath(XPATH_TITLE_ADDRESS_STEPS))
				.wait(secondsWait).check());
	}

	public boolean existsDesplegablePaises() {
		return (state(Present, By.xpath(XPATH_DESPLEGABLE_PAISES)).check());
	}

	public boolean isOptionPaisSelected(String codigoPais) {
		String xpathOptionPaisSelected = getXPath_optionPaisSelected(codigoPais);
		return (state(Present, By.xpath(xpathOptionPaisSelected)).check());
	}

	public void clickButtonFinalizar() {
		click(By.xpath(XPATH_BUTTON_FINALIZAR)).exec();
	}

	public boolean isVisibleErrorInputDireccion() {
		return (state(Visible, By.xpath(XPATH_ERROR_INPUT_DIRECCION)).check());
	}
}

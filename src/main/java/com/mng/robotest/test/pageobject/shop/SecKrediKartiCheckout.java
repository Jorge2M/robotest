package com.mng.robotest.test.pageobject.shop;

import org.openqa.selenium.Keys;
import com.mng.robotest.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecKrediKartiCheckout extends PageBase {

	private static final String XPATH_FORMULARIO_TARJETA = "//div[@class='msuFormularioTarjeta']";
	private static final String XPATH_INPUT_CARD_NUMBER = XPATH_FORMULARIO_TARJETA + "//input[@id[contains(.,'cardNumber')] or @id[contains(.,'msu_cardpan')] or @id[contains(.,'number-card')]]";
	private static final String XPATH_CAPA_PAGO_PLAZO_MOBIL = "//table[@class[contains(.,'installment-msu')]]";
	private static final String XPATH_CAPA_PAGO_PLAZO_DESKTOP = "//div[@class[contains(.,'installmentsTable')]]";			
	
	private String getXPathCapaPagoPlazo() {
		if (channel.isDevice()) {
			return XPATH_CAPA_PAGO_PLAZO_MOBIL;
		}
		return XPATH_CAPA_PAGO_PLAZO_DESKTOP;
	}
	
	private String getXPathCheckPlazos(int posicion) {
		String xpathDivPlazo = getXPathCapaPagoPlazo();
		if (channel.isDevice()) {
			return xpathDivPlazo + "//div[@class[contains(.,'custom-radio')] and @data-custom-radio-id][" + posicion + "]";
		}
		return xpathDivPlazo + "//input[@type='radio' and @name='installment'][" + posicion + "]"; 
	}
 
	public void inputCardNumberAndTab(String numTarj) {
		getElement(XPATH_INPUT_CARD_NUMBER).sendKeys(numTarj, Keys.TAB);
	}
	
	public boolean isVisiblePagoAPlazoUntil(int seconds) {
		return state(Visible, getXPathCapaPagoPlazo()).wait(seconds).check();
	}
	
	public void clickRadioPlazo(int posicion) {
		getElement(getXPathCheckPlazos(posicion)).click();
	}
}

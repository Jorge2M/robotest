package com.mng.robotest.test.pageobject.chequeregalo;

import org.openqa.selenium.By;

import com.mng.robotest.domains.footer.pageobjects.PageFromFooter;
import com.mng.robotest.test.generic.ChequeRegalo;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;


public class PageChequeRegaloInputDataOld extends PageChequeRegaloInputData implements PageFromFooter {
	
	private static final String XPATH_CONTENT = "//div[@class[contains(.,'chequeRegalo')]]";
	private static final String XPATH_LINK_QUIERO_COMPRAR = "//span[@id[contains(.,'oldGiftVoucherShowPurchasePageButton')]]";
	private static final String XPATH_BUTTON_COMPRAR = "//div[@id[contains(.,'checkout')]]";
	
	private static final String XPATH_INPUT_NOMBRE = "//input[@name[contains(.,'nombre')]]";
	private static final String XPATH_INPUT_APELLIDOS = "//input[@name[contains(.,'apellidos')]]";
	private static final String XPATH_INPUT_EMAIL = "//input[@name[contains(.,'email')]]";
	private static final String XPATH_INPUT_CONF_EMAIL = "//input[@name[contains(.,'email2')]]";
	private static final String XPATH_INPUT_MENSAJE = "//textarea[@name[contains(.,'mensaje')]]";
	
	@Override
	public boolean isPageCorrectUntil(int seconds) {
		return state(State.Present, XPATH_CONTENT).wait(seconds).check();
	}
	
	private String getXPathRadio(Importe importe) {
		return "//input[@name='cantidadCheque' and @value='" + importe.getImporte() + "']";
	}
	
	@Override
	public void clickImporteCheque(Importe importeToClick) {
		String xpath = getXPathRadio(importeToClick);
		click(xpath).exec();
	}
	
	@Override
	public void clickComprarIni() {
		click(XPATH_LINK_QUIERO_COMPRAR).exec();
	}
	
	@Override
	public boolean isVisibleDataInput(int seconds) {
		return state(State.Visible, XPATH_INPUT_NOMBRE).wait(seconds).check();
	}
	
	@Override
	public void inputDataCheque(ChequeRegalo chequeRegalo) {
		sendKeysWithRetry(chequeRegalo.getNombre(), By.xpath(XPATH_INPUT_NOMBRE), 2, driver);
		sendKeysWithRetry(chequeRegalo.getApellidos(), By.xpath(XPATH_INPUT_APELLIDOS), 2, driver);
		sendKeysWithRetry(chequeRegalo.getEmail(), By.xpath(XPATH_INPUT_EMAIL), 2, driver);
		sendKeysWithRetry(chequeRegalo.getEmail(), By.xpath(XPATH_INPUT_CONF_EMAIL), 2, driver);
		sendKeysWithRetry(chequeRegalo.getMensaje(), By.xpath(XPATH_INPUT_MENSAJE), 2, driver);
	}
	
	@Override
	public void clickComprarFin(ChequeRegalo chequeRegalo) {
		click(XPATH_BUTTON_COMPRAR).exec();
	}
	
	@Override
	public String getName() {
		return "Cheques regalo antiguo";
	}
 
}

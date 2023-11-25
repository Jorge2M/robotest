package com.mng.robotest.tests.domains.chequeregalo.pageobjects;

import org.openqa.selenium.By;

import com.mng.robotest.tests.domains.chequeregalo.beans.ChequeRegalo;
import com.mng.robotest.tests.domains.footer.pageobjects.PageFromFooter;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageChequeRegaloInputDataOld extends PageChequeRegaloInputData implements PageFromFooter {
	
	private static final String XP_CONTENT = "//div[@class[contains(.,'chequeRegalo')]]";
	private static final String XP_LINK_QUIERO_COMPRAR = "//span[@id[contains(.,'oldGiftVoucherShowPurchasePageButton')]]";
	private static final String XP_BUTTON_COMPRAR = "//div[@id[contains(.,'checkout')]]";
	
	private static final String XP_INPUT_NOMBRE = "//input[@name[contains(.,'nombre')]]";
	private static final String XP_INPUT_APELLIDOS = "//input[@name[contains(.,'apellidos')]]";
	private static final String XP_INPUT_EMAIL = "//input[@name[contains(.,'email')]]";
	private static final String XP_INPUT_CONF_EMAIL = "//input[@name[contains(.,'email2')]]";
	private static final String XP_INPUT_MENSAJE = "//textarea[@name[contains(.,'mensaje')]]";
	
	@Override
	public boolean isPageCorrectUntil(int seconds) {
		return state(Present, XP_CONTENT).wait(seconds).check();
	}
	
	private String getXPathRadio(Importe importe) {
		return "//input[@name='cantidadCheque' and @value='" + importe.getAmmount() + "']";
	}
	
	@Override
	public void clickImporteCheque(Importe importeToClick) {
		click(getXPathRadio(importeToClick)).exec();
	}
	
	@Override
	public void clickComprarIni() {
		click(XP_LINK_QUIERO_COMPRAR).exec();
	}
	
	@Override
	public boolean isVisibleDataInput(int seconds) {
		return state(Visible, XP_INPUT_NOMBRE).wait(seconds).check();
	}
	
	@Override
	public void inputDataCheque(ChequeRegalo chequeRegalo) {
		sendKeysWithRetry(chequeRegalo.getNombre(), By.xpath(XP_INPUT_NOMBRE), 2, driver);
		sendKeysWithRetry(chequeRegalo.getApellidos(), By.xpath(XP_INPUT_APELLIDOS), 2, driver);
		sendKeysWithRetry(chequeRegalo.getEmail(), By.xpath(XP_INPUT_EMAIL), 2, driver);
		sendKeysWithRetry(chequeRegalo.getEmail(), By.xpath(XP_INPUT_CONF_EMAIL), 2, driver);
		sendKeysWithRetry(chequeRegalo.getMensaje(), By.xpath(XP_INPUT_MENSAJE), 2, driver);
	}
	
	@Override
	public void clickComprarFin(ChequeRegalo chequeRegalo) {
		click(XP_BUTTON_COMPRAR).exec();
	}
	
	@Override
	public String getName() {
		return "Cheques regalo antiguo";
	}
 
}

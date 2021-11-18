package com.mng.robotest.test80.mango.test.pageobject.chequeregalo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.generic.ChequeRegalo;
import com.mng.robotest.test80.mango.test.pageobject.shop.footer.PageFromFooter;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

public class PageChequeRegaloInputDataOld extends PageChequeRegaloInputData implements PageFromFooter {
	
	private static final String XPathContent = "//div[@class[contains(.,'chequeRegalo')]]";
	private static final String XPathLinkQuieroComprar = "//span[@id[contains(.,'oldGiftVoucherShowPurchasePageButton')]]";
	private static final String XPathButtonComprar = "//div[@id[contains(.,'checkout')]]";
	
	private static final String XPathInputNombre = "//input[@name[contains(.,'nombre')]]";
	private static final String XPathInputApellidos = "//input[@name[contains(.,'apellidos')]]";
	private static final String XPathInputEmail = "//input[@name[contains(.,'email')]]";
	private static final String XPathInputConfEmail = "//input[@name[contains(.,'email2')]]";
	private static final String XPathInputMensaje = "//textarea[@name[contains(.,'mensaje')]]";
	
	public PageChequeRegaloInputDataOld(WebDriver driver) {
		super(driver);
	}
	
	@Override
	public boolean isPageCorrectUntil(int maxSeconds) {
		return state(State.Present, By.xpath(XPathContent)).wait(maxSeconds).check();
	}
	
	private String getXPathRadio(Importe importe) {
		return "//input[@name='cantidadCheque' and @value='" + importe.getImporte() + "']";
	}
	
	@Override
	public void clickImporteCheque(Importe importeToClick) {
		String xpath = getXPathRadio(importeToClick);
		click(By.xpath(xpath)).exec();
	}
	
	@Override
	public void clickComprarIni() {
		click(By.xpath(XPathLinkQuieroComprar)).exec();
	}
	
	@Override
	public boolean isVisibleDataInput(int maxSeconds) {
		return state(State.Visible, By.xpath(XPathInputNombre)).wait(maxSeconds).check();
	}
	
	@Override
	public void inputDataCheque(ChequeRegalo chequeRegalo) {
		sendKeysWithRetry(chequeRegalo.getNombre(), By.xpath(XPathInputNombre), 2, driver);
		sendKeysWithRetry(chequeRegalo.getApellidos(), By.xpath(XPathInputApellidos), 2, driver);
		sendKeysWithRetry(chequeRegalo.getEmail(), By.xpath(XPathInputEmail), 2, driver);
		sendKeysWithRetry(chequeRegalo.getEmail(), By.xpath(XPathInputConfEmail), 2, driver);
		sendKeysWithRetry(chequeRegalo.getMensaje(), By.xpath(XPathInputMensaje), 2, driver);
	}
	
	@Override
	public void clickComprarFin(ChequeRegalo chequeRegalo) {
		click(By.xpath(XPathButtonComprar)).exec();
	}
	
	@Override
	public String getName() {
		return "Cheques regalo antiguo";
	}
 
}

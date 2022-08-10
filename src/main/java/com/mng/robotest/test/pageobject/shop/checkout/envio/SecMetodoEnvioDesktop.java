package com.mng.robotest.test.pageobject.shop.checkout.envio;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.pageobject.shop.checkout.envio.TipoTransporteEnum.TipoTransporte;


public class SecMetodoEnvioDesktop extends PageBase {
	

	private final ModalDroppoints modalDroppoints = new ModalDroppoints();
	
	private static final String XPATH_RADIO_INPUT = "//input[@id[contains(.,'Transportes')]]";
	private static final String XPATH_SELECT_FRANJA_HORARIA_METODO_URGENTE = "//select[@data-component-id='time-range-sameday_nextday_franjas']";
	
	private String getXPathBlockMetodo(TipoTransporte tipoTransporte) {
		return 
			"//div[(@class[contains(.,'bloqueMetodos')] or @class[contains(.,'metodoSelected')]) and " + 
			"@data-analytics-id='" + tipoTransporte.getIdAnalytics() + "']";
	}
	
	private String getXPathBlockMetodoSelected(TipoTransporte tipoTransporte) {
		String xpathBlockMethod = getXPathBlockMetodo(tipoTransporte);
		return (xpathBlockMethod + "//self::*[@class[contains(.,'metodoSelected')]]");
	}

	private String getXPathRadioMetodo(TipoTransporte tipoTransporte) {
		String xpath = getXPathBlockMetodo(tipoTransporte) + XPATH_RADIO_INPUT;
		
		//TODO for test intimissimi
		if (tipoTransporte==TipoTransporte.STANDARD) {
			return xpath + " | " + "//input[@id[contains(.,'Intimissimi')] and @value='Domicilio']";
		}
		return xpath;
	}
	
	public void selectMetodoIfNotSelected(TipoTransporte tipoTransporte) {
		int zeroSecondsToWait = 0;
		if (!isBlockSelectedUntil(tipoTransporte, zeroSecondsToWait)) {
			selectMetodo(tipoTransporte);
		}
	}

	public void selectMetodo(TipoTransporte tipoTransporte) {
		String xpathMethodRadio = getXPathRadioMetodo(tipoTransporte);
		if (state(Visible, By.xpath(xpathMethodRadio)).check() &&
			tipoTransporte != TipoTransporte.POSTNORD) {
			click(By.xpath(xpathMethodRadio)).waitLoadPage(5).exec();
		} else {
			String xpathBlock = getXPathBlockMetodo(tipoTransporte);
			click(By.xpath(xpathBlock)).waitLoadPage(5).exec();
		}
	}

	public boolean isPresentBlockMetodo(TipoTransporte tipoTransporte) {
		String xpathBLock = getXPathBlockMetodo(tipoTransporte);
		return (state(Present, By.xpath(xpathBLock)).check());
	}
	
	public boolean isBlockSelectedUntil(TipoTransporte tipoTransporte, int maxSeconds) {
		String xpathBlockSelected = getXPathBlockMetodoSelected(tipoTransporte);
		waitForPageLoaded(driver);
		return (state(Visible, By.xpath(xpathBlockSelected)).wait(maxSeconds).check());
	}
	
	public void selectFranjaHorariaUrgente(int posicion) {
		Select selectHorario = new Select(driver.findElement(By.xpath(XPATH_SELECT_FRANJA_HORARIA_METODO_URGENTE)));
		selectHorario.selectByIndex(posicion);
	}
}

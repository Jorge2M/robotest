package com.mng.robotest.domains.compra.pageobject.envio;

import org.openqa.selenium.support.ui.Select;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.compra.pageobject.envio.TipoTransporteEnum.TipoTransporte;
import com.mng.robotest.domains.transversal.PageBase;

public class SecMetodoEnvioDesktop extends PageBase {
	
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
		if (!isBlockSelectedUntil(tipoTransporte, 0)) {
			selectMetodo(tipoTransporte);
		}
	}

	public void selectMetodo(TipoTransporte tipoTransporte) {
		String xpathMethodRadio = getXPathRadioMetodo(tipoTransporte);
		if (state(Visible, xpathMethodRadio).check() &&
			tipoTransporte != TipoTransporte.POSTNORD) {
			click(xpathMethodRadio).waitLoadPage(5).exec();
		} else {
			String xpathBlock = getXPathBlockMetodo(tipoTransporte);
			click(xpathBlock).waitLoadPage(5).exec();
		}
	}

	public boolean isPresentBlockMetodo(TipoTransporte tipoTransporte) {
		String xpathBLock = getXPathBlockMetodo(tipoTransporte);
		return state(Present, xpathBLock).check();
	}
	
	public boolean isBlockSelectedUntil(TipoTransporte tipoTransporte, int seconds) {
		String xpathBlockSelected = getXPathBlockMetodoSelected(tipoTransporte);
		waitLoadPage();
		return state(Visible, xpathBlockSelected).wait(seconds).check();
	}
	
	public void selectFranjaHorariaUrgente(int posicion) {
		Select selectHorario = new Select(getElement(XPATH_SELECT_FRANJA_HORARIA_METODO_URGENTE));
		selectHorario.selectByIndex(posicion);
	}
}

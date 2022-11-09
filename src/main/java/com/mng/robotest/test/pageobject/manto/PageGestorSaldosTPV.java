package com.mng.robotest.test.pageobject.manto;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageGestorSaldosTPV extends PageBase {

	public static final String TITULO = "Gestor de Saldos de TPV";
	private static final String INI_XPATH_TITULO = "//td[@class='txt11B' and text()[contains(.,'";
	private static final String XPATH_INPUT_TPV = "//textarea[@id='form:tpvInput']";
	private static final String XPATH_CONSULTAR_SALDOS_BUTTON = "//input[@id='form:consultar']";
	private static final String XPATH_TABLA_SALDOS = "//span[text()='SALDOS']/ancestor::table";
	private static final String INI_XPATH_ID_TPV_TABLA_SALDOS = XPATH_TABLA_SALDOS + "//span[text()[contains(.,'SHOP TPV : ";
	private static final String XPATH_WIDGET_ERROR = "//div[@aria-describedby='error']";
	private static final String XPATH_LOAD_POPUP_IMAGE = "//img[@src='../images/loadingFonsNegre.gif']";

	public String getXPathTitulo(String title){
		return (INI_XPATH_TITULO + title + "')]]");
	}  
	
	public String getXPathIdTPVTablaSaldos(String tpv) {
		return INI_XPATH_ID_TPV_TABLA_SALDOS + tpv + "')] or text()[contains(.,'OUTLET TPV : " + tpv + "')]]";
	}
	
	public boolean isPage() {
		String xpath = getXPathTitulo(TITULO);
		return state(Present, xpath).check();
	}

	public boolean isVisibleTPVInput() {
		return state(Visible, XPATH_INPUT_TPV).check();
	}

	public void insertTPVAndClickConsultarSaldos(String tpv) {
		insertTPV(tpv);
		clickConsultarSaldos();
	}

	private void insertTPV(String tpv) {
		getElement(XPATH_INPUT_TPV).click();
		getElement(XPATH_INPUT_TPV).clear();
		getElement(XPATH_INPUT_TPV).sendKeys(tpv);
		
	}
	
	private void clickConsultarSaldos() {
		getElement(XPATH_CONSULTAR_SALDOS_BUTTON).click();
		state(Invisible, XPATH_LOAD_POPUP_IMAGE)
			.wait(120).check();
	}

	public boolean isTablaSaldosVisible() {
		waitLoadPage();
		return state(Visible, XPATH_TABLA_SALDOS).check();
	}

	public boolean isTPVIDVisible(String tpv) {
		String xpath = getXPathIdTPVTablaSaldos(tpv);
		return state(Visible, xpath).check();
	}

	public boolean isUnvalidTPVMessageVisible() {
		waitLoadPage();
		return state(Visible, XPATH_WIDGET_ERROR).check();
	}
	
}
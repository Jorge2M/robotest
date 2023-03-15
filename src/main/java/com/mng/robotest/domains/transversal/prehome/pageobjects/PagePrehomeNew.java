package com.mng.robotest.domains.transversal.prehome.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Invisible;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Present;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;

public class PagePrehomeNew extends PagePrehomeBase implements PagePrehomeI {

	private static final String XPATH_SELECTOR_PAISES = "//div[@class[contains(.,'CountrySelector__row')]]";
	private static final String XPATH_PAIS_SELECCIONADO = "//input[@data-testid='countrySelector.country.inputSearch.search']";
	private static final String XPATH_PAIS_OPTION = "//li[@data-testid[contains(.,'countrySelector.country.list.option')]]";
	private static final String XPATH_ICON_SALE_PAIS_SELECCIONADO = XPATH_PAIS_SELECCIONADO + "//span[@class[contains(.,'icon-outline-bag')]]";
	private static final String XPATH_SELECTOR_IDIOMAS = "//div[@id='countrySelector.languagues']/..";
	private static final String XPATH_IDIOMA_OPTION = XPATH_SELECTOR_IDIOMAS + "//div";
	private static final String XPATH_BUTTON_ACCEPT = "//form//button[@type='submit']"; //Necesitamos un data-testid (React)
	
	private String getXPathCountryItemFromCodigo(String codigoPrehome) {
		return XPATH_PAIS_OPTION + "//self::*[value='" + codigoPrehome + "']";
	}
	private String getXPathCountryItemFromName(String nameCountry) {
		return XPATH_PAIS_OPTION + "//self::*[text='" + nameCountry + "']";
	}
	private String getXPathIdiomaItemFromName(String nameIdioma) {
		return XPATH_IDIOMA_OPTION + "//self::*[@name='" + nameIdioma + "']";
	}
	
	@Override
	public boolean isPageUntil(int seconds) {
		return state(Present, XPATH_PAIS_SELECCIONADO).wait(seconds).check();
	}

	@Override
	boolean isNotPageUntil(int seconds) {
		return state(Invisible, XPATH_PAIS_SELECCIONADO).wait(seconds).check();
	}
	
	@Override
	public boolean isPage() {
		return isPageUntil(0);
	}
	
	@Override
	public boolean isPaisSelectedWithMarcaCompra() {
		return state(Visible, XPATH_ICON_SALE_PAIS_SELECCIONADO).check();
	}
	
	@Override
	public void selecionPais() {
		state(Present, XPATH_SELECTOR_PAISES).wait(5).check();
		setInitialModalsOff();
		if (!isPaisSelected()) {
			unfoldCountrys();
			inputAndSelectCountry();
		}
	}
	
	@Override
	public boolean isPaisSelected() {
		state(Visible, XPATH_PAIS_SELECCIONADO).wait(1).check();
		return getElement(XPATH_PAIS_SELECCIONADO).getAttribute("value")
				.contains(pais.getNombre_pais());
	}
	
	private void unfoldCountrys() {
		click(XPATH_SELECTOR_PAISES).exec();
	}
	
	private void inputAndSelectCountry() {
		String nameCountry = getNameCountry();
		getElement(XPATH_PAIS_SELECCIONADO).sendKeys(nameCountry);
		clickCountry(nameCountry);
	}

	private String getNameCountry() {
		String xpathCountryOption = getXPathCountryItemFromCodigo(pais.getCodigo_prehome());
		return getElement(xpathCountryOption).getText();
	}
	
	private void clickCountry(String nameCountry) {
		String xpathCountryOption = getXPathCountryItemFromName(nameCountry);
		click(xpathCountryOption).exec();
	}
	
	@Override
	void seleccionaIdioma(String nombrePais, String nombreIdioma) {
		unfoldIdiomas();
		String xpathButtonIdioma = getXPathIdiomaItemFromName(nombreIdioma);
		click(xpathButtonIdioma).exec();
	}
	
	@Override
	public void selecionIdiomaAndEnter() { 
		if (pais.getListIdiomas().size() > 1) {
			seleccionaIdioma(pais.getNombre_pais(), idioma.getCodigo().getLiteral());
		} 
		selectButtonForEnter();
		isNotPageUntil(30);
		waitLoadPage();
	}

	private void unfoldIdiomas() {
		click(XPATH_SELECTOR_IDIOMAS).exec();
	}
	
	@Override
	void selectButtonForEnter() {
		click(XPATH_BUTTON_ACCEPT).exec();
	}	
	
}
package com.mng.robotest.tests.domains.changecountry.pageobjects;

import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalChangeCountryGenesis extends ModalChangeCountry {
	
	private static final String XP_MODAL = 
			"//*[@data-testid[contains(.,'modalCountryLanguage')] or @data-testid[contains(.,'changeCountryLanguage')]]";
//	private static final String XP_MODAL = "//*[@data-testid[contains(.,'changeCountryLanguage')]]";
	private static final String XP_ASPA_CLOSE = "//*[@data-testid='modal.close.button']";
	private static final String XP_SELECTOR_PAISES = XP_MODAL + "//*[@id='countryAutocomplete']";
	private static final String XP_INPUT_COUNTRY = XP_SELECTOR_PAISES;
	private static final String XP_PAIS_OPTION = XP_MODAL + "//div[@class[contains(.,'Autocomplete_option')]]";
	private static final String XP_CONTINUE_BUTTON = XP_MODAL + "//button/span/..";
	
	private String getXPathIdiomButton(IdiomaPais idioma) {
		return "//a[@data-lang='" + idioma.getCodigo().name().toLowerCase() + "']";
	}
	
	private String getXPathCountryItemFromCodigo(String codigoPrehome) {
		return XP_PAIS_OPTION + "//self::*[@id='" + codigoPrehome + "']";
	}
	
	@Override
	public boolean isVisible(int seconds) {
		return state(VISIBLE, XP_MODAL).wait(seconds).check();
	}
	
	@Override
	public void change(Pais pais, IdiomaPais idioma) {
		unfoldCountrys();
		inputAndSelectCountry(pais);
		executeChange(pais, idioma);
	}
	
	@Override
	public void closeModalIfVisible() {
		if (isVisible(0)) {
			getElementVisible(XP_ASPA_CLOSE).click();
			state(INVISIBLE, XP_MODAL).wait(1).check();
		}
	}
	
	private void unfoldCountrys() {
		state(VISIBLE, XP_SELECTOR_PAISES).wait(1).check();
		click(XP_SELECTOR_PAISES).exec();
		waitMillis(1000);
	}
	
	private void inputAndSelectCountry(Pais pais) {
		String nameCountry = getNameCountry(pais);
		getElement(XP_INPUT_COUNTRY).clear();
		getElement(XP_INPUT_COUNTRY).sendKeys(nameCountry);
		waitMillis(500);
		clickCountry(pais.getCodigoPrehome());
	}	
	
	private String getNameCountry(Pais pais) {
		String xpathCountryOption = getXPathCountryItemFromCodigo(pais.getCodigoPrehome());
		state(VISIBLE, xpathCountryOption).wait(2).check();
		String innerHTML = getElement(xpathCountryOption).getAttribute("innerHTML");
		int indexLastSpan = innerHTML.lastIndexOf("</span>");
		return innerHTML.substring(indexLastSpan + "</span>".length()).trim();
	}
	
	private void clickCountry(String codigoPrehome) {
		String xpathCountryOption = getXPathCountryItemFromCodigo(codigoPrehome);
		click(xpathCountryOption).exec();
	}
	
	private void executeChange(Pais pais, IdiomaPais idioma) {
		waitMillis(500);
		if (pais.getListIdiomas().size() > 1) {
			String xpathIdiomButton = getXPathIdiomButton(idioma);
			click(getElementVisible(xpathIdiomButton)).exec();
		} else {
			click(getElementVisible(XP_CONTINUE_BUTTON)).exec();
		}
	}
	
}

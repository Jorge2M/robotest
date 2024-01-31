package com.mng.robotest.tests.domains.changecountry.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;

public class ModalChangeCountryOld extends ModalChangeCountry {
	
	private static final String XP_MODAL = "//div[(@id='cambioPais' or @id='seleccionPais') and @class='modalPopUp']"; 
	private static final String XP_ASPA_CLOSE = "//div[@data-ga-category='modal-deteccion-ip' and @class[contains(.,'closeModal')]]";
	private static final String XP_SELECTOR_PAISES = "//*[@id='countrySelect_chosen']";
	private static final String XP_INPUT_COUNTRY = "//div[@class='chosen-drop']//input";
	private static final String XP_PAIS_OPTION = "//select[@id='countrySelect']/option";
	private static final String XP_CONTINUE_BUTTON = "//a[@data-ga-category='modal-seleccion-pais']";
	
	private String getXPathIdiomButton(IdiomaPais idioma) {
		return "//a[@data-lang='" + idioma.getCodigo().name().toLowerCase() + "']";
	}
	
	private String getXPathCountryItemFromCodigo(String codigoPrehome) {
		return XP_PAIS_OPTION + "//self::*[@value='" + codigoPrehome + "']";
	}
	
	private String getXPathCountryItemFromName(String nameCountry) {
		return "//li[@data-option-array-index and text()='" + nameCountry + "']";
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
		click(XP_SELECTOR_PAISES).exec();
	}
	
	private void inputAndSelectCountry(Pais pais) {
		String nameCountry = getNameCountry(pais);
		getElement(XP_INPUT_COUNTRY).sendKeys(nameCountry);
		waitMillis(500);
		clickCountry(nameCountry);
	}	
	
	private String getNameCountry(Pais pais) {
		String xpathCountryOption = getXPathCountryItemFromCodigo(pais.getCodigoPrehome());
		return getElement(xpathCountryOption).getAttribute("innerHTML");
	}
	
	private void clickCountry(String nameCountry) {
		String xpathCountryOption = getXPathCountryItemFromName(nameCountry);
		click(xpathCountryOption).exec();
	}
	
	private void executeChange(Pais pais, IdiomaPais idioma) {
		if (pais.getListIdiomas().size() > 1) {
			String xpathIdiomButton = getXPathIdiomButton(idioma);
			click(getElementVisible(xpathIdiomButton)).exec();
		} else {
			click(getElementVisible(XP_CONTINUE_BUTTON)).exec();
		}
	}
	
}

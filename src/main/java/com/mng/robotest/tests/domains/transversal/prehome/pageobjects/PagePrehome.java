package com.mng.robotest.tests.domains.transversal.prehome.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Invisible;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Present;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.footer.pageobjects.PageFromFooter;
import com.mng.robotest.tests.domains.transversal.browser.LocalStorageMango;
import com.mng.robotest.tests.domains.transversal.cabecera.pageobjects.SecCabeceraMostFrequent;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.pageobject.shop.modales.ModalLoyaltyAfterAccess;

public class PagePrehome extends PageBase implements PageFromFooter {

	protected final Pais pais = dataTest.getPais();
	protected final IdiomaPais idioma = dataTest.getIdioma();	

	private static final String XPATH_SELECTOR_PAISES = "//*[@data-testid='countrySelector.country']";
	private static final String XPATH_PAIS_SELECCIONADO = "//input[@data-testid='countrySelector.country.inputSearch.search']";
	private static final String XPATH_PAIS_OPTION = "//li[@data-testid[contains(.,'countrySelector.country.list.option')]]";
	private static final String XPATH_ICON_SALE_PAIS_SELECCIONADO = XPATH_PAIS_SELECCIONADO + "//span[@class[contains(.,'icon-outline-bag')]]";
	private static final String XPATH_SELECTOR_IDIOMAS = "//*[@data-testid='countrySelector.languagues']/..";
	private static final String XPATH_IDIOMA_OPTION_DESKTOP = XPATH_SELECTOR_IDIOMAS + "//div";
	private static final String XPATH_IDIOMA_OPTION_MOBILE = XPATH_SELECTOR_IDIOMAS + "//option";
	private static final String XPATH_BUTTON_ACCEPT = "//form//button[@type='submit']"; //Necesitamos un data-testid (React)

	private String getXPathCountryItemFromCodigo(String codigoPrehome) {
		return XPATH_PAIS_OPTION + "//self::*[@value='" + codigoPrehome + "']";
	}
	private String getXPathCountryItemFromName(String nameCountry) {
		return XPATH_PAIS_OPTION + "//self::*[text()='" + nameCountry + "']";
	}
	private String getXPathIdiomaItemFromName(String nameIdioma) {
		if (channel==Channel.desktop) {
		    return XPATH_IDIOMA_OPTION_DESKTOP + "//self::*[@name='" + nameIdioma + "']";
		}
		return XPATH_IDIOMA_OPTION_MOBILE + "//self::*[text()='" + nameIdioma + "']";
	}	

	@Override
	public String getName() {
		return "Prehome";
	}
	
	@Override
	public boolean isPageCorrectUntil(int seconds) {
		return isPageUntil(seconds);
	}	
	
	public boolean isPageUntil(int seconds) {
		return state(Present, XPATH_PAIS_SELECCIONADO).wait(seconds).check();
	}

	boolean isNotPageUntil(int seconds) {
		return state(Invisible, XPATH_PAIS_SELECCIONADO).wait(seconds).check();
	}
	
	public boolean isPage() {
		return isPageUntil(0);
	}
	
	public boolean isPaisSelectedWithMarcaCompra() {
		return state(Visible, XPATH_ICON_SALE_PAIS_SELECCIONADO).check();
	}
	
	public void selecionPais() {
		state(Present, XPATH_SELECTOR_PAISES).wait(5).check();
		new LocalStorageMango().setInitialModalsOff();
		if (!isPaisSelected()) {
			unfoldCountrys();
			inputAndSelectCountry();
		}
	}
	
	public boolean isPaisSelected() {
		state(Visible, XPATH_PAIS_SELECCIONADO).wait(1).check();
		return getElement(XPATH_PAIS_SELECCIONADO).getAttribute("value")
				.contains(pais.getNombrePais());
	}
	
	private void unfoldCountrys() {
		click(XPATH_SELECTOR_PAISES).exec();
	}
	
	private void inputAndSelectCountry() {
		String nameCountry = getNameCountry();
		getElement(XPATH_PAIS_SELECCIONADO).sendKeys(nameCountry);
		waitMillis(500);
		clickCountry(nameCountry);
	}

	private String getNameCountry() {
		String xpathCountryOption = getXPathCountryItemFromCodigo(pais.getCodigoPrehome());
		return getElement(xpathCountryOption + "//p").getText();
	}
	
	private void clickCountry(String nameCountry) {
		String xpathCountryOption = getXPathCountryItemFromName(nameCountry);
		click(xpathCountryOption).exec();
	}
	
	void seleccionaIdioma(String nombreIdioma) {
		unfoldIdiomas();
		String xpathButtonIdioma = getXPathIdiomaItemFromName(nombreIdioma);
		if (!state(Visible, xpathButtonIdioma).wait(1).check()) {
			unfoldIdiomas();
		}
		click(xpathButtonIdioma).exec();
	}
	
	public void selecionIdiomaAndEnter() { 
		if (pais.getListIdiomas(app).size() > 1) {
			seleccionaIdioma(idioma.getCodigo().getLiteral());
		} 
		selectButtonForEnter();
		isNotPageUntil(30);
		waitLoadPage();
	}

	private void unfoldIdiomas() {
		state(Visible, XPATH_SELECTOR_IDIOMAS).wait(2).check();
		click(XPATH_SELECTOR_IDIOMAS).exec();
	}
	
	void selectButtonForEnter() {
		click(XPATH_BUTTON_ACCEPT).exec();
		if (!state(Invisible, XPATH_BUTTON_ACCEPT).wait(10).check()) {
			click(XPATH_BUTTON_ACCEPT).type(TypeClick.javascript).exec();
			if (!state(Invisible, XPATH_BUTTON_ACCEPT).wait(15).check()) {
				click(XPATH_BUTTON_ACCEPT).exec();
			}
		}
	}	
	
	public void accesoShopViaPrehome() {
		selecPaisIdiomaYAccede();
		new ModalLoyaltyAfterAccess().closeModalIfVisible();
		if (channel.isDevice()) {
			new SecCabeceraMostFrequent().closeSmartBannerIfExistsMobil();
		}
	}
	
	public void selecPaisIdiomaYAccede() {
		//TODO eliminar try-catch
		try {
			selecionPais();
			selecionIdiomaAndEnter();
		}
		catch (Exception e) {
			Log4jTM.getLogger().error("Problem accessing prehome. {}. {}", e.getClass().getName(), e.getMessage());
		}		
	}

}

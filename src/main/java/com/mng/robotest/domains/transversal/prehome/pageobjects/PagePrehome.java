package com.mng.robotest.domains.transversal.prehome.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Invisible;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Present;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;
import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.domains.footer.pageobjects.PageFromFooter;
import com.mng.robotest.domains.transversal.acceptcookies.pageobjects.SectionCookies;
import com.mng.robotest.domains.transversal.acceptcookies.steps.SectionCookiesSteps;
import com.mng.robotest.domains.transversal.cabecera.pageobjects.SecCabeceraMostFrequent;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.pageobject.shop.modales.ModalLoyaltyAfterAccess;
import com.mng.robotest.test.pageobject.utils.LocalStorage;
import com.mng.robotest.test.utils.testab.TestABactive;

import static com.mng.robotest.domains.transversal.acceptcookies.pageobjects.ModalSetCookies.SectionConfCookies.*;

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
		setInitialModalsOff();
		if (!isPaisSelected()) {
			unfoldCountrys();
			inputAndSelectCountry();
		}
	}
	
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
		waitMillis(500);
		clickCountry(nameCountry);
	}

	private String getNameCountry() {
		String xpathCountryOption = getXPathCountryItemFromCodigo(pais.getCodigo_prehome());
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
	
	public void previousAccessShopSteps() throws Exception {
		reloadIfServiceUnavailable();
		new PageJCAS().identJCASifExists();
		new TestABactive().currentTestABsToActivate();
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
	
	private void reloadIfServiceUnavailable() {
		if (driver.getPageSource().contains("Service Unavailable")) {
			driver.navigate().refresh();
		}
	}
	
	public void manageCookies(boolean acceptCookies) {
		var sectionCookiesSteps = new SectionCookiesSteps();
		if (acceptCookies) {
			if (new SectionCookies().isVisible(5)) {
				sectionCookiesSteps.accept();
			}
		} else {
			//Enable Only performance cookies for suport to TestABs
//			changeCookieOptanonConsent();
			enablePerformanceCookies();
		}
	}
	
//	private void changeCookieOptanonConsent() {
//		new SectionCookiesSteps().changeCookie_OptanonConsent();
//	}
	
	private void enablePerformanceCookies() {
		var modalSetCookiesSteps = new SectionCookiesSteps().setCookies();
		modalSetCookiesSteps.select(COOKIES_DE_RENDIMIENTO);
		modalSetCookiesSteps.enableSwitchCookies();
		modalSetCookiesSteps.saveConfiguration();
	}	
	
	protected void setInitialModalsOff() {
		//Damos de alta la cookie de newsLetter porque no podemos gestionar correctamente el cierre 
		//del modal en la página de portada (es aleatorio y aparece en un intervalo de 0 a 5 segundos)
		var localStorage = new LocalStorage(driver);
		localStorage.setItemInLocalStorage("modalRegistroNewsletter", "0");
		localStorage.setItemInLocalStorage("modalRegistroNewsletterImpacts", "0");
		localStorage.setItemInLocalStorage("modalAdhesionLoyalty", "true");
		localStorage.setItemInLocalStorage("modalSPShown", "1");
		localStorage.setItemInLocalStorage("MangoShopModalIPConfirmed", "ES-es__2");
	}

}

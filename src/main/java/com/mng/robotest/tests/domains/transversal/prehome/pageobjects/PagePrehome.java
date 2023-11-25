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
import com.mng.robotest.tests.domains.transversal.cabecera.pageobjects.SecCabecera;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;

public class PagePrehome extends PageBase implements PageFromFooter {

	protected final Pais pais = dataTest.getPais();
	protected final IdiomaPais idioma = dataTest.getIdioma();	

	private static final String XP_SELECTOR_PAISES = "//*[@data-testid='countrySelector.country']";
	private static final String XP_PAIS_SELECCIONADO = "//input[@data-testid='countrySelector.country.inputSearch.search']";
	private static final String XP_PAIS_OPTION = "//li[@data-testid[contains(.,'countrySelector.country.list.option')]]";
	private static final String XP_ICON_SALE_PAIS_SELECCIONADO = XP_PAIS_SELECCIONADO + "//span[@class[contains(.,'icon-outline-bag')]]";
	private static final String XP_SELECTOR_IDIOMAS = "//*[@data-testid='countrySelector.languagues']/..";
	private static final String XP_IDIOMA_OPTION_DESKTOP = XP_SELECTOR_IDIOMAS + "//div";
	private static final String XP_IDIOMA_OPTION_MOBILE = XP_SELECTOR_IDIOMAS + "//option";
	private static final String XP_BUTTON_ACCEPT = "//form//button[@type='submit']"; //Necesitamos un data-testid (React)

	private String getXPathCountryItemFromCodigo(String codigoPrehome) {
		return XP_PAIS_OPTION + "//self::*[@value='" + codigoPrehome + "']";
	}
	private String getXPathCountryItemFromName(String nameCountry) {
		return XP_PAIS_OPTION + "//self::*[text()='" + nameCountry + "']";
	}
	private String getXPathIdiomaItemFromName(String nameIdioma) {
		if (channel==Channel.desktop) {
		    return XP_IDIOMA_OPTION_DESKTOP + "//self::*[@name='" + nameIdioma + "']";
		}
		return XP_IDIOMA_OPTION_MOBILE + "//self::*[text()='" + nameIdioma + "']";
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
		return state(Present, XP_PAIS_SELECCIONADO).wait(seconds).check();
	}

	boolean isNotPageUntil(int seconds) {
		return state(Invisible, XP_PAIS_SELECCIONADO).wait(seconds).check();
	}
	
	public boolean isPage() {
		return isPageUntil(0);
	}
	
	public boolean isPaisSelectedWithMarcaCompra() {
		return state(Visible, XP_ICON_SALE_PAIS_SELECCIONADO).check();
	}
	
	public void selecionPais() {
		state(Present, XP_SELECTOR_PAISES).wait(5).check();
		new LocalStorageMango().setInitialModalsOff();
		if (!isPaisSelected()) {
			unfoldCountrys();
			inputAndSelectCountry();
		}
	}
	
	public boolean isPaisSelected() {
		state(Visible, XP_PAIS_SELECCIONADO).wait(1).check();
		return getElement(XP_PAIS_SELECCIONADO).getAttribute("value")
				.contains(pais.getNombrePais());
	}
	
	private void unfoldCountrys() {
		click(XP_SELECTOR_PAISES).exec();
	}
	
	private void inputAndSelectCountry() {
		String nameCountry = getNameCountry();
		getElement(XP_PAIS_SELECCIONADO).sendKeys(nameCountry);
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
		state(Visible, XP_SELECTOR_IDIOMAS).wait(2).check();
		click(XP_SELECTOR_IDIOMAS).exec();
	}
	
	void selectButtonForEnter() {
		click(XP_BUTTON_ACCEPT).exec();
		if (!state(Invisible, XP_BUTTON_ACCEPT).wait(10).check()) {
			click(XP_BUTTON_ACCEPT).type(TypeClick.javascript).exec();
			if (!state(Invisible, XP_BUTTON_ACCEPT).wait(15).check()) {
				click(XP_BUTTON_ACCEPT).exec();
			}
		}
	}	
	
	public void accesoShopViaPrehome() {
		selecPaisIdiomaYAccede();
//		new ModalLoyaltyAfterAccess().closeModalIfVisible();
		if (channel.isDevice()) {
			SecCabecera.make().closeSmartBannerIfExistsMobil();
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

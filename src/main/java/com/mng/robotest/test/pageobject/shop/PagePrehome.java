package com.mng.robotest.test.pageobject.shop;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;
import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.pageobject.shop.acceptcookies.SectionCookies;
import com.mng.robotest.test.pageobject.shop.acceptcookies.ModalSetCookies.SectionConfCookies;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabeceraOutlet_Mobil;
import com.mng.robotest.test.pageobject.shop.modales.ModalLoyaltyAfterAccess;
import com.mng.robotest.test.pageobject.utils.LocalStorage;
import com.mng.robotest.test.steps.shop.acceptcookies.ModalSetCookiesSteps;
import com.mng.robotest.test.steps.shop.acceptcookies.SectionCookiesSteps;
import com.mng.robotest.test.utils.testab.TestABactive;


public class PagePrehome extends PageBase {

	enum ButtonEnter { ENTER, CONTINUAR };
	
	private final Pais pais = dataTest.pais;
	private final IdiomaPais idioma = dataTest.idioma;
	
	private static final String XPATH_SELECT_PAISES = "//select[@id='countrySelect']";
	private static final String XPATH_DIV_PAIS_SELECCIONADO = "//div[@id='countrySelect_chosen']";
	private static final String XPATH_ICON_SALE_PAIS_SELECCIONADO = XPATH_DIV_PAIS_SELECCIONADO + "//span[@class[contains(.,'salesIcon')]]";
	private static final String XPATH_INPUT_PAIS = "//div[@class[contains(.,'chosen-search')]]/input";
	
	private String getXPathOptionPaisFromName(String nombrePais) {
		return XPATH_SELECT_PAISES + "//option[@data-alt-spellings[contains(.,'" + nombrePais + "')]]";
	}   
	
	private String getXPathButtonIdioma(String codigoPais, String nombreIdioma) {
		return "//div[@id='lang_" + codigoPais + "']//a[text()[contains(.,'" + nombreIdioma + "')]]";
	}
	
	private String getXPathButtonForEnter(ButtonEnter button, String codigoPais) {
		switch (button) {
		case ENTER:
			return ("//div[@id='lang_" + codigoPais + "']/div[@class[contains(.,'phFormEnter')]]");
		case CONTINUAR:
		default:
			return("//div[@id='lang_" + codigoPais + "']/div[@class[contains(.,'modalFormEnter')]]");
		}
	}

	public static boolean isPage(WebDriver driver) {
		return PageBase.state(Present, By.xpath(XPATH_DIV_PAIS_SELECCIONADO), driver).check();
	}
	
	public boolean isPage() {
		return isPage(driver);
	}

	public boolean isNotPageUntil(int maxSeconds) {
		return state(Invisible, XPATH_DIV_PAIS_SELECCIONADO).wait(maxSeconds).check();
	}

	public String getCodigoPais(String nombrePais) {
		String xpathOptionPais = getXPathOptionPaisFromName(nombrePais);
		String codigoPais = getElement(xpathOptionPais).getAttribute("value");
		return codigoPais;
	}

	public boolean isPaisSelectedWithMarcaCompra() {
		return state(Visible, XPATH_ICON_SALE_PAIS_SELECCIONADO).check();
	}

	public boolean isPaisSelectedDesktop() {
		String nombrePais = pais.getNombre_pais();
		return getElement(XPATH_DIV_PAIS_SELECCIONADO).getText().contains(nombrePais);
	}

	public void desplieguaListaPaises() {
		moveToElement(By.xpath(XPATH_DIV_PAIS_SELECCIONADO), driver);
		getElement(XPATH_DIV_PAIS_SELECCIONADO + "/a").click();
	}

	public void seleccionaIdioma(String nombrePais, String nombreIdioma) {
		String codigoPais = getCodigoPais(nombrePais);
		String xpathButtonIdioma = getXPathButtonIdioma(codigoPais, nombreIdioma);
		click(xpathButtonIdioma).type(TypeClick.javascript).exec();
	}

	public void inputPaisAndSelect(String nombrePais) throws Exception {
		String codigoPais = getCodigoPais(nombrePais);
		if (!channel.isDevice()) {
			new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class[contains(.,'chosen-with-drop')]]")));
			getElement(XPATH_INPUT_PAIS).sendKeys(nombrePais);
			
			// Seleccionamos el país encontrado
			getElement("//div[@class='chosen-drop']/ul/li").click();		
		} else {
			//En el caso de mobile no ejecutamos los despliegues porque es muy complejo tratar con los desplegables nativos del dispositivo
			//Seleccionamos el país a partir de su código de país
			getElement(XPATH_SELECT_PAISES + "/option[@value='" + codigoPais + "']").click();
		}
	}
	
	public void selectButtonForEnter(String codigoPais) {
		try {
			boolean buttonEnterSelected = clickButtonForEnterIfExists(ButtonEnter.ENTER, codigoPais); 
			if (!buttonEnterSelected) {
				clickButtonForEnterIfExists(ButtonEnter.CONTINUAR, codigoPais);
			}
		} 
		catch (Exception e) {
			Log4jTM.getLogger().warn("Exception clicking button for Enter. But perhaps the click have work fine", e);
		}
	}

	public boolean clickButtonForEnterIfExists(ButtonEnter buttonEnter, String codigoPais) {
		String xpathButton = getXPathButtonForEnter(buttonEnter, codigoPais);
		if (state(Present, xpathButton).check() && 
			getElement(xpathButton).isDisplayed()) {
			moveToElement(By.xpath(xpathButton), driver);
			click(xpathButton + "/a").type(TypeClick.javascript).exec();
			return true;
		}
		return false;
	}

	public void closeModalNewsLetterIfExists() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Object result = js.executeScript("return sessionObjectsJson");
		if (result!=null) {
			Map<String,Object> resultMap = (Map<String,Object>)result;

			//Si figura para lanzar la llamada JSON de NewsLetter
			if (resultMap.entrySet().toString().contains("modalRegistroNewsletter")) {
				String xpathDivModal = "//div[@id='modalNewsletter']";
				if (state(Visible, xpathDivModal).wait(5).check()) {
					//Clickamos al aspa para cerrar el modal
					getElement(xpathDivModal + "//div[@id='modalClose']").click();
				}
			}
		}
	}

	public void setInitialModalsOff() {
		LocalStorage localStorage = new LocalStorage(driver);
		localStorage.setItemInLocalStorage("modalRegistroNewsletter", "0");
		localStorage.setItemInLocalStorage("modalAdhesionLoyalty", "true");
	}

	public void accesoShopViaPrehome(boolean acceptCookies) throws Exception {
		previousAccessShopSteps(acceptCookies);
		selecPaisIdiomaYAccede();
		ModalLoyaltyAfterAccess.closeModalIfVisible(driver);
		if (channel.isDevice()) {
			SecCabeceraOutlet_Mobil secCabecera = new SecCabeceraOutlet_Mobil();
			secCabecera.closeSmartBannerIfExistsMobil();
		}
	}
	
	public void previousAccessShopSteps(boolean acceptCookies) throws Exception {
		reloadIfServiceUnavailable();
		new PageJCAS().identJCASifExists();
		TestABactive.currentTestABsToActivate(channel, app, driver);
		manageCookies(acceptCookies);
	}
	
	private void manageCookies(boolean acceptCookies) {
		SectionCookiesSteps sectionCookiesSteps = new SectionCookiesSteps();
		if (acceptCookies) {
			if (new SectionCookies().isVisible(2)) {
				sectionCookiesSteps.accept();
				//changeCookie_OptanonConsent();
				//setupCookies();
			}
		} else {
			ModalSetCookiesSteps modalSetCookiesSteps = sectionCookiesSteps.setCookies();
			modalSetCookiesSteps.saveConfiguration();
		}
	}
	
	private void reloadIfServiceUnavailable() {
		if (driver.getPageSource().contains("Service Unavailable")) {
			driver.navigate().refresh();
		}
	}
	
	private void changeCookie_OptanonConsent() {
		new SectionCookiesSteps().changeCookie_OptanonConsent();
	}
	
	private void setupCookies() {
		ModalSetCookiesSteps modalSetCookiesSteps = new SectionCookiesSteps().setCookies();
		modalSetCookiesSteps.select(SectionConfCookies.COOKIES_DIRIGIDAS);
		((JavascriptExecutor) driver).executeScript("document.getElementsByClassName('ot-tgl')[0].style.display='block'");	
		modalSetCookiesSteps.disableSwitchCookies();
		
		modalSetCookiesSteps.select(SectionConfCookies.COOKIES_DE_REDES_SOCIALES);
		modalSetCookiesSteps.disableSwitchCookies();
		
		modalSetCookiesSteps.select(SectionConfCookies.COOKIES_FUNCIONOALES);
		modalSetCookiesSteps.disableSwitchCookies();
		
		modalSetCookiesSteps.select(SectionConfCookies.COOKIES_DE_RENDIMIENTO);
		modalSetCookiesSteps.disableSwitchCookies();
		
		modalSetCookiesSteps.saveConfiguration();
	}
	
	public void selecPaisIdiomaYAccede() throws Exception {
		selecionPais();
		selecionIdiomaAndEnter();
	}
	
	public void selecionPais() throws Exception {
		new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_SELECT_PAISES)));
		
		//Damos de alta la cookie de newsLetter porque no podemos gestionar correctamente el cierre 
		//del modal en la página de portada (es aleatorio y aparece en un intervalo de 0 a 5 segundos)
		setInitialModalsOff();
		if (channel.isDevice() ||
			!isPaisSelectedDesktop()) {
			if (!channel.isDevice()) {
				//Nos posicionamos y desplegamos la lista de países (en el caso de mobile no desplegamos 
				//porque entonces es complejo manejar el desplegable que aparece en este tipo de dispositivos)
				desplieguaListaPaises();
			}
			
			inputPaisAndSelect(pais.getNombre_pais());
		}
	}
	
	public void selecionIdiomaAndEnter() throws Exception { 
		if (pais.getListIdiomas().size() > 1) {
			//Si el país tiene más de 1 idioma seleccionar el que nos llega como parámetro
			seleccionaIdioma(pais.getNombre_pais(), idioma.getCodigo().getLiteral());
		} else {
			String codigoPais = getCodigoPais(pais.getNombre_pais());
			selectButtonForEnter(codigoPais);
		}
	
		//Esperamos a que desaparezca la página de Prehome
		isNotPageUntil(30);
		waitForPageLoaded(driver);
	}
}

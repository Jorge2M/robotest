package com.mng.robotest.domains.transversal.prehome.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Invisible;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Present;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;

public class PagePrehomeOld extends PagePrehomeBase implements PagePrehomeI {

	enum ButtonEnter { ENTER, CONTINUAR }
	
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
	
	@Override
	public boolean isPageUntil(int seconds) {
		return state(Present, XPATH_DIV_PAIS_SELECCIONADO).wait(seconds).check();
	}

	@Override
	boolean isNotPageUntil(int seconds) {
		return state(Invisible, XPATH_DIV_PAIS_SELECCIONADO).wait(seconds).check();
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
	public boolean isPaisSelected() {
		String nombrePais = pais.getNombre_pais();
		return getElement(XPATH_DIV_PAIS_SELECCIONADO).getText().contains(nombrePais);
	}
	
	@Override
	public void selecionPais() {
		state(Present, XPATH_SELECT_PAISES).wait(5).check();
		setInitialModalsOff();
		if (channel.isDevice() ||
			!isPaisSelected()) {
			if (!channel.isDevice()) {
				//Nos posicionamos y desplegamos la lista de países (en el caso de mobile no desplegamos 
				//porque entonces es complejo manejar el desplegable que aparece en este tipo de dispositivos)
				desplieguaListaPaises();
			}
			
			inputPaisAndSelect(pais.getNombre_pais());
		}
	}
	
	private void desplieguaListaPaises() {
		moveToElement(XPATH_DIV_PAIS_SELECCIONADO);
		getElement(XPATH_DIV_PAIS_SELECCIONADO + "/a").click();
	}

	@Override
	void seleccionaIdioma(String nombrePais, String nombreIdioma) {
		String codigoPais = getCodigoPais(nombrePais);
		String xpathButtonIdioma = getXPathButtonIdioma(codigoPais, nombreIdioma);
		click(xpathButtonIdioma).type(TypeClick.javascript).exec();
	}

	private void inputPaisAndSelect(String nombrePais) {
		String codigoPais = getCodigoPais(nombrePais);
		if (!channel.isDevice()) {
			new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class[contains(.,'chosen-with-drop')]]")));
			getElement(XPATH_INPUT_PAIS).sendKeys(nombrePais);
			
			// Seleccionamos el país encontrado
			getElement("//div[@class='chosen-drop']/ul/li").click();		
		} else {
			//En el caso de mobile no ejecutamos los despliegues porque es muy complejo tratar con los desplegables nativos del dispositivo
			//Seleccionamos el país a partir de su código de país
			getElement(XPATH_SELECT_PAISES + "/option[@value='" + codigoPais + "']").click();
		}
	}
	
	@Override
	void selectButtonForEnter() {
		String codigoPais = getCodigoPais(pais.getNombre_pais());
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

	@Override
	public void selecionIdiomaAndEnter() { 
		if (pais.getListIdiomas().size() > 1) {
			seleccionaIdioma(pais.getNombre_pais(), idioma.getCodigo().getLiteral());
		} else {
			selectButtonForEnter();
		}
	
		isNotPageUntil(30);
		waitLoadPage();
	}
	
	private boolean clickButtonForEnterIfExists(ButtonEnter buttonEnter, String codigoPais) {
		String xpathButton = getXPathButtonForEnter(buttonEnter, codigoPais);
		if (state(Present, xpathButton).check() && 
			getElement(xpathButton).isDisplayed()) {
			moveToElement(xpathButton);
			click(xpathButton + "/a").type(TypeClick.javascript).exec();
			return true;
		}
		return false;
	}

	private String getCodigoPais(String nombrePais) {
		String xpathOptionPais = getXPathOptionPaisFromName(nombrePais);
		return getElement(xpathOptionPais).getAttribute("value");
	}

}

package com.mng.robotest.domains.ficha.pageobjects.tallas;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.test.data.Talla;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.JavascriptExecutor;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SSecSelTallasFichaDevice extends PageBase implements SSecSelTallasFicha {
	
	private static final String XPATH_SELECTOR_BUTTON = "//*[@data-testid='sizeSelectorButton']";
	private static final String XPATH_CAPA_TALLAS = "//div[@id='sizesContainerId']";
	private static final String XPATH_OPTION_TALLA = XPATH_CAPA_TALLAS + "//div[@data-testid[contains(.,'sizeSelector.size')]]";
	private static final String XPATH_TALLA_SELECTED = XPATH_SELECTOR_BUTTON + "//span[@class[contains(.,'size-text')]]";
	private static final String XPATH_OPTION_TALLA_UNICA = "//button[@id='productFormSelect']//span[@class='one-size-text']";
	private static final String XPATH_MSG_AVISO_TALLA = "//p[@class[contains(.,'sizes-notify-error')]]";
	
	public SSecSelTallasFichaDevice(Channel channel, AppEcom app) {
		super(channel, app);
	}
	
	private String getXPathOptionTallaDisponible() {
		return XPATH_OPTION_TALLA + "//self::*[@data-testid[contains(.,'size.available')]]";
	}
	private String getXPathOptionTallaNoDisponible() {
		return XPATH_OPTION_TALLA + "//self::*[@data-testid[contains(.,'size.unavailable')]]";
	}
	
	private String getXPathOptionTallaDisponible(String talla) {
		return getXPathOptionTallaDisponible() + "//self::*[text()[contains(.,'" + talla + "')]]";
	}
	
	String getXPathOptionTalla(Talla talla) {
		return getXPathOptionTalla(talla.getLabels()); 
	}
	
	private String getXPathOptionTalla(List<String> possibleLabels) {
		String coreXPath = possibleLabels.stream()
			.map(s -> 
				"text()='" + s + "' or " + 
				"starts-with(text(),'" + s + " " + "') or " +
				"starts-with(text(),'" + s + "cm" + "')")
			.collect(Collectors.joining(" or "));
		
		return XPATH_OPTION_TALLA + "//self::*[" + coreXPath + "]"; 
	}
	
	@Override
	public boolean isSectionUntil(int seconds) {
		for (int i=0; i<seconds; i++) {
			if (isVisibleSelectorTallasUntil(0)) {
				return true;
			}
			if (isVisibleSelectorButtonUntil(0)) {
				return true;
			}
			waitMillis(1000);
		}
		return false;
	}
	
	@Override
	public boolean isVisibleSelectorTallasUntil(int seconds) {
		return state(Visible, XPATH_CAPA_TALLAS).wait(seconds).check();
	}
	
	public boolean isVisibleSelectorButtonUntil(int seconds) {
		return state(Visible, XPATH_SELECTOR_BUTTON).wait(seconds).check();
	}
	
	@Override
	public int getNumOptionsTallas() {
		return getElements(XPATH_OPTION_TALLA).size();
	}
	
	@Override
	public int getNumOptionsTallasNoDisponibles() {
		return getElements(getXPathOptionTallaNoDisponible()).size();
	}
	
	@Override
	public boolean isTallaAvailable(String talla) {
		return state(Present, getXPathOptionTallaDisponible(talla)).check();
	}
	
	@Override
	public boolean isTallaUnica() {
		return state(Present, XPATH_OPTION_TALLA_UNICA).check();
	}
	
	@Override
	public boolean isVisibleListTallasForSelectUntil(int seconds) {
		return state(Visible, XPATH_OPTION_TALLA).wait(seconds).check();
	}
	
	private void despliegaSelectTallas() {
		if (isTallaUnica()) {
			return;
		}
		if (channel==Channel.tablet && app==AppEcom.votf) {
			despliegaSelectTallasTabletVotf();
		} else {
			despliegaSelectTallasExec();
		}
	}
	
	//Synchronized because is a error when unfold in many tablet-votf in parallel
	private synchronized void despliegaSelectTallasTabletVotf() {
		despliegaSelectTallasExec();
	}
	
	private void despliegaSelectTallasExec() {
		for (int i=0; i<3; i++) {
			state(Visible, XPATH_SELECTOR_BUTTON).wait(2).check();
			click(XPATH_SELECTOR_BUTTON).exec();
			if (isVisibleSelectorTallasUntil(1)) {
				break;
			}
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,+25)", "");
		}
	}

	@Override
	public void selectTallaByValue(String tallaNum) {
		if (isTallaUnica()) {
			return;
		}
		despliegaSelectTallas();
		Talla talla = Talla.fromValue(tallaNum);
		String xpathTalla = getXPathOptionTalla(talla);
		state(Clickable, xpathTalla).wait(2).check();
		click(xpathTalla).exec();
	}

	@Override
	public void selectTallaByLabel(String tallaLabel) {
		if (isTallaUnica()) {
			return;
		}
		despliegaSelectTallas();
		String xpathTalla = getXPathOptionTalla(Arrays.asList(tallaLabel));
		state(Clickable, xpathTalla).wait(2).check();
		click(xpathTalla).exec();
	}
	
	@Override
	public void selectTallaByIndex(int posicionEnDesplegable) {
		if (isTallaUnica()) {
			return;
		}
		despliegaSelectTallas();
		click("(" + XPATH_OPTION_TALLA + ")[" + posicionEnDesplegable + "]").exec();
	}

	@Override
	public void selectFirstTallaAvailable() {
		if (isTallaUnica()) {
			return;
		}
		despliegaSelectTallas();
		click(getXPathOptionTallaDisponible()).exec();
	}

	/**
	 * @return el literal visible de la talla seleccionada en el desplegable
	 */
	@Override
	public String getTallaAlfSelected(AppEcom app) {
		String tallaVisible = getElement(XPATH_TALLA_SELECTED).getText();
		tallaVisible = removeAlmacenFromTalla(tallaVisible);
		
		//Tratamos el caso relacionado con los entornos de test y eliminamos la parte a partir de " - " para contemplar casos como el de 'S - Delivery in 4-7 business day')
		if (tallaVisible.indexOf(" - ") >= 0) {
			tallaVisible = tallaVisible.substring(0, tallaVisible.indexOf(" - "));
		}
		
		return tallaVisible;
	}
	
	@Override
	public String getTallaAlf(int posicion) {
		String xpathTalla = "(" + XPATH_OPTION_TALLA + ")[" + posicion + "]";
		if (state(Present, xpathTalla).check()) {
			return getElement(xpathTalla).getText();
		}
		return "";
	}
	
	@Override
	public String getTallaCodNum(int posicion) {
		String xpathTalla = "(" + XPATH_OPTION_TALLA + ")[" + posicion + "]";
		if (state(Present, xpathTalla).check()) {
			return getElement(xpathTalla).getAttribute("value");
		}
		return "";
	}
	
	@Override
	public boolean isVisibleAvisoSeleccionTalla() {
		return state(Visible, XPATH_MSG_AVISO_TALLA).check();
	}	
}

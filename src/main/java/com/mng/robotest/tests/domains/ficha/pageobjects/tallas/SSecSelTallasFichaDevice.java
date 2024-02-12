package com.mng.robotest.tests.domains.ficha.pageobjects.tallas;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.testslegacy.data.PaisShop;
import com.mng.robotest.testslegacy.data.Talla;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SSecSelTallasFichaDevice extends PageBase implements SSecSelTallasFicha {
	
	private static final String XP_SELECTOR_BUTTON = "//*[@data-testid='sizeSelectorButton']";
	private static final String XP_CAPA_TALLAS = "//div[@id='sizesContainerId']";
	private static final String XP_OPTION_TALLA = XP_CAPA_TALLAS + "//div[@data-testid[contains(.,'sizeSelector.size')]]";
	private static final String XP_CAPA_TALLA_SELECTED = "(" + XP_SELECTOR_BUTTON + " | //*[@id='productFormSelect'])";
	private static final String XP_TALLA_SELECTED = XP_CAPA_TALLA_SELECTED + "//span[@class[contains(.,'size-text')]]";
	private static final String XP_OPTION_TALLA_UNICA = "//button[@id='productFormSelect']//span[@class='one-size-text']";
	private static final String XP_MSG_AVISO_TALLA = "//p[@class[contains(.,'sizes-notify-error')]]";
	private static final String XP_DIV_CLOSE = "//div[@data-testid='sheet.overlay' or @aria-label='close']";
	
	public SSecSelTallasFichaDevice(Channel channel, AppEcom app) {
		super(channel, app);
	}
	
	private String getXPathOptionTallaDisponible() {
		return XP_OPTION_TALLA + "//self::*[@data-testid[contains(.,'size.available')]]";
	}
	private String getXPathOptionTallaNoDisponible() {
		return XP_OPTION_TALLA + "//self::*[@data-testid[contains(.,'size.unavailable')]]";
	}
	
	private String getXPathOptionTallaDisponible(String talla) {
		return getXPathOptionTallaDisponible() + "//self::*[text()[contains(.,'" + talla + "')]]";
	}
	
	String getXPathOptionTalla(Talla talla) {
		return getXPathOptionTalla(talla, dataTest.getCodigoPais());
	}
	
	String getXPathOptionTalla(Talla talla, String codPais) {
		return getXPathOptionTalla(talla.getLabels(PaisShop.from(codPais)));
	}
	
	private String getXPathOptionTalla(List<String> possibleLabels) {
		String coreXPath = possibleLabels.stream()
			.map(s -> 
				"text()='" + s + "' or " + 
				"starts-with(text(),'" + s + " " + "') or " +
				"starts-with(text(),'" + s + "cm" + "')")
			.collect(Collectors.joining(" or "));
		
		return XP_OPTION_TALLA + "//self::*[" + coreXPath + "]"; 
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
		return state(VISIBLE, XP_CAPA_TALLAS).wait(seconds).check();
	}
	
	public boolean isVisibleSelectorButtonUntil(int seconds) {
		return state(VISIBLE, XP_SELECTOR_BUTTON).wait(seconds).check();
	}
	
	@Override
	public int getNumOptionsTallas() {
		return getElements(XP_OPTION_TALLA).size();
	}
	
	@Override
	public int getNumOptionsTallasNoDisponibles() {
		return getElements(getXPathOptionTallaNoDisponible()).size();
	}
	
	@Override
	public boolean isTallaAvailable(String talla) {
		return state(PRESENT, getXPathOptionTallaDisponible(talla)).check();
	}
	
	@Override
	public boolean isTallaUnica() {
		return state(PRESENT, XP_OPTION_TALLA_UNICA).check();
	}
	
	@Override
	public boolean isVisibleListTallasForSelectUntil(int seconds) {
		return state(VISIBLE, XP_OPTION_TALLA).wait(seconds).check();
	}
	
	@Override
	public void closeTallas() {
		click(XP_DIV_CLOSE).exec();
	}
	
	private void despliegaSelectTallas() {
		if (isTallaUnica()) {
			return;
		}
		if (isTablet() && isVotf()) {
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
			state(VISIBLE, XP_SELECTOR_BUTTON).wait(4).check();
			click(XP_SELECTOR_BUTTON).exec();
			if (isVisibleSelectorTallasUntil(1)) {
				break;
			}
			scrollVertical(+25);
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
		state(CLICKABLE, xpathTalla).wait(2).check();
		click(xpathTalla).exec();
	}

	@Override
	public void selectTallaByLabel(String tallaLabel) {
		if (isTallaUnica()) {
			return;
		}
		despliegaSelectTallas();
		String xpathTalla = getXPathOptionTalla(Arrays.asList(tallaLabel));
		state(CLICKABLE, xpathTalla).wait(2).check();
		click(xpathTalla).exec();
	}
	
	@Override
	public void selectTallaByIndex(int posicionEnDesplegable) {
		if (isTallaUnica()) {
			return;
		}
		despliegaSelectTallas();
		click("(" + XP_OPTION_TALLA + ")[" + posicionEnDesplegable + "]").exec();
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
		String tallaVisible = getElement(XP_TALLA_SELECTED).getText();
		tallaVisible = removeAlmacenFromTalla(tallaVisible);
		
		//Tratamos el caso relacionado con los entornos de test y eliminamos la parte a partir de " - " para contemplar casos como el de 'S - Delivery in 4-7 business day')
		if (tallaVisible.indexOf(" - ") >= 0) {
			tallaVisible = tallaVisible.substring(0, tallaVisible.indexOf(" - "));
		}
		
		return tallaVisible;
	}
	
	@Override
	public String getTallaAlf(int posicion) {
		String xpathTalla = "(" + XP_OPTION_TALLA + ")[" + posicion + "]";
		if (state(PRESENT, xpathTalla).check()) {
			return getElement(xpathTalla).getText();
		}
		return "";
	}
	
	@Override
	public String getTallaCodNum(int posicion) {
		String xpathTalla = "(" + XP_OPTION_TALLA + ")[" + posicion + "]";
		if (state(PRESENT, xpathTalla).check()) {
			return getElement(xpathTalla).getAttribute("value");
		}
		return "";
	}
	
	@Override
	public boolean isVisibleAvisoSeleccionTalla() {
		return state(VISIBLE, XP_MSG_AVISO_TALLA).check();
	}	
}

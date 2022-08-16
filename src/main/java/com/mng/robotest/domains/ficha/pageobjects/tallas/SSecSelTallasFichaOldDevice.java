package com.mng.robotest.domains.ficha.pageobjects.tallas;



import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.domains.transversal.PageBase;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.data.Talla;


public class SSecSelTallasFichaOldDevice extends PageBase implements SSecSelTallasFicha {
	
	private static final String XPATH_SELECTOR_BUTTON = "//*[@data-testid='sizeSelectorButton']";
	private static final String XPATH_CAPA_TALLAS = "//div[@id='sizesContainerId']";
	private static final String XPATH_OPTION_TALLA = XPATH_CAPA_TALLAS + "//span[@class='size-text']";
	private static final String XPATH_TALLA_SELECTED = XPATH_SELECTOR_BUTTON + "//span[@class[contains(.,'size-text')]]";
	private static final String XPATH_OPTION_TALLA_UNICA = "//button[@id='productFormSelect']//span[@class='one-size-text']";
	
	public SSecSelTallasFichaOldDevice(Channel channel, AppEcom app) {
		super(channel, app);
	}
	
	private String getXPathOptionTallaSegunDisponible(boolean disponible) {
		String symbol = (disponible) ? "<" : ">";
		return (XPATH_OPTION_TALLA + "//self::*[string-length(normalize-space(text()))" + symbol + "20]");
	}
	
	private String getXPathOptionTallaSegunDisponible(boolean disponible, String talla) {
		String xpathOption = getXPathOptionTallaSegunDisponible(disponible);
		return (xpathOption + "//self::*[text()[contains(.,'" + talla + "')]]");
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
	public boolean isVisibleSelectorTallasUntil(int maxSeconds) {
		return (state(Visible, XPATH_CAPA_TALLAS).wait(maxSeconds).check());
	}
	
	@Override
	public int getNumOptionsTallas() {
		return getElements(XPATH_OPTION_TALLA).size();
	}
	
	@Override
	public int getNumOptionsTallasNoDisponibles() {
		String xpathOptions = getXPathOptionTallaSegunDisponible(false);
		return getElements(xpathOptions).size();
	}
	
	@Override
	public boolean isTallaAvailable(String talla) {
		String xpathTalla = getXPathOptionTallaSegunDisponible(true, talla);
		return state(Present, xpathTalla).check();
	}
	
	@Override
	public boolean isTallaUnica() {
		return state(Present, XPATH_OPTION_TALLA_UNICA).check();
	}
	
	@Override
	public boolean isVisibleListTallasForSelectUntil(int maxSeconds) {
		return state(Visible, XPATH_OPTION_TALLA).wait(maxSeconds).check();
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
		state(State.Clickable, xpathTalla).wait(2).check();
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
		String xpathTallaAvailable = getXPathOptionTallaSegunDisponible(true);
		click(xpathTallaAvailable).exec();
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
}

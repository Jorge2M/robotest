package com.mng.robotest.domains.ficha.pageobjects.tallas;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;

public class SSecSelTallasFichaDesktop extends PageBase implements SSecSelTallasFicha {
	
	private static final String XPATH_CAPA_TALLAS = "//div[@id='sizesContainer']"; 
	private static final String XPATH_SELECTOR_TALLAS = XPATH_CAPA_TALLAS + "//ul";
	private static final String XPATH_LIST_TALLAS_FOR_SELECT = XPATH_CAPA_TALLAS + "//ul";	
	private static final String XPATH_TALLA_ITEM = XPATH_CAPA_TALLAS + "//span[@data-available]";
	private static final String XPATH_TALLA_AVAILABLE = XPATH_TALLA_ITEM + "//self::*[@data-available='true']";
	private static final String XPATH_TALLA_UNAVAILABLE = XPATH_TALLA_ITEM + "//self::*[@data-available='false']";
	private static final String XPATH_ICON_DESPLEGABLE_TALLAS = "//i[@class[contains(.,'icon-outline-down')]]";
	private static final String XPATH_TALLA_SELECTED = XPATH_ICON_DESPLEGABLE_TALLAS + "/../span";
	private static final String XPATH_MSG_AVISO_TALLA = XPATH_CAPA_TALLAS + "//span";
	
	private String getXPathTallaByCodigo(String codigoNumericoTalla) {
		int numTalla = Integer.parseInt(codigoNumericoTalla);
		return (XPATH_TALLA_ITEM + "//self::span[@id='size-" + codigoNumericoTalla + "' or @id='size-" + numTalla + "']"); 
	}
	
	private String getXPathTallaByLabel(String labelTalla) {
		return (XPATH_TALLA_ITEM + "//self::span[text()='" + labelTalla + "']"); 
	}
	
	private String getXPathTallaAvailable(String talla) {
		return (XPATH_TALLA_AVAILABLE + "//::*[text()[contains(.,'" + talla + "')]]");
	}

	@Override
	public boolean isSectionUntil(int seconds) {
		return isVisibleSelectorTallasUntil(seconds);
	}
	
	@Override
	public boolean isVisibleSelectorTallasUntil(int seconds) {
		return (state(Visible, XPATH_SELECTOR_TALLAS).wait(seconds).check());
	}
	
	@Override
	public String getTallaAlfSelected(AppEcom app) {
		if (state(Present, XPATH_TALLA_SELECTED).check()) {
			if (isTallaUnica()) {
			   return "unitalla";
			}
			
			String textTalla = getElement(XPATH_TALLA_SELECTED).getText();
			textTalla = removeAlmacenFromTalla(textTalla);
			return textTalla;
		}
		
		return "";
	}
	
	@Override
	public String getTallaAlf(int posicion) {
		String xpathTalla = "(" + XPATH_TALLA_ITEM + ")[" + posicion + "]";
		if (state(Present, xpathTalla).check()) {
			return getElement(xpathTalla).getAttribute("innerHTML");
		}
		return "";
	}
	
	@Override
	public String getTallaCodNum(int posicion) {
		String xpathTalla = "(" + XPATH_TALLA_ITEM + ")[" + posicion + "]";
		if (state(Present, xpathTalla).check()) {
			return getElement(xpathTalla).getAttribute("data-value");
		}
		return "";
	}

	@Override
	public boolean isTallaUnica() {
		return 
			!state(Visible, XPATH_ICON_DESPLEGABLE_TALLAS).check() &&
			getElements(XPATH_TALLA_ITEM).size()==1;
	}
	
	@Override
	public boolean isVisibleListTallasForSelectUntil(int seconds) {
		return (state(Visible, XPATH_LIST_TALLAS_FOR_SELECT).wait(seconds).check());
	}
	
	@Override
	public void selectTallaByValue(String codigoNumericoTalla) {
		unfoldTallas();
		String xpathTalla = getXPathTallaByCodigo(codigoNumericoTalla);
		if (state(Present, xpathTalla).check()) {
			click(xpathTalla).type(TypeClick.javascript).exec();
		}
	}
	
	@Override
	public void selectTallaByLabel(String tallaLabel) {
		unfoldTallas();
		String xpathTalla = getXPathTallaByLabel(tallaLabel);
		if (state(Clickable, xpathTalla).check()) {
			getElement(xpathTalla).click();
		}
	}
	
	@Override
	public void selectTallaByIndex(int posicion) {
		unfoldTallas();
		String xpathTallaByPos = "(" + XPATH_TALLA_ITEM + ")[" + posicion + "]";
		if (state(Clickable, xpathTallaByPos).check()) {
			getElement(xpathTallaByPos).click();
		}
	}
	
	@Override
	public void selectFirstTallaAvailable() {
		unfoldTallas();
		if (state(Clickable, XPATH_TALLA_AVAILABLE).check()) {
			getElement(XPATH_TALLA_AVAILABLE).click();
		}
	}
	
	private void unfoldTallas() {
		if (state(Visible, XPATH_ICON_DESPLEGABLE_TALLAS).check()) {
			click(XPATH_ICON_DESPLEGABLE_TALLAS).exec();
		}
	}
	
	@Override
	public int getNumOptionsTallasNoDisponibles() {
		return getElements(XPATH_TALLA_UNAVAILABLE).size();
	}	
	
	@Override
	public boolean isTallaAvailable(String talla) {
		String xpathTalla = getXPathTallaAvailable(talla);
		return state(Present, xpathTalla).check();
	}
	
	@Override
	public int getNumOptionsTallas() {
		return getElements(XPATH_TALLA_ITEM).size();
	}	
	
	@Override
	public boolean isVisibleAvisoSeleccionTalla() {
		return state(Visible, XPATH_MSG_AVISO_TALLA).check();
	}
}

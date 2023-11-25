package com.mng.robotest.tests.domains.ficha.pageobjects.tallas;

import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;

public class SSecSelTallasFichaDesktop extends PageBase implements SSecSelTallasFicha {
	
	private static final String XP_CAPA_TALLAS = "//div[@id='sizesContainer']"; 
	private static final String XP_SELECTOR_TALLAS = XP_CAPA_TALLAS + "//ul";
	private static final String XP_LIST_TALLAS_FOR_SELECT = XP_CAPA_TALLAS + "//ul";	
	private static final String XP_TALLA_ITEM = XP_CAPA_TALLAS + "//*[@data-testid[contains(.,'sizeSelector.size')]]";
	private static final String XP_TALLA_AVAILABLE = XP_TALLA_ITEM + "//self::*[@data-testid[contains(.,'.available')]]";
	private static final String XP_TALLA_UNAVAILABLE = XP_TALLA_ITEM + "//self::*[@data-testid[contains(.,'.unavailable')]]";
	private static final String XP_ICON_DESPLEGABLE_TALLAS = "//i[@class[contains(.,'icon-outline-down')]]";
	private static final String XP_TALLA_SELECTED = XP_ICON_DESPLEGABLE_TALLAS + "/../span";
	private static final String XP_MSG_AVISO_TALLA = XP_CAPA_TALLAS + "//span";
	
	private String getXPathTallaByCodigo(String codigoNumericoTalla) {
		int numTalla = Integer.parseInt(codigoNumericoTalla);
		return (XP_TALLA_ITEM + "//self::span[@id='size-" + codigoNumericoTalla + "' or @id='size-" + numTalla + "']"); 
	}
	
	private String getXPathTallaByLabel(String labelTalla) {
		return (XP_TALLA_ITEM + "//self::span[text()='" + labelTalla + "']"); 
	}
	
	private String getXPathTallaAvailable(String talla) {
		return (XP_TALLA_AVAILABLE + "//::*[text()[contains(.,'" + talla + "')]]");
	}

	@Override
	public boolean isSectionUntil(int seconds) {
		return isVisibleSelectorTallasUntil(seconds);
	}
	
	@Override
	public boolean isVisibleSelectorTallasUntil(int seconds) {
		return (state(Visible, XP_SELECTOR_TALLAS).wait(seconds).check());
	}
	
	@Override
	public String getTallaAlfSelected(AppEcom app) {
		if (state(Present, XP_TALLA_SELECTED).check()) {
			if (isTallaUnica()) {
			   return "unitalla";
			}
			
			String textTalla = getElement(XP_TALLA_SELECTED).getText();
			textTalla = removeAlmacenFromTalla(textTalla);
			return textTalla;
		}
		
		return "";
	}
	
	@Override
	public String getTallaAlf(int posicion) {
		String xpathTalla = "(" + XP_TALLA_ITEM + ")[" + posicion + "]";
		if (state(Present, xpathTalla).check()) {
			return getElement(xpathTalla).getAttribute("innerHTML");
		}
		return "";
	}
	
	@Override
	public String getTallaCodNum(int posicion) {
		String xpathTalla = "(" + XP_TALLA_ITEM + ")[" + posicion + "]";
		if (state(Present, xpathTalla).check()) {
			return getElement(xpathTalla).getAttribute("data-value");
		}
		return "";
	}

	@Override
	public boolean isTallaUnica() {
		return 
			!state(Visible, XP_ICON_DESPLEGABLE_TALLAS).check() &&
			getElements(XP_TALLA_ITEM).size()==1;
	}
	
	@Override
	public boolean isVisibleListTallasForSelectUntil(int seconds) {
		return (state(Visible, XP_LIST_TALLAS_FOR_SELECT).wait(seconds).check());
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
		String xpathTallaByPos = "(" + XP_TALLA_ITEM + ")[" + posicion + "]";
		if (state(Clickable, xpathTallaByPos).check()) {
			getElement(xpathTallaByPos).click();
		}
	}
	
	@Override
	public void selectFirstTallaAvailable() {
		unfoldTallas();
		if (state(Clickable, XP_TALLA_AVAILABLE).check()) {
			getElement(XP_TALLA_AVAILABLE).click();
		}
	}
	
	private void unfoldTallas() {
		if (state(Visible, XP_ICON_DESPLEGABLE_TALLAS).check()) {
			click(XP_ICON_DESPLEGABLE_TALLAS).exec();
		}
	}
	
	@Override
	public int getNumOptionsTallasNoDisponibles() {
		return getElements(XP_TALLA_UNAVAILABLE).size();
	}	
	
	@Override
	public boolean isTallaAvailable(String talla) {
		String xpathTalla = getXPathTallaAvailable(talla);
		return state(Present, xpathTalla).check();
	}
	
	@Override
	public int getNumOptionsTallas() {
		return getElements(XP_TALLA_ITEM).size();
	}	
	
	@Override
	public boolean isVisibleAvisoSeleccionTalla() {
		return state(Visible, XP_MSG_AVISO_TALLA).check();
	}
	
	@Override
	public void closeTallas() {
		selectFirstTallaAvailable();
	}
}

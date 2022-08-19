package com.mng.robotest.domains.ficha.pageobjects.tallas;



import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.conftestmaker.AppEcom;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SSecSelTallasFichaNew extends PageBase implements SSecSelTallasFicha {
	
	private static final String XPATH_CAPA_TALLAS = "//form/div[@class='sizes']";
	private static final String XPATH_SELECTOR_TALLAS = XPATH_CAPA_TALLAS + "/div[@class='selector']";
	private static final String XPATH_LIST_TALLAS_FOR_SELECT = XPATH_SELECTOR_TALLAS + "//div[@class[contains(.,'selector-list')]]";
	private static final String XPATH_TALLA_ITEM = XPATH_CAPA_TALLAS + "//span[(@role='option' or @role='button') and not(@data-available='false')]";
	private static final String XPATH_TALLA_AVAILABLE = XPATH_TALLA_ITEM + "//self::span[@data-available='true' or @class='single-size']";
	private static final String XPATH_TALLA_UNAVAILABLE = XPATH_TALLA_ITEM + "//self::span[not(@data-available) and not(@class='single-size')]";
	private static final String XPATH_TALLA_SELECTED = XPATH_TALLA_ITEM + "//self::span[@class[contains(.,'selector-trigger')] or @class='single-size']";
	private static final String XPATH_TALLA_UNICA = XPATH_TALLA_ITEM + "//self::span[@class[contains(.,'single-size')]]";
	
	private String getXPathTallaByCodigo(String codigoNumericoTalla) {
		int numTalla = Integer.valueOf(codigoNumericoTalla);
		return (XPATH_TALLA_ITEM + "//self::span[@data-value='" + codigoNumericoTalla + "' or @data-value='" + numTalla + "']"); 
	}
	
	private String getXPathTallaByLabel(String labelTalla) {
		return (XPATH_TALLA_ITEM + "//self::span[text()='" + labelTalla + "']"); 
	}
	
	private String getXPathTallaAvailable(String talla) {
		return (XPATH_TALLA_AVAILABLE + "//::*[text()[contains(.,'" + talla + "')]]");
	}

	@Override
	public boolean isSectionUntil(int maxSeconds) {
		return isVisibleSelectorTallasUntil(maxSeconds);
	}
	
	@Override
	public boolean isVisibleSelectorTallasUntil(int maxSeconds) {
		return (state(Visible, XPATH_SELECTOR_TALLAS).wait(maxSeconds).check());
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
		return state(Present, XPATH_TALLA_UNICA).check();
	}
	
	private boolean unfoldListTallasIfNotYet() {
		if (!state(Visible, XPATH_LIST_TALLAS_FOR_SELECT).check()) {
			//En el caso de talla única no existe XPathSelectorTallas
			if (state(Visible, XPATH_SELECTOR_TALLAS).check()) {
				getElement(XPATH_SELECTOR_TALLAS).click();
			} else {
				return true;
			}
			return (isVisibleListTallasForSelectUntil(1));
		}
		
		return true;
	}
	
	@Override
	public boolean isVisibleListTallasForSelectUntil(int maxSeconds) {
		return (state(Visible, XPATH_LIST_TALLAS_FOR_SELECT).wait(maxSeconds).check());
	}
	
	@Override
	public void selectTallaByValue(String codigoNumericoTalla) {
		unfoldListTallasIfNotYet();
		String xpathTalla = getXPathTallaByCodigo(codigoNumericoTalla);
		if (state(Clickable, xpathTalla).check()) {
			getElement(xpathTalla).click();
		}
	}
	
	@Override
	public void selectTallaByLabel(String tallaLabel) {
		unfoldListTallasIfNotYet();
		String xpathTalla = getXPathTallaByLabel(tallaLabel);
		if (state(Clickable, xpathTalla).check()) {
			getElement(xpathTalla).click();
		}
	}
	
	@Override
	public void selectTallaByIndex(int posicion) {
		unfoldListTallasIfNotYet();
		String xpathTallaByPos = "(" + XPATH_TALLA_ITEM + ")[" + posicion + "]";
		if (state(Clickable, xpathTallaByPos).check()) {
			getElement(xpathTallaByPos).click();
		}
	}
	
	@Override
	public void selectFirstTallaAvailable() {
		unfoldListTallasIfNotYet();
		if (state(Clickable, XPATH_TALLA_AVAILABLE).check()) {
			getElement(XPATH_TALLA_AVAILABLE).click();
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
}

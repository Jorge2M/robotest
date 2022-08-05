package com.mng.robotest.domains.ficha.pageobjects.tallas;

import org.openqa.selenium.By;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.conftestmaker.AppEcom;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SSecSelTallasFichaNew extends PageObjTM implements SSecSelTallasFicha {
	
	private static final String XPathCapaTallas = "//form/div[@class='sizes']";
	private static final String XPathSelectorTallas = XPathCapaTallas + "/div[@class='selector']";
	private static final String XPathListTallsForSelect = XPathSelectorTallas + "//div[@class[contains(.,'selector-list')]]";
	private static final String XPathTallaItem = XPathCapaTallas + "//span[(@role='option' or @role='button') and not(@data-available='false')]";
	private static final String XPathTallaAvailable = XPathTallaItem + "//self::span[@data-available='true' or @class='single-size']";
	private static final String XPathTallaUnavailable = XPathTallaItem + "//self::span[not(@data-available) and not(@class='single-size')]";
	private static final String XPathTallaSelected = XPathTallaItem + "//self::span[@class[contains(.,'selector-trigger')] or @class='single-size']";
	private static final String XPathTallaUnica = XPathTallaItem + "//self::span[@class[contains(.,'single-size')]]";
	
	private String getXPathTallaByCodigo(String codigoNumericoTalla) {
		int numTalla = Integer.valueOf(codigoNumericoTalla);
		return (XPathTallaItem + "//self::span[@data-value='" + codigoNumericoTalla + "' or @data-value='" + numTalla + "']"); 
	}
	
	private String getXPathTallaByLabel(String labelTalla) {
		return (XPathTallaItem + "//self::span[text()='" + labelTalla + "']"); 
	}
	
	private String getXPathTallaAvailable(String talla) {
		return (XPathTallaAvailable + "//::*[text()[contains(.,'" + talla + "')]]");
	}

	@Override
	public boolean isVisibleSelectorTallasUntil(int maxSeconds) {
		return (state(Visible, By.xpath(XPathSelectorTallas)).wait(maxSeconds).check());
	}
	
	@Override
	public String getTallaAlfSelected(AppEcom app) {
		if (state(Present, By.xpath(XPathTallaSelected)).check()) {
			if (isTallaUnica()) {
			   return "unitalla";
			}
			
			String textTalla = driver.findElement(By.xpath(XPathTallaSelected)).getText();
			textTalla = removeAlmacenFromTalla(textTalla);
			return textTalla;
		}
		
		return "";
	}
	
	@Override
	public String getTallaAlf(int posicion) {
		String xpathTalla = "(" + XPathTallaItem + ")[" + posicion + "]";
		if (state(Present, By.xpath(xpathTalla)).check()) {
			return (driver.findElement(By.xpath(xpathTalla)).getAttribute("innerHTML"));
		}
		return "";
	}
	
	@Override
	public String getTallaCodNum(int posicion) {
		String xpathTalla = "(" + XPathTallaItem + ")[" + posicion + "]";
		if (state(Present, By.xpath(xpathTalla)).check()) {
			return (driver.findElement(By.xpath(xpathTalla)).getAttribute("data-value"));
		}
		return "";
	}

	@Override
	public boolean isTallaUnica() {
		return (state(Present, By.xpath(XPathTallaUnica)).check());
	}
	
	private boolean unfoldListTallasIfNotYet() {
		if (!state(Visible, By.xpath(XPathListTallsForSelect)).check()) {
			//En el caso de talla única no existe XPathSelectorTallas
			if (state(Visible, By.xpath(XPathSelectorTallas)).check()) {
				driver.findElement(By.xpath(XPathSelectorTallas)).click();
			} else {
				return true;
			}
			return (isVisibleListTallasForSelectUntil(1));
		}
		
		return true;
	}
	
	@Override
	public boolean isVisibleListTallasForSelectUntil(int maxSeconds) {
		return (state(Visible, By.xpath(XPathListTallsForSelect)).wait(maxSeconds).check());
	}
	
	@Override
	public void selectTallaByValue(String codigoNumericoTalla) {
		unfoldListTallasIfNotYet();
		String xpathTalla = getXPathTallaByCodigo(codigoNumericoTalla);
		if (state(Clickable, By.xpath(xpathTalla)).check()) {
			driver.findElement(By.xpath(xpathTalla)).click();
		}
	}
	
	@Override
	public void selectTallaByLabel(String tallaLabel) {
		unfoldListTallasIfNotYet();
		String xpathTalla = getXPathTallaByLabel(tallaLabel);
		if (state(Clickable, By.xpath(xpathTalla)).check()) {
			driver.findElement(By.xpath(xpathTalla)).click();
		}
	}
	
	@Override
	public void selectTallaByIndex(int posicion) {
		unfoldListTallasIfNotYet();
		String xpathTallaByPos = "(" + XPathTallaItem + ")[" + posicion + "]";
		if (state(Clickable, By.xpath(xpathTallaByPos)).check()) {
			driver.findElement(By.xpath(xpathTallaByPos)).click();
		}
	}
	
	@Override
	public void selectFirstTallaAvailable() {
		unfoldListTallasIfNotYet();
		if (state(Clickable, By.xpath(XPathTallaAvailable)).check()) {
			driver.findElement(By.xpath(XPathTallaAvailable)).click();
		}
	}
	
	@Override
	public int getNumOptionsTallasNoDisponibles() {
		return (driver.findElements(By.xpath(XPathTallaUnavailable)).size());
	}	
	
	@Override
	public boolean isTallaAvailable(String talla) {
		String xpathTalla = getXPathTallaAvailable(talla);
		return (state(Present, By.xpath(xpathTalla)).check());
	}
	
	@Override
	public int getNumOptionsTallas() {
		return (driver.findElements(By.xpath(XPathTallaItem)).size());
	}	
}

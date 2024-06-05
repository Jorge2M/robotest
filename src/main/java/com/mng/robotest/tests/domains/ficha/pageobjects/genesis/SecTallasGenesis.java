package com.mng.robotest.tests.domains.ficha.pageobjects.genesis;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import org.openqa.selenium.By;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.testslegacy.data.PaisShop;
import com.mng.robotest.testslegacy.data.Talla;

public class SecTallasGenesis extends PageBase {

	//TODO pendiente que den de alta el data-testid
	private static final String XP_LINK_GUIA_TALLAS = "//button[@class[contains(.,'sizeGuide')]]";
	
	private static final String XP_MSG_AVISO_SELECT_TALLA = "//div[@id='pdp-form']/div/p";
//	private static final String XP_STICKY_CONTENT = "//div[@id='sticky-content']";
//	private static final String XP_TALLA_SELECTED = XP_STICKY_CONTENT + "//button[@data-testid='pdp.sticky.size']";
	private static final String XP_ICON_DESPLEGABLE_TALLAS = "//*[@data-testid='down-small']";
	
	private static final String XP_SELECTOR_TALLAS_DESKTOP = "//*[@data-testid='pdp.productInfo.sizeSelector']";
	private static final String XP_TALLA_ITEM_DESKTOP = XP_SELECTOR_TALLAS_DESKTOP + "//li/button[@data-testid[contains(.,'pdp.productInfo.sizeSelector')]]//..";
	private static final String XP_TALLA_SELECTED_DESKTOP = XP_TALLA_ITEM_DESKTOP + XP_ICON_DESPLEGABLE_TALLAS + "//ancestor::li";	
	private static final String XP_TALLA_AVAILABLE_DESKTOP = XP_TALLA_ITEM_DESKTOP + "//*[@data-testid[contains(.,'Available')]]";;
	private static final String XP_TALLA_UNAVAILABLE_DESKTOP = XP_TALLA_ITEM_DESKTOP + "//*[@data-testid[contains(.,'Unavailable')]]";
	
	private static final String XP_SELECTOR_TALLAS_MOBIL = "//*[@data-testid='sheet.draggable.dialog']";
	private static final String XP_TALLA_MOBIL = "//button[@data-testid[contains(.,'pdp.productInfo.sizeSelector')]]/..";
	private static final String XP_TALLA_ITEM_MOBIL = XP_SELECTOR_TALLAS_MOBIL + XP_TALLA_MOBIL;
	private static final String XP_TALLA_SELECTED_MOBIL = "//*[@data-testid='pdp.productInfo.sizeSelector']/button[@data-testid[contains(.,'pdp.productInfo.sizeSelector.sizeAvailable')]]";
	private static final String XP_TALLA_AVAILABLE_MOBIL = XP_TALLA_ITEM_MOBIL + "//*[@data-testid[contains(.,'Available')]]";
	private static final String XP_TALLA_UNAVAILABLE_MOBIL = XP_TALLA_ITEM_MOBIL + "//*[@data-testid[contains(.,'Unavailable')]]";

	private String getXPathSelectorTallas() {
		if (isDevice()) {
			return XP_SELECTOR_TALLAS_MOBIL;
		}
		return XP_SELECTOR_TALLAS_DESKTOP;
	}
	
	private String getXPathTallaItem() {
		if (isDevice()) {
			return XP_TALLA_ITEM_MOBIL;
		}
		return XP_TALLA_ITEM_DESKTOP;
	}
	
	private String getXPathTallaAvailable() {
		if (isDevice()) {
			return XP_TALLA_AVAILABLE_MOBIL;
		}
		return XP_TALLA_AVAILABLE_DESKTOP;
	}
	
	private String getXPathTallaUnavailable() {
		if (isDevice()) {
			return XP_TALLA_UNAVAILABLE_MOBIL;
		}
		return XP_TALLA_UNAVAILABLE_DESKTOP;
	}
	
	private String getXPathTallaSelected() {
		if (isDevice()) {
			return XP_TALLA_SELECTED_MOBIL;
		}
		return XP_TALLA_SELECTED_DESKTOP;
	}
	
	private String getXPathTallaValue(String tallaValue) {
		return getXPathTallaItem() + "//self::*[@data-testid[contains(.,'." + tallaValue + "')]]";
	}
	
	public boolean isVisibleAvisoSeleccionTalla() { //Tested
		return state(VISIBLE, XP_MSG_AVISO_SELECT_TALLA).check();
	}
	
	public boolean isVisibleListTallasForSelectUntil(int seconds) {
		return state(VISIBLE, getXPathSelectorTallas()).wait(seconds).check();
	}
	
	public boolean isTallaUnica() {
		if (isDevice()) {
			return isTallaUnicaMobil();
		}
		return isTallaUnicaDesktop();
	}
	
	private boolean isTallaUnicaMobil() {
		return getElements(XP_TALLA_MOBIL).isEmpty();
	}
	
	private boolean isTallaUnicaDesktop() {
		return getElements(getXPathTallaItem()).isEmpty();
	}
	
	public Talla getTallaSelected() {
		var pais = PaisShop.from(dataTest.getCodigoPais());
		return Talla.fromLabel(getTallaSelectedAlf(), pais);
	}

	public String getTallaSelectedAlf() {
		if (isTallaSelected()) {
			if (isTallaUnica()) {
				return "unitalla";
			}
			return getLabelTallaSelected();
		}
		return "";
	}
	
	public boolean selectGuiaDeTallasIfVisible() {
		if (state(VISIBLE, XP_LINK_GUIA_TALLAS).check()) {
			click(XP_LINK_GUIA_TALLAS).exec();
			return true;
		}
		return false;
	}
	
	private boolean isTallaSelected() {
		if (isDevice()) {
			return isTallaSelectedMobil();
		}
		return isTallaSelectedDesktop();
	}
	
	private boolean isTallaSelectedDesktop() { //Tested
		return state(VISIBLE, XP_TALLA_SELECTED_DESKTOP).check();
	}
	private boolean isTallaSelectedMobil() {
		return state(VISIBLE, XP_TALLA_SELECTED_MOBIL).check();
	}
	
	private String getLabelTallaSelected() { //Tested
		return getElement(getXPathTallaSelected()).getText();
	}

	private void unfoldTallas() {
		if (isDevice()) {
			unfoldTallasMobil();
		} else {
			unfoldTallasDesktop();
		}
	}
	
	private void unfoldTallasMobil() {
		waitMillis(250);
		if (!state(VISIBLE, XP_SELECTOR_TALLAS_MOBIL).check()) {
			clickIconOrButtonForUnfoldTallas();
			waitMillis(250);
		}
	}
	
	private void unfoldTallasDesktop() { 
		clickIconOrButtonForUnfoldTallas();
	}
	
	private void clickIconOrButtonForUnfoldTallas() {
		var icon = getElementVisible(XP_ICON_DESPLEGABLE_TALLAS);
		if (icon!=null) {
			//For when the chatbot icon overlays the tallas icon
			try {
				click(icon).exec();
			} catch (Exception e) {
				click(icon).by(By.xpath("./..")).exec();
			}
		}
	}
	
	private String getXPathTallaLabel(String label) {
		return getXPathTallaItem() + "//span[text()='" + label + "']/ancestor::li";
	}
	
	private String getXPathTalla(Talla talla) {
		String xpath = getXPathTallaItem() + "//span[";
		var pais = PaisShop.getPais(dataTest.getPais());
		var labels = talla.getLabels(pais);
		
		for (int i = 0; i < labels.size(); i++) {
			xpath += "text()='" + labels.get(i) + "'";
	        if (i < labels.size() - 1) {
	            xpath += " or ";
	        }
		}
		xpath+="]/ancestor::li";
		return xpath;
	}
	
	public void selectTallaByLabel(String label) { //Tested
		unfoldTallas();
		click(getXPathTallaLabel(label)).exec();
	}
	
	public void selectTallaByValue(String tallaValue) {
		unfoldTallas();
		click(getXPathTallaValue(tallaValue)).exec();
	}
	
	public void selectTallaByValue(Talla talla) {
		unfoldTallas();
		click(getXPathTalla(talla)).exec();
	}
	
	private String getXPathTalla(int position) {
		return "(" + getXPathTallaItem() + ")[" + position + "]";
	}
	
	public void selectTallaByIndex(int posicion) {
		unfoldTallas();
		click(getXPathTalla(posicion)).exec();
	}
	
	public void selectFirstTallaAvailable() {
		if (!isTallaUnica()) {
			unfoldTallas();
			click(getXPathTallaAvailable()).exec();
		}
	}
	
	public String getTallaAlf(int position) {
		unfoldTallas();
		String xpTalla = getXPathTalla(position); 
		return getElement(xpTalla).getText();
	}
	
	public String getTallaCodNum(int position) {
		unfoldTallas();
		String xpTalla = getXPathTalla(position); 
		String tallaDtid = getElement(xpTalla).getAttribute("data-testid");
		return tallaDtid.substring(tallaDtid.length() - 2);
	}
	
	public int getNumOptionsTallasNoDisponibles() {
		unfoldTallas();
		return getElements(getXPathTallaUnavailable()).size();
	}
	
	public int getNumOptionsTallas() {
		unfoldTallas();
		return getElements(getXPathTallaItem()).size();
	}
	
	public void closeTallas() {
		selectFirstTallaAvailable();
	}	
	
}

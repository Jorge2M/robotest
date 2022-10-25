package com.mng.robotest.test.pageobject.shop;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;
import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageMenusManto extends PageBase {

	private static final String XPATH_TITULO = "//td[@class='txt11B'] | //form[@id='formTempl']";
	private static final String XPATH_CELDA_TEXT_MENU_PRINCIPAL = "//td[text()[contains(.,'Menú principal')]]";
	private static final String XPATH_CABECERA_MENU = "//table//td[@bgcolor='#505050']/span";
	private static final String XPATH_LINK_MENU = "//table//a[@onclick]";
	
	public String getXPathLinkMenu(String menu) {
		return "//a[text()[contains(.,'" + menu + "')]]";
	}
	
	private String getXPathNextElement(String xpathPosicionInicial) {
		return xpathPosicionInicial + "/../following::td/a";
	}
	
	private String getXPathFirstElement(String menuName) {
		return XPATH_CABECERA_MENU + "[text()='" + menuName + "']" + "/../following::td";
	}

	private String getTextMenuTitulo() {
		return getElement(XPATH_TITULO).getText();
	}

	public boolean isPage() {
		return state(Present, XPATH_CELDA_TEXT_MENU_PRINCIPAL).check();
	}

	public boolean validateIsPage(String subMenu, int seconds) {
		for (int i=0; i<seconds; i++) {
			if (vaidateIsPage(subMenu)) {
				return true;
			}
			waitMillis(1000);
		}
		return false;
	} 
	
	private boolean vaidateIsPage(String subMenu) {
		String menuHeaderText = getTextMenuTitulo();
		String subMenuText = subMenu.replace("· ", "");
		if (menuHeaderText.contains(subMenuText) || menuHeaderText.contains(subMenuText.toUpperCase()) || 
			menuHeaderText.toUpperCase().contains(subMenuText.toUpperCase())) {
			return true;
		} else {
			if(isMenuHeaderVisible()) {
				return true;
			}
		}
		
		return false;
	}

	public boolean isMenuHeaderVisible() {
		return state(Present, XPATH_TITULO).check();
	}

	private boolean isNextXPathMenuHeader(String xpathPosicionInicial, String nextMenuName) {
		String xpathNextPosicion = xpathPosicionInicial + "/../following::td/child::node()";
		String texto = getElement(xpathNextPosicion).getText();
		return (!nextMenuName.equals(texto));
	}
	
	private boolean isNextXPathEndTable(String xpathPosicionInicial) {
		String xpathNextPosicion = xpathPosicionInicial + "/../following::td/child::node()";
		return state(Present, xpathNextPosicion).check();
	}
	
	public String clickMenuAndAcceptAlertIfExists(String textoMenu) {
		try {
			clickMenu(textoMenu);
		} catch (UnhandledAlertException f) {
			String textAlert = PageObjTM.acceptAlertIfExists(driver);
			if ("".compareTo(textAlert)==0) {
				return "Unknown or Empty";
			}
			return textAlert;
		}
		return "";
	}

	public void clickMenu(String textoMenu) {
		int maxTimeToWait = 60;
		int timeWaited = 0;
		if (textoMenu.contains("'")) {
			int positionDelete = textoMenu.indexOf("'");
			String textoMenuRecortado = textoMenu.substring(positionDelete+1, textoMenu.length());
			click(getXPathLinkMenu(textoMenuRecortado))
				.waitLink(60).waitLoadPage(60)
				.type(TypeClick.javascript).exec();
		}else {
			click(getXPathLinkMenu(textoMenu))
				.waitLink(60).waitLoadPage(60)
				.type(TypeClick.javascript).exec();
		}
		
		while (!state(Present, XPATH_TITULO).check() && 
				timeWaited != maxTimeToWait) {
			waitMillis(1000);
			timeWaited++;
		}
		
		waitForPageLoaded(driver, 60);
	}

	public List<WebElement> getListLinksMenus() {
		return getElements(XPATH_LINK_MENU);
	}
	
	public List<String> getListMenuNames() {
		List<String> listMenuNames = new ArrayList<>();
		List<WebElement> listElemMenus = getListLinksMenus();
		for (WebElement menu : listElemMenus)
			listMenuNames.add(menu.getText());
		
		return listMenuNames;
	}
	
	public List<WebElement> getListCabecerasMenus() {
		return getElements(XPATH_CABECERA_MENU);
	}
	
	public List<String> getListCabecerasMenusName() {
		List<String> listCabecerasNames = new ArrayList<>();
		List<WebElement> listCabecerasMenus = getListCabecerasMenus();
		for (WebElement menu : listCabecerasMenus)
			listCabecerasNames.add(menu.getText());
		
		return listCabecerasNames;
	}

	public List<String> getListSubMenusName(String menuName, String nextMenuName) {
		List<String> listSubMenusNames = new ArrayList<>();
		List<WebElement> listSubMenus = getListSubMenus(menuName, nextMenuName);
		for (WebElement menu : listSubMenus) {
			listSubMenusNames.add(menu.getText());
		}
		return listSubMenusNames;
	}
	
	public List<WebElement> getListSubMenus(String menuName, String nextMenuName) {
		String xpathPosicionInicial = getXPathFirstElement(menuName);
		List<WebElement> elements = new ArrayList<>();
		elements.add(getElement(xpathPosicionInicial));
		if (nextMenuName==null) {
			while (isNextXPathEndTable(xpathPosicionInicial)){
				xpathPosicionInicial = getXPathNextElement(xpathPosicionInicial);
				elements.add(getElement(xpathPosicionInicial));
			}
			return elements;
		}
		while (isNextXPathMenuHeader(xpathPosicionInicial, nextMenuName)) {
			xpathPosicionInicial = getXPathNextElement(xpathPosicionInicial);
			elements.add(getElement(xpathPosicionInicial));
		}
		return elements;
	}
}

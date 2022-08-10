package com.mng.robotest.test.pageobject.shop;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;
import com.mng.robotest.domains.transversal.PageBase;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.SeleniumUtils;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageMenusManto extends PageBase {

	private static final String INI_XPATH_TITULO = "//td[@class='txt11B' and text()[contains(.,'";
	private static final String XPATH_TITULO = "//td[@class='txt11B'] | //form[@id='formTempl']";
	private static final String XPATH_CELDA_TEXT_MENU_PRINCIPAL = "//td[text()[contains(.,'Menú principal')]]";
	private static final String XPATH_CABECERA_MENU = "//table//td[@bgcolor='#505050']/span";
	private static final String XPATH_SUB_MENUS = "//table//td/a[@onclick]";
	private static final String XPATH_LINK_MENU = "//table//a[@onclick]";
	private static final String XPATH_TABLE_LAST_ELEMENT = "//table[@id='tabla_derecha']//tr[last()]/td/a[@onclick]";
	
	public String getXpathLinkMenu(String menu) {
		return "//a[text()[contains(.,'" + menu + "')]]";
	}
	
	private String getXPathTitulo(String title){
		return (INI_XPATH_TITULO + title + "')]]");
	}
	
	private String getXPathNextElement(String XPathPosicionInicial) {
		return XPathPosicionInicial + "/../following::td/a";
	}
	
	private String getXPathFirstElement(String menuName) {
		return XPATH_CABECERA_MENU + "[text()='" + menuName + "']" + "/../following::td";
	}

	private String getTextMenuTitulo() {
		return driver.findElement(By.xpath(XPATH_TITULO)).getText();
	}

	public boolean isPage() {
		return (state(Present, By.xpath(XPATH_CELDA_TEXT_MENU_PRINCIPAL)).check());
	}

	public boolean validateIsPage(String subMenu, int maxSeconds) {
		for (int i=0; i<maxSeconds; i++) {
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
		return state(Present, By.xpath(XPATH_TITULO)).check();
	}

	private boolean isNextXPathMenuHeader(String XPathPosicionInicial, String nextMenuName) {
		String XPathNextPosicion = XPathPosicionInicial + "/../following::td/child::node()";
		String texto = driver.findElement(By.xpath(XPathNextPosicion)).getText();
		return (!nextMenuName.equals(texto));
	}
	
	private boolean isNextXPathEndTable(String XPathPosicionInicial) {
		String XPathNextPosicion = XPathPosicionInicial + "/../following::td/child::node()";
		if (!state(Present, By.xpath(XPathNextPosicion)).check()) {
			return false;
		}
		return true;
		
	}
	
	public String clickMenuAndAcceptAlertIfExists(String textoMenu) throws Exception {
		try {
			clickMenu(textoMenu);
		} catch (UnhandledAlertException f) {
			String textAlert = SeleniumUtils.acceptAlertIfExists(driver);
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
			By byElem = By.xpath(getXpathLinkMenu(textoMenuRecortado));
			click(byElem)
				.waitLink(60).waitLoadPage(60)
				.type(TypeClick.javascript).exec();
		}else {
			By byElem = By.xpath(getXpathLinkMenu(textoMenu));
			click(byElem)
				.waitLink(60).waitLoadPage(60)
				.type(TypeClick.javascript).exec();
		}
		
		while (!state(Present, By.xpath(XPATH_TITULO)).check() && 
				timeWaited != maxTimeToWait) {
			waitMillis(1000);
			timeWaited++;
		}
		
		waitForPageLoaded(driver, 60);
	}

	public List<WebElement> getListLinksMenus() {
		return (driver.findElements(By.xpath(XPATH_LINK_MENU)));
	}
	
	public ArrayList<String> getListMenuNames() {
		ArrayList<String> listMenuNames = new ArrayList<>();
		List<WebElement> listElemMenus = getListLinksMenus();
		for (WebElement menu : listElemMenus)
			listMenuNames.add(menu.getText());
		
		return listMenuNames;
	}
	
	public List<WebElement> getListCabecerasMenus() {
		return (driver.findElements(By.xpath(XPATH_CABECERA_MENU)));
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
		for (WebElement menu : listSubMenus)
			listSubMenusNames.add(menu.getText());
		
		return listSubMenusNames;
	}
	
	public List<WebElement> getListSubMenus(String menuName, String nextMenuName) {
		String XPathPosicionInicial = getXPathFirstElement(menuName);
		List<WebElement> elements = new ArrayList<>();
		elements.add(driver.findElement(By.xpath(XPathPosicionInicial)));
		if (nextMenuName==null) {
			while (isNextXPathEndTable(XPathPosicionInicial)){
				XPathPosicionInicial = getXPathNextElement(XPathPosicionInicial);
				elements.add(driver.findElement(By.xpath(XPathPosicionInicial)));
			}
			return elements;
		}
		while (isNextXPathMenuHeader(XPathPosicionInicial, nextMenuName)){
			XPathPosicionInicial = getXPathNextElement(XPathPosicionInicial);
			elements.add(driver.findElement(By.xpath(XPathPosicionInicial)));
		}
		return elements;
	}
}

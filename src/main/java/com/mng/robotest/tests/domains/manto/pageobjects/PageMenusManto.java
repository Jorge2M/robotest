package com.mng.robotest.tests.domains.manto.pageobjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.manto.tests.MenusFact.Section;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageMenusManto extends PageBase {

	private static final String XP_TITULO = "//td[@class='txt11B'] | //form[@id='formTempl']";
	private static final String XP_CELDA_TEXT_MENU_PRINCIPAL = "//td[text()[contains(.,'Menú principal')]]";
	private static final String XP_CABECERA_MENU = "//table//td[@bgcolor='#505050']/span";
	private static final String XP_LINK_MENU = "//table//a[@onclick]";
	
	private static final List<Pair<Section, String>> BLACK_LIST_MENUS = Arrays.asList(
			Pair.of(Section.MARKETPLACES, "· Zalando Shapes"),
			Pair.of(Section.MARKETPLACES, "· Publish Errors")); 
	
	private String getXPathLinkMenu(String menu) {
		return "//a[text()[contains(.,'" + menu + "')]]";
	}
	private String getXPathSubMenusFromSection(Section section) {
		return "//td/span[text()='" + section.getCabecera() + "']/../../following::tr";
	}
	
	private String getTextMenuTitulo() {
		return getElement(XP_TITULO).getText();
	}

	public boolean isPage() {
		return state(PRESENT, XP_CELDA_TEXT_MENU_PRINCIPAL).check();
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
		return state(PRESENT, XP_TITULO).check();
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
				.type(JAVASCRIPT).exec();
		}else {
			click(getXPathLinkMenu(textoMenu))
				.waitLink(60).waitLoadPage(60)
				.type(JAVASCRIPT).exec();
		}
		
		while (!state(PRESENT, XP_TITULO).check() && 
				timeWaited != maxTimeToWait) {
			waitMillis(1000);
			timeWaited++;
		}
		
		waitForPageLoaded(driver, 60);
	}

	public List<WebElement> getListLinksMenus() {
		return getElements(XP_LINK_MENU);
	}
	
	public List<String> getListMenuNames() {
		List<String> listMenuNames = new ArrayList<>();
		List<WebElement> listElemMenus = getListLinksMenus();
		for (WebElement menu : listElemMenus)
			listMenuNames.add(menu.getText());
		
		return listMenuNames;
	}
	
	public List<WebElement> getListCabecerasMenus() {
		return getElements(XP_CABECERA_MENU);
	}
	
	public List<String> getListCabecerasMenusName() {
		List<String> listCabecerasNames = new ArrayList<>();
		List<WebElement> listCabecerasMenus = getListCabecerasMenus();
		for (WebElement menu : listCabecerasMenus)
			listCabecerasNames.add(menu.getText());
		
		return listCabecerasNames;
	}

	public List<String> getListSubMenusName(Section section) {
		return getListAncorSubMenusFromSection(section).stream()
				.map(a -> a.getText())
				.filter(t -> !BLACK_LIST_MENUS.contains(Pair.of(section, t)))
				.toList();
	}
	
	private List<WebElement> getListAncorSubMenusFromSection(Section section) {
		var menusResult = new ArrayList<WebElement>();
		String xpathMenu = getXPathSubMenusFromSection(section);
		for (WebElement menuItemTr : getElements(xpathMenu)) {
			var ancorMenu = getAncorInMenu(menuItemTr); 
			if (ancorMenu.isPresent()) {
				menusResult.add(ancorMenu.get());
			} else {
				break;
			}
		}
		return menusResult;
	}
	private Optional<WebElement> getAncorInMenu(WebElement menuItemTr) {
		try {
			return Optional.of(getElement(menuItemTr, ".//a"));
		}
		catch (NoSuchElementException e) {
			return Optional.empty();
		}
	}
	
}

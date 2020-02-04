package com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenuLateralDesktop;

public class SecMenuLateralDesktop extends WebdrvWrapp {
	
	private final WebDriver driver;
	private final AppEcom app;
	
	private static String TagConcatMenus = "[@TAG_CONCAT_MENUS]";
	
	private static String XPathLinkMenuWithTagOutlet = 
		"//div[@class[contains(.,'sidebar')]]" + 
		"//li[@data-ga-label[contains(.,'" + TagConcatMenus + "')]]" +
		"/a";
	private static String XPathSelectedRelativeMenuOutlet = 
		"//self::*[@class[contains(.,'--selected')]]";
	
	private static String XPathLinkMenuWithTagShop = 
		"//li[@class='_3AcVO' or @class='element' or not(@class)]" + //El not(@class) viene por un desarrollo de Jesús en un cloud (4-02-2020) 
		"/a[@href[contains(.,'" + TagConcatMenus + "')]]";
	private static String XPathSelectedRelativeMenuShop = 
		"//self::*[@aria-label[contains(.,'seleccionado')]]";

	private SecMenuLateralDesktop(AppEcom app, WebDriver driver) {
		this.driver = driver;
		this.app = app;
	}
	public static SecMenuLateralDesktop getNew(AppEcom app, WebDriver driver) {
		return (new SecMenuLateralDesktop(app, driver));
	}

	private String getXPathLinkMenu(MenuLateralDesktop menu) {
		String dataGaLabel =  menu.getDataGaLabelMenuLateralDesktop();
		switch (app) {
		case outlet:
			return (XPathLinkMenuWithTagOutlet.replace(TagConcatMenus, dataGaLabel));
		default:
			return (XPathLinkMenuWithTagShop
				.replace(TagConcatMenus, dataGaLabel
							.replace(":", "-")
							.replaceFirst("-", "/")));
		}
	}
	
	private String getXPathSelectedRelativeMenu() {
		switch (app) {
		case outlet:
			return XPathSelectedRelativeMenuOutlet;
		default:
			return XPathSelectedRelativeMenuShop;
		}
	}
	
	public String getXPathLinkMenuSelected(MenuLateralDesktop menu) {
		return (getXPathLinkMenu(menu) + getXPathSelectedRelativeMenu());
	}

	public boolean isSelectedMenu(MenuLateralDesktop menu, int maxSecondsToWait) {
		String linkMenuSel = getXPathLinkMenuSelected(menu) ;
		return (isElementVisibleUntil(driver, By.xpath(linkMenuSel), maxSecondsToWait));
	}

	public void clickMenu(MenuLateralDesktop menu) throws Exception {
		String xpathMenu1erNivel = getXPathLinkMenu(menu);
		WebdrvWrapp.moveToElement(By.xpath(xpathMenu1erNivel), driver);
		clickAndWaitLoad(driver, By.xpath(xpathMenu1erNivel));
	}

	/**
	 * @return si es o no visible un menú lateral de 1er (menu2oNivel=null) o 2o nivel (menu2oNivel!=null)
	 */
	public boolean isVisibleMenu(MenuLateralDesktop menu) throws Exception {
		String xpathMenu = getXPathLinkMenu(menu);
		return (isElementVisible(driver, By.xpath(xpathMenu)));
	}
}

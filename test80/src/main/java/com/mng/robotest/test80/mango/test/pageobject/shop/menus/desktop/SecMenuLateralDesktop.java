package com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenuLateralDesktop;

public class SecMenuLateralDesktop extends PageObjTM {
	
	private final AppEcom app;
	
	private static String TagConcatMenus = "[@TAG_CONCAT_MENUS]";
	
//	private static String XPathLinkMenuWithTagOutletOld = 
//		"//div[@class[contains(.,'sidebar')]]" + 
//		"//li[@data-ga-label[contains(.,'" + TagConcatMenus + "')]]" +
//		"/a";
//	private static String XPathLinkMenuWithTagOutletNew = 
//		"//li[@class[contains(.,'element')]]" +  
//		"/a[@href[contains(.,'" + TagConcatMenus + "')]]";
//	private static String XPathSelectedRelativeMenuOutlet = 
//		"//self::*[@class[contains(.,'--selected')]]";
	
	private static String XPathLinkMenuWithTagShop = 
		"//li[not(@class) or @class='element']" +  
		"/a[@href[contains(.,'" + TagConcatMenus + "')]]";
	private static String XPathLinkMenuWithTagOutlet = 
		"//li[@class='_3AcVO' or @class='element']" +  
		"/a[@href[contains(.,'" + TagConcatMenus + "')]]";
	private static String XPathSelectedRelativeMenuShop = 
		"//self::*[@aria-label[contains(.,'seleccionado')]]";

	private SecMenuLateralDesktop(AppEcom app, WebDriver driver) {
		super(driver);
		this.app = app;
	}
	public static SecMenuLateralDesktop getNew(AppEcom app, WebDriver driver) {
		return (new SecMenuLateralDesktop(app, driver));
	}

	private String getXPathLinkMenu(MenuLateralDesktop menu) {
		String dataGaLabel =  menu.getDataGaLabelMenuLateralDesktop();
		switch (app) {
		case outlet:
			return (XPathLinkMenuWithTagOutlet
					.replace(TagConcatMenus, dataGaLabel
					.replace(":", "-")
					.replaceFirst("-", "/")));
//			switch (PageGaleriaDesktop.getOutletVersion(driver)) {
//			case newwithreact:
//				return (XPathLinkMenuWithTagOutletNew
//					.replace(TagConcatMenus, dataGaLabel
//					.replace(":", "-")
//					.replaceFirst("-", "/")));
//			case old:
//				return (XPathLinkMenuWithTagOutletOld.replace(TagConcatMenus, dataGaLabel));
//			}
		default:
			return (XPathLinkMenuWithTagShop
				.replace(TagConcatMenus, dataGaLabel
				.replace(":", "-")
				.replaceFirst("-", "/")));
		}
	}
	
	private String getXPathSelectedRelativeMenu() {
		switch (app) {
//		case outlet:
//			return XPathSelectedRelativeMenuOutlet;
		default:
			return XPathSelectedRelativeMenuShop;
		}
	}
	
	public String getXPathLinkMenuSelected(MenuLateralDesktop menu) {
		return (getXPathLinkMenu(menu) + getXPathSelectedRelativeMenu());
	}

	public boolean isSelectedMenu(MenuLateralDesktop menu, int maxSeconds) {
		String linkMenuSel = getXPathLinkMenuSelected(menu) ;
		return (state(Visible, By.xpath(linkMenuSel)).wait(maxSeconds).check());
	}

	public void clickMenu(MenuLateralDesktop menu) {
		String xpathMenu1erNivel = getXPathLinkMenu(menu);
		moveToElement(By.xpath(xpathMenu1erNivel), driver);
		click(By.xpath(xpathMenu1erNivel)).exec();
	}

	/**
	 * @return si es o no visible un menú lateral de 1er (menu2oNivel=null) o 2o nivel (menu2oNivel!=null)
	 */
	public boolean isVisibleMenu(MenuLateralDesktop menu) throws Exception {
		String xpathMenu = getXPathLinkMenu(menu);
		return (state(Visible, By.xpath(xpathMenu)).check());
	}
}

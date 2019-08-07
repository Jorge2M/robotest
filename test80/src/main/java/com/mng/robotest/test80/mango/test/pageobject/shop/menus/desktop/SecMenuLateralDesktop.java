package com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenuLateralDesktop;

public class SecMenuLateralDesktop extends WebdrvWrapp {
	
	private final WebDriver driver;
	
	private static String TagConcatMenus = "[@TAG_CONCAT_MENUS]";
	private static String XPathLinkMenuWithTag = 
		"//div[@class[contains(.,'sidebar')]]//li[@data-ga-label[contains(.,'" + TagConcatMenus + "')]]/a";
	private static String XPathSelectedRelativeMenu = "//self::*[@class[contains(.,'--selected')]]";
	
	private SecMenuLateralDesktop(WebDriver driver) {
		this.driver = driver;
	}
	
	public static SecMenuLateralDesktop getNew(WebDriver driver) {
		return (new SecMenuLateralDesktop(driver));
	}
	
	public String getXPathLinkMenu(MenuLateralDesktop menu) {
        String dataGaLabel =  menu.getDataGaLabelMenuLateralDesktop();
        return (XPathLinkMenuWithTag.replace(TagConcatMenus, dataGaLabel));
	}
    
    public String getXPathLinkMenuSelected(MenuLateralDesktop menu) {
        return (getXPathLinkMenu(menu) + XPathSelectedRelativeMenu);
    }
    
    public boolean isSelectedMenu(MenuLateralDesktop menu, int maxSecondsToWait) {
        String linkMenuSel = getXPathLinkMenuSelected(menu) ;
        return (isElementVisibleUntil(driver, By.xpath(linkMenuSel), maxSecondsToWait));
    }
    
    public void clickMenu(MenuLateralDesktop menu) throws Exception {
        String xpathMenu1erNivel = getXPathLinkMenu(menu);
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

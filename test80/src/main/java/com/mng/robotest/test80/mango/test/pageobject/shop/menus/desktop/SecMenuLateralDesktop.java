package com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.Menu2onLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenuLateralDesktop;

public class SecMenuLateralDesktop extends WebdrvWrapp {
	
	static String TagConcatMenus = "[@TAG_CONCAT_MENUS]";
	static String XPathLinkMenuWithTag = 
		"//div[@class[contains(.,'sidebar')]]//li[@data-ga-label[contains(.,'" + TagConcatMenus + "')]]/a";
	static String XPathSelectedRelativeMenu = "//self::*[@class[contains(.,'--selected')]]";
	
	public static String getXPathLinkMenu(MenuLateralDesktop menu) {
        String dataGaLabel =  menu.getDataGaLabelMenuLateralDesktop();
        return (XPathLinkMenuWithTag.replace(TagConcatMenus, dataGaLabel));
	}
    
    public static String getXPathLinkMenuSelected(MenuLateralDesktop menu) {
        return (getXPathLinkMenu(menu) + XPathSelectedRelativeMenu);
    }
    
    public static boolean isSelectedMenu(MenuLateralDesktop menu, int maxSecondsToWait, WebDriver driver) {
        String linkMenuSel = getXPathLinkMenuSelected(menu) ;
        return (isElementVisibleUntil(driver, By.xpath(linkMenuSel), maxSecondsToWait));
    }
    
    public static void clickMenu(MenuLateralDesktop menu, WebDriver driver) throws Exception {
        String xpathMenu1erNivel = getXPathLinkMenu(menu);
        clickAndWaitLoad(driver, By.xpath(xpathMenu1erNivel));
    }
    
    /**
     * @return si es o no visible un menú lateral de 1er (menu2oNivel=null) o 2o nivel (menu2oNivel!=null)
     */
    public static boolean isVisibleMenu(WebDriver driver, MenuLateralDesktop menu) throws Exception {
        String xpathMenu = getXPathLinkMenu(menu);
        return (isElementVisible(driver, By.xpath(xpathMenu)));
    }    

    /**
     * @return si son visibles una lista de menús de 2o nivel asociados a uno concreto de 1er nivel
     */
    public static boolean areVisibleMenus2oNivel(Menu1rstLevel menu1rstLevel, WebDriver driver) throws Exception {
        for (Menu2onLevel menu2oNivelTmp : menu1rstLevel.getListMenus2onLevel()) {
            if (!isVisibleMenu(driver, menu2oNivelTmp)) 
            	return false;
        }
        
        return true;
    }
}

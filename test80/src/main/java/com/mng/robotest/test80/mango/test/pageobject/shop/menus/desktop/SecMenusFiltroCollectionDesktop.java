package com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.filtros.FilterCollection;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusFiltroCollection;


public class SecMenusFiltroCollectionDesktop extends WebdrvWrapp implements SecMenusFiltroCollection {

	private WebDriver driver;
    private static String XPathDivMenusDesktop = "//div[@id='nuevaTemporadaFilter']";
    
    private static String getXPathMenu(FilterCollection typeMenu) {
        return (XPathDivMenusDesktop + "//li[@data-filter-value='" + typeMenu.getDataFilterValue() + "']");
    }
    
    public SecMenusFiltroCollectionDesktop(WebDriver driver) {
    	this.driver = driver;
    }
    
    @Override
    public boolean isVisible() {
        return (isElementVisible(driver, By.xpath(XPathDivMenusDesktop)));
    }
    
    @Override
    public boolean isVisibleMenu(FilterCollection typeMenu) {
        String xpathMenu = getXPathMenu(typeMenu);
        return (isElementVisible(driver, By.xpath(xpathMenu)));
    }
    
    @Override
    public void click(FilterCollection typeMenu) throws Exception {
    	String xpathMenu = getXPathMenu(typeMenu);
    	clickAndWaitLoad(driver, By.xpath(xpathMenu));
    }
}

package com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;
import com.mng.robotest.test80.mango.test.pageobject.shop.filtros.FilterCollection;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusFiltroCollection;


public class SecMenusFiltroCollectionDesktop extends PageObjTM implements SecMenusFiltroCollection {

    private static String XPathDivMenusDesktop = "//div[@id='nuevaTemporadaFilter']";
    
    private static String getXPathMenu(FilterCollection typeMenu) {
        return (XPathDivMenusDesktop + "//li[@data-filter-value='" + typeMenu.getDataFilterValue() + "']");
    }
    
    public SecMenusFiltroCollectionDesktop(WebDriver driver) {
    	super(driver);
    }
    
    @Override
    public boolean isVisible() {
    	return (state(Visible, By.xpath(XPathDivMenusDesktop)).check());
    }
    
    @Override
    public boolean isVisibleMenu(FilterCollection typeMenu) {
        String xpathMenu = getXPathMenu(typeMenu);
        return (state(Visible, By.xpath(xpathMenu)).check());
    }
    
    @Override
    public void click(FilterCollection typeMenu) throws Exception {
    	String xpathMenu = getXPathMenu(typeMenu);
    	clickAndWaitLoad(driver, By.xpath(xpathMenu));
    }
}

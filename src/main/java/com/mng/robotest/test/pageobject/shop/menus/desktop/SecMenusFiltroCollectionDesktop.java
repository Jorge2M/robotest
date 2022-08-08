package com.mng.robotest.test.pageobject.shop.menus.desktop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.pageobject.shop.filtros.FilterCollection;
import com.mng.robotest.test.pageobject.shop.menus.SecMenusFiltroCollection;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecMenusFiltroCollectionDesktop extends PageBase implements SecMenusFiltroCollection {

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
	public void click(FilterCollection typeMenu) {
		String xpathMenu = getXPathMenu(typeMenu);
		click(By.xpath(xpathMenu)).exec();
	}
}

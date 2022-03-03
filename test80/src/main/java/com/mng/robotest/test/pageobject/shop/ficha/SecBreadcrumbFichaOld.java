package com.mng.robotest.test.pageobject.shop.ficha;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecBreadcrumbFichaOld {
	
	public enum ItemBCrumb {Line, Group, Galery}
	
	static String XPathLinea = "//nav[@class='nav-product']";
	static String XPathBreadCrumb = "//ol[@typeof='BreadcrumbList']";
	static String XPathBreadCrumbItem = XPathBreadCrumb + "/li[@typeof='ListItem']";
	
	public static String getXPathBreadCrumbItem(ItemBCrumb itemBCrumb) {
		int position = 1;
		switch (itemBCrumb) {
		case Line: 
			position = 1;
			break;
		case Group:
			position = 2;
			break;
		case Galery:
		default:
			position = 3;
		}
		
		return "(" + XPathBreadCrumbItem + ")[" + position + "]";
	}
	
	public static boolean isVisibleBreadCrumb(WebDriver driver) {
		return (state(Visible, By.xpath(XPathBreadCrumb), driver).check());
	}
	
	public static String getUrlItemBreadCrumb(ItemBCrumb itemBCrumb, WebDriver driver) {
		String xpathItem = getXPathBreadCrumbItem(itemBCrumb) + "//a";
		if (state(Visible, By.xpath(xpathItem), driver).check()) {
			return driver.findElement(By.xpath(xpathItem)).getAttribute("href");
		}
		return "";
	}
	
	public static String getNameItemBreadCrumb(ItemBCrumb itemBCrumb, WebDriver driver) {
		String xpathItem = getXPathBreadCrumbItem(itemBCrumb) + "//span";
		if (state(Visible, By.xpath(xpathItem), driver).check()) {
			return driver.findElement(By.xpath(xpathItem)).getAttribute("innerHTML");
		}
		return "";
	}
}

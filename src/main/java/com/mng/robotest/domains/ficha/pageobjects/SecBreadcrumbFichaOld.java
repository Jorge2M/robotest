package com.mng.robotest.domains.ficha.pageobjects;


import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.transversal.PageBase;


public class SecBreadcrumbFichaOld extends PageBase {
	
	public enum ItemBCrumb { LINE, GROUP, GALERY }
	
	private static final String XPATH_BREAD_CRUMB = "//ol[@typeof='BreadcrumbList']";
	private static final String XPATH_BREAD_CRUMB_ITEM = XPATH_BREAD_CRUMB + "/li[@typeof='ListItem']";
	
	public static String getXPathBreadCrumbItem(ItemBCrumb itemBCrumb) {
		int position = 1;
		switch (itemBCrumb) {
		case LINE: 
			position = 1;
			break;
		case GROUP:
			position = 2;
			break;
		case GALERY:
		default:
			position = 3;
		}
		
		return "(" + XPATH_BREAD_CRUMB_ITEM + ")[" + position + "]";
	}
	
	public boolean isVisibleBreadCrumb() {
		return state(Visible, XPATH_BREAD_CRUMB).check();
	}
	
	public String getUrlItemBreadCrumb(ItemBCrumb itemBCrumb) {
		String xpathItem = getXPathBreadCrumbItem(itemBCrumb) + "//a";
		if (state(Visible, xpathItem).check()) {
			return getElement(xpathItem).getAttribute("href");
		}
		return "";
	}
	
	public String getNameItemBreadCrumb(ItemBCrumb itemBCrumb) {
		String xpathItem = getXPathBreadCrumbItem(itemBCrumb) + "//span";
		if (state(Visible, xpathItem).check()) {
			return getElement(xpathItem).getAttribute("innerHTML");
		}
		return "";
	}
}

package com.mng.robotest.test.pageobject.shop.menus;

import org.openqa.selenium.By;

import com.mng.robotest.domains.transversal.PageBase;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecMenusFiltroDiscount extends PageBase {
	
	public enum TypeMenuDiscount { UP_TO_50, UP_TO_60, BETWEEN_50_60, OFF_60, FROM_60, FROM_70 }
	static String XPathDivMenus = "//nav[@id='descuentoFilter']";
	
	private static String getXPathMenu(TypeMenuDiscount typeMenu) {
		String valuesParamTemporada1 = "";
		switch (typeMenu) {
		case UP_TO_50:
			valuesParamTemporada1 = "1/49";
			break;
		case UP_TO_60:
			valuesParamTemporada1 = "1/59";
			break;
		case BETWEEN_50_60:
			valuesParamTemporada1 = "50/59";
			break;
		case OFF_60:
			valuesParamTemporada1 = "60/69";
			break;			
		case FROM_60:
			valuesParamTemporada1 = "60/90";
			break;
		case FROM_70:
		default:
			valuesParamTemporada1 = "70/90";
			break;
		}		
		
		return (XPathDivMenus + "//li[@data-filter-value='" + valuesParamTemporada1 + "']");
	}
	
	public boolean isVisible() {
		return (state(Visible, By.xpath(XPathDivMenus)).check());
	}
	
	public boolean isVisibleMenu(String typeMenu) {
		return (isVisibleMenu(TypeMenuDiscount.valueOf(typeMenu)));
	}
	
	public boolean isVisibleMenu(TypeMenuDiscount typeMenu) {
		String xpathMenu = getXPathMenu(typeMenu);
		return (state(Visible, By.xpath(xpathMenu)).check());
	}
	
	public int getNumberOfVisibleMenus() {
		int numberMenusVisibles = 0;
		for (TypeMenuDiscount typeMenu : TypeMenuDiscount.values()) {
			if (isVisibleMenu(typeMenu)) {
				numberMenusVisibles+=1;
			}
		}
		
		return numberMenusVisibles;
	}
	
	public void clickTypeMenu(String typeMenu) {
		clickTypeMenu(TypeMenuDiscount.valueOf(typeMenu));
	}

	public void clickTypeMenu(TypeMenuDiscount typeMenu) {
		String xpathMenu = getXPathMenu(typeMenu);
		click(By.xpath(xpathMenu)).exec();
	}
}

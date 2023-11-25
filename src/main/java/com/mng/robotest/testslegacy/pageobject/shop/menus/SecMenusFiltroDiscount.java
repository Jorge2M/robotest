package com.mng.robotest.testslegacy.pageobject.shop.menus;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class SecMenusFiltroDiscount extends PageBase {
	
	public enum TypeMenuDiscount { UP_TO_50, UP_TO_60, BETWEEN_50_60, OFF_60, FROM_60, FROM_70 }
	
	private static final String XP_DIV_MENUS = "//nav[@id='descuentoFilter']";
	
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
		
		return (XP_DIV_MENUS + "//li[@data-filter-value='" + valuesParamTemporada1 + "']");
	}
	
	public boolean isVisible() {
		return state(Visible, XP_DIV_MENUS).check();
	}
	
	public boolean isVisibleMenu(String typeMenu) {
		return isVisibleMenu(TypeMenuDiscount.valueOf(typeMenu));
	}
	
	public boolean isVisibleMenu(TypeMenuDiscount typeMenu) {
		String xpathMenu = getXPathMenu(typeMenu);
		return state(Visible, xpathMenu).check();
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
		click(getXPathMenu(typeMenu)).exec();
	}
}

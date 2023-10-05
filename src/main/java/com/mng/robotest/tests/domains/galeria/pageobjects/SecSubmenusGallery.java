package com.mng.robotest.tests.domains.galeria.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.PageBase;

public class SecSubmenusGallery extends PageBase {

	private static final String XPATH_CAPA_SHOP = "//div[@id='title']";
	private static final String XPATH_CAPA_OUTLET = "//header[@id='catalogTitle']";
	
	private String getXPathCapa() {
		if (app==AppEcom.shop) {
			return XPATH_CAPA_SHOP;
		}
		return XPATH_CAPA_OUTLET;
	}
	
	private String getXPathMenu(String nameMenu) {
		String nameMenuFirstCapital = nameMenu.substring(0, 1).toUpperCase() + nameMenu.substring(1);
		return 
			getXPathCapa() + 
			"//a[text()='" + nameMenu + "' or text()='" + nameMenuFirstCapital + "']";
	}
	
	public boolean isVisible(int seconds) {
		return state(Visible, getXPathCapa()).wait(seconds).check();
	}
	
	public boolean isVisibleSubmenu(String nameMenu) {
		return state(Visible, getXPathMenu(nameMenu)).wait(1).check();
	}
	
	public boolean isMenuSelected(String nameMenu) {
		if (isVisibleSubmenu(nameMenu)) {
			String xpath = getXPathMenu(nameMenu);
			//TODO solicitar data-testid
			return getElement(xpath).getAttribute("className").contains("vHguD");
		}
		return false;
	}
	
	public void clickSubmenu(String nameMenu) {
		click(getXPathMenu(nameMenu)).exec();
	}
}

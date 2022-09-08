package com.mng.robotest.test.pageobject.shop.galeria;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.domains.transversal.PageBase;

public class SecSubmenusGallery extends PageBase {

	private static final String XPATH_CAPA = "//div[@id='title']";
	private static final String TAG_NAME = "@TAG_NAME";
	private static final String TAG2_NAME = "@TAG2_NAME";
	private static final String XPATH_SUBMENU_ITEM = XPATH_CAPA + "//a[text()='" + TAG_NAME + "' or text()='" + TAG2_NAME + "']";
	
	private String getXPathMenu(String nameMenu) {
		String nameMenuFirstCapital = nameMenu.substring(0, 1).toUpperCase() + nameMenu.substring(1);
		return XPATH_SUBMENU_ITEM
				.replace(TAG_NAME, nameMenu)
				.replace(TAG2_NAME, nameMenuFirstCapital);
	}
	
	public boolean isVisible(int maxSeconds) {
		return state(State.Visible, XPATH_CAPA).wait(maxSeconds).check();
	}
	
	public boolean isVisibleSubmenu(String nameMenu) {
		return state(State.Visible, getXPathMenu(nameMenu)).wait(1).check();
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

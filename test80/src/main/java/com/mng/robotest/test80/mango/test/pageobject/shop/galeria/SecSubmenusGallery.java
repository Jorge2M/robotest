package com.mng.robotest.test80.mango.test.pageobject.shop.galeria;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

public class SecSubmenusGallery {

	//TODO modificar cuando dispongamos del data-testid
	private final static String XPathCapa = "//div[@class[contains(.,'src-catalog-header-navigation')] or @class[contains(.,'_3dYiR')]]";
	
	private final static String TAG_NAME = "@TAG_NAME";
	private final static String TAG2_NAME = "@TAG2_NAME";
	private final static String XPathSubmenuItem = XPathCapa + "/a[text()='" + TAG_NAME + "' or text()='" + TAG2_NAME + "']";
	
	private static String getXPathMenu(String nameMenu) {
		String nameMenuFirstCapital = nameMenu.substring(0, 1).toUpperCase() + nameMenu.substring(1);
		return XPathSubmenuItem
				.replace(TAG_NAME, nameMenu)
				.replace(TAG2_NAME, nameMenuFirstCapital);
	}
	
	public static boolean isVisible(int maxSeconds, WebDriver driver) {
		return PageObjTM.state(State.Visible, By.xpath(XPathCapa), driver).wait(maxSeconds).check();
	}
	
	public static boolean isVisibleSubmenu(String nameMenu, WebDriver driver) {
		String xpath = getXPathMenu(nameMenu);
		return PageObjTM.state(State.Visible, By.xpath(xpath), driver).check();
	}
}

package com.mng.robotest.test.pageobject.shop.galeria;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

public class SecSubmenusGallery {

	//TODO modificar cuando dispongamos del data-testid (React)
	private static final String XPathCapa = "//div[@class[contains(.,'src-catalog-header-navigation')] or @class[contains(.,'XNV9w')]]";
	
	private static final String TAG_NAME = "@TAG_NAME";
	private static final String TAG2_NAME = "@TAG2_NAME";
	private static final String XPathSubmenuItem = XPathCapa + "/a[text()='" + TAG_NAME + "' or text()='" + TAG2_NAME + "']";
	
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
		return PageObjTM.state(State.Visible, By.xpath(xpath), driver).wait(1).check();
	}
	
	public static boolean isMenuSelected(String nameMenu, WebDriver driver) {
		if (isVisibleSubmenu(nameMenu, driver)) {
			String xpath = getXPathMenu(nameMenu);
			//TODO solicitar data-testid
			return driver.findElement(By.xpath(xpath)).getAttribute("className").contains("SwwoF");
		}
		return false;
	}
	
	public static void clickSubmenu(String nameMenu, WebDriver driver) {
		String xpath = getXPathMenu(nameMenu);
		PageObjTM.click(By.xpath(xpath), driver).exec();
	}
}

package com.mng.robotest.test80.mango.test.pageobject.shop.miscompras;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.conf.Channel;

public class PageMisComprasMobil extends PageMisCompras {

	static String XPathCapaContenedora = "//div[@id='myPurchases']";
	
	private PageMisComprasMobil(Channel channel, WebDriver driver) {
		super(channel, driver);
	}
	public static PageMisComprasMobil getNew(Channel channel, WebDriver driver) {
		return new PageMisComprasMobil(channel, driver);
	}
	
	@Override
	public boolean isPageUntil(int maxSeconds) {
		return (isElementVisibleUntil(driver, By.xpath(XPathCapaContenedora), maxSeconds));
	}
	
}

package com.mng.robotest.test80.mango.test.pageobject.shop.miscompras;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.miscompras.PageMisCompras.TypeTicket;

public class PageMisComprasMobil extends PageMisCompras {

	private static String XPathCapaContenedora = "//div[@id='myPurchases']";
	
	public PageMisComprasMobil(WebDriver driver) {
		super(Channel.mobile, driver);
	}
	
	@Override
	public boolean isPageUntil(int maxSeconds) {
		return (state(Visible, By.xpath(XPathCapaContenedora)).wait(maxSeconds).check());
	}
}

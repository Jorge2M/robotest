package com.mng.robotest.test80.mango.test.pageobject.shop.galeria;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecCrossSelling {
	
	private final Channel channel;
	private final WebDriver driver;
	
	private static String XPathSectionMovil = "//section[@class='cross-selling']";
	private static String XPathSectionDesktop = "//section[@id='crossSelling']";

	public SecCrossSelling(Channel channel, WebDriver driver) {
		this.channel = channel;
		this.driver = driver;
	}
	
	private String getXPathSection() {
		switch (channel) {
		case desktop:
			return XPathSectionDesktop;
		default:
			return XPathSectionMovil;
		}
	}
	
	public String getXPath_link(int numLink) {
		return ("(" + getXPathSection() + "/a)[" + numLink + "]");
	}

	public boolean isSectionVisible() {
		return (state(Visible, By.xpath(getXPathSection()), driver).check());
	}

	/**
	 * @return si un link del cross-selling está asociado a un determinado menú (validamos que coincida el texto y el href) 
	 */
	public boolean linkAssociatedToMenu(int numLink, String litMenu, String hrefMenu) {
		String xpathLink = getXPath_link(numLink);
		WebElement link = getElementVisible(driver, By.xpath(xpathLink));
		return (
			link!=null &&
			link.getAttribute("innerHTML").compareTo(litMenu)==0 &&
			link.getAttribute("href").compareTo(hrefMenu)==0);
	}
}

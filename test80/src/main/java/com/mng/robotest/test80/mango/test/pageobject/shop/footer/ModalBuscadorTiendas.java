package com.mng.robotest.test80.mango.test.pageobject.shop.footer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

public class ModalBuscadorTiendas extends WebdrvWrapp implements PageFromFooter {
	
	final String XPathForIdPage = "//div[@class='search']/h1[text()[contains(.,'Encuentra tu tienda')]]";
	
	final String XPathForIdPageMobile = "//p[@class='header-title' and text()[contains(.,'Encuentra tu tienda')]]";
	
	Channel channel = null;
	
	public ModalBuscadorTiendas (Channel channel) {
		this.channel = channel;
	}
	
	@Override
	public String getName() {
		return "Encuentra tu tienda";
	}
	
	@Override
	public boolean isPageCorrect(WebDriver driver) {
		if (channel==Channel.movil_web)
			return (isElementPresent(driver, By.xpath(XPathForIdPageMobile)));
		return (isElementPresent(driver, By.xpath(XPathForIdPage)));
	}
}

package com.mng.robotest.test80.mango.test.pageobject.shop.footer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalBuscadorTiendas extends PageObjTM implements PageFromFooter {
	
	final String XPathForIdPage = "//div[@class='search']/h1[text()[contains(.,'Encuentra tu tienda')]]";
	final String XPathForIdPageMobile = "//p[@class='header-title' and text()[contains(.,'Encuentra tu tienda')]]";
	
	Channel channel = null;
	
	public ModalBuscadorTiendas (Channel channel, WebDriver driver) {
		super(driver);
		this.channel = channel;
	}
	
	@Override
	public String getName() {
		return "Encuentra tu tienda";
	}
	
	@Override
	public boolean isPageCorrectUntil(int maxSeconds) {
		if (channel==Channel.movil_web) {
			return (state(Present, By.xpath(XPathForIdPageMobile), driver)
					.wait(maxSeconds).check());
		}
		return (state(Present, By.xpath(XPathForIdPage), driver)
				.wait(maxSeconds).check());
	}
}

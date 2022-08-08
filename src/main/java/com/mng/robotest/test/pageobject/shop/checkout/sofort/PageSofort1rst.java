package com.mng.robotest.test.pageobject.shop.checkout.sofort;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageSofort1rst extends PageBase {
	
	private final Channel channel;
	
	private static final String xPathFigurasButtonDesktop = "//input[@class[contains(.,'paySubmit')]]";
	private static final String xPathIconoSofort = "//input[@name='brandName' and @type='submit']";
	
	public PageSofort1rst(Channel channel, WebDriver driver) {
		super(driver);
		this.channel = channel;
	}
	
	private String getXPathClickToFollow(Channel channel) {
		if (channel.isDevice()) {
			return xPathIconoSofort;
		} else {
			return xPathFigurasButtonDesktop;
		}
	}
	
	public boolean isPageVisibleUntil(int maxSeconds) {
		String xpPathClickFollowing = getXPathClickToFollow(channel);
		return (state(Visible, By.xpath(xpPathClickFollowing)).wait(maxSeconds).check());
	}

	public void clickGoToSofort() {
		String xpPathClickFollowing = getXPathClickToFollow(channel);
		click(By.xpath(xpPathClickFollowing)).exec();
	}
}

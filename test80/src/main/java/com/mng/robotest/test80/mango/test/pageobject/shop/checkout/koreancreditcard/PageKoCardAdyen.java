package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class PageKoCardAdyen extends PageObjTM {
	
    private final static String XPathIconKoreanCreditCard = "//input[@name='brandName']";
    private final static String XPathGreenButton = "//input[@name='pay']";
    
    public PageKoCardAdyen(WebDriver driver) {
    	super(driver);
    }
    
    public boolean isPage() {
        return (isIconVisible());
    }

	public boolean isIconVisible() {
		return (state(Visible, By.xpath(XPathIconKoreanCreditCard), driver).check());
	}

	public void clickForContinue(Channel channel) {
		switch (channel) {
		case movil_web:
			clickIcon();
			break;
		case desktop:
			clickGreenButton();
		}
	}
	
	private void clickIcon() {
		click(By.xpath(XPathIconKoreanCreditCard)).exec();
	}
	
	private void clickGreenButton() {
		click(By.xpath(XPathGreenButton)).exec();
	}
}

package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;
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

	public void clickForContinue(Channel channel) throws Exception {
		switch (channel) {
		case movil_web:
			clickIcon();
			break;
		case desktop:
			clickGreenButton();
		}
	}
	
	private void clickIcon() throws Exception {
		clickAndWaitLoad(driver, By.xpath(XPathIconKoreanCreditCard));
	}
	
	private void clickGreenButton() throws Exception {
		clickAndWaitLoad(driver, By.xpath(XPathGreenButton));
	}
}

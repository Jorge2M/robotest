package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paysecureqiwi;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Present;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;

public class PagePaysecureQiwi1rst extends PageObjTM {
	
	private final boolean isPro;
	
	public enum PaysecureGateway { 
		Card("card", "100:1"), 
		Qiwi("qiwi", "36:2"), 
		WebMoney("wm", "30:2"), 
		Яндекс("ym", "32:2");
		
		private String XPathIconoPro = "//div[@class[contains(.,'select-card')]]"; 
		private String XPathIconoTest = "//input[@name='isSET']";
		private String code;
		private String value;
		private PaysecureGateway(String code, String value) {
			this.code = code;
			this.value = value;
		}
		public By getBy(boolean isPro) {
			if (isPro) {
				return By.xpath(XPathIconoPro + "//self::*[@class[contains(.,'" + code + "-payment')]]");
			}
			return By.xpath(XPathIconoTest + "//self::*[@value='" + value + "']");
		}
	}
	
	public PagePaysecureQiwi1rst(AppEcom app, WebDriver driver) {
		super(driver);
		isPro = UtilsMangoTest.isEntornoPRO(app, driver);
	}
	
	public boolean isPage() {
		return (state(Visible, PaysecureGateway.Qiwi.getBy(isPro)).check());
	}

	public boolean isPresentIcon(PaysecureGateway gateway) {
		return (state(Present, gateway.getBy(isPro)).check());
	}

	public void clickIcon(PaysecureGateway gateway) {
		click(gateway.getBy(isPro)).exec();
	}
}

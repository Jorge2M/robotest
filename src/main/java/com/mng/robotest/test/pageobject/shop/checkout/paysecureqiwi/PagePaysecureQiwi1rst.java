package com.mng.robotest.test.pageobject.shop.checkout.paysecureqiwi;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Present;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;

import org.openqa.selenium.By;

import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.generic.UtilsMangoTest;

public class PagePaysecureQiwi1rst extends PageBase {
	
	public enum PaysecureGateway { 
		Card("card", "100:1"), 
		Qiwi("qiwi", "36:2"), 
		WebMoney("wm", "30:2"), 
		Yandex("ym", "32:2"); //Яндекс
		
		private String xpathIconoPro = "//div[@class[contains(.,'select-card')]]"; 
		private String xpathIconoTest = "//input[@name='isSET']";
		private String code;
		private String value;
		private PaysecureGateway(String code, String value) {
			this.code = code;
			this.value = value;
		}
		public By getBy(boolean isPro) {
			if (isPro) {
				return By.xpath(xpathIconoPro + "//self::*[@class[contains(.,'" + code + "-payment')]]");
			}
			return By.xpath(xpathIconoTest + "//self::*[@value='" + value + "']");
		}
	}
	
	public boolean isPage() {
		return state(Visible, PaysecureGateway.Qiwi.getBy(isPRO())).check();
	}

	public boolean isPresentIcon(PaysecureGateway gateway) {
		return state(Present, gateway.getBy(isPRO())).check();
	}

	public void clickIcon(PaysecureGateway gateway) {
		click(gateway.getBy(isPRO())).exec();
	}
}

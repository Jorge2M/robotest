package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageKoCardINIpay2Mobil {
	
	public enum BodyPageKoCardINIpay2 implements ElementPage {
		inputEmail("//input[@type='email' and @name='email']"),
		nextButton("//span[@id='cardNext2Btn']");

		private By by;
		BodyPageKoCardINIpay2 (String xPath) {
			by = By.xpath(xPath);
		}

		@Override
		public By getBy() {
			return by;
		}
	}

	public static boolean isPage(WebDriver driver) {
		return (state(Visible, BodyPageKoCardINIpay2.inputEmail.getBy(), driver).wait(0).check());
	}
}
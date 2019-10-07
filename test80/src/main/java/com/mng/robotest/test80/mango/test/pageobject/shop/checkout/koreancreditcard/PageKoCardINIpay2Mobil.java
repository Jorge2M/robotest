package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.wrapper.ElementPage;
import com.mng.testmaker.service.webdriver.wrapper.ElementPageFunctions;

public class PageKoCardINIpay2Mobil extends ElementPageFunctions {
	
    public enum BodyPageKoCardINIpay2 implements ElementPage {
        inputEmail("//input[@type='email' and @name='email']"),
    	nextButton("//span[@id='cardNext2Btn']");

        private String xPath;
        BodyPageKoCardINIpay2 (String xPath) {
            this.xPath = xPath;
        }

        @Override
        public String getXPath() {
            return this.xPath;
        }
    }
    
    public static boolean isPage(WebDriver driver) {
    	return (isElementInStateUntil(BodyPageKoCardINIpay2.inputEmail, StateElem.Visible, 0, driver));
    }
}
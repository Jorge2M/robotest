package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.pageobject.ElementPage;
import com.mng.testmaker.service.webdriver.pageobject.ElementPageFunctions;

import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageKoCardINIpay3Mobil extends ElementPageFunctions {
	
    public enum BodyPageKoCardINIpay3 implements ElementPage {
    	buttonInstallISP("//div[@class[contains(.,'btn')]]//div[text()[contains(.,'모바일 ISP 설치하기')]]"),
        nextButton("//span[@id='cardNext2Btn']");
    	
        private String xPath;
        BodyPageKoCardINIpay3 (String xPath) {
            this.xPath = xPath;
        }

        @Override
        public String getXPath() {
            return this.xPath;
        }
    }
    
    public static boolean isPage(WebDriver driver) {
    	return (isElementInStateUntil(BodyPageKoCardINIpay3.buttonInstallISP, Visible, 0, driver));
    }
}

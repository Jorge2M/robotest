package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.webdriverwrapper.ElementPage;
import com.mng.robotest.test80.arq.webdriverwrapper.ElementPageFunctions;

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
    	return (isElementInStateUntil(BodyPageKoCardINIpay3.buttonInstallISP, StateElem.Visible, 0, driver));
    }
}

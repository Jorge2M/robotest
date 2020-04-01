package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.pageobject.ElementPage;

import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageKoCardINIpay3Mobil {
	
    public enum BodyPageKoCardINIpay3 implements ElementPage {
    	buttonInstallISP("//div[@class[contains(.,'btn')]]//div[text()[contains(.,'모바일 ISP 설치하기')]]"),
        nextButton("//span[@id='cardNext2Btn']");
    	
        private By by;
        BodyPageKoCardINIpay3 (String xPath) {
            by = By.xpath(xPath);
        }

        @Override
        public By getBy() {
            return by;
        }
    }
    
    public static boolean isPage(WebDriver driver) {
    	return (state(Visible, BodyPageKoCardINIpay3.buttonInstallISP.getBy(), driver).wait(0).check());
    }
}

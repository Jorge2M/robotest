package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.ElementPage;
import com.mng.robotest.test80.mango.test.pageobject.ElementPageFunctions;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

public class PageKoCardINIpay1Mobil extends ElementPageFunctions {
	private final static String tagTitleButtonTypeCard = "@TagLitTypeCard";
    public enum BodyPageKoCardINIpay1 implements ElementPage {
        terms("//label[@for='allAgree']"),
        termsTitle("//div[@id='term1Title']"),
        samsungpay("//div[@class='simplePaySec_ul_bt' and text()[contains(.,'SAMSUNGPAY')]]"),
        typecardbutton_withtag("//li[text()[contains(.,'" + tagTitleButtonTypeCard + "')]]"),
    	submitButton("//input[@type='submit']");

        private String xPath;
        BodyPageKoCardINIpay1 (String xPath) {
            this.xPath = xPath;
        }

        @Override
        public String getXPath() {
            return this.xPath;
        }
        
        public static void clickTypeCardButton(String litButton, WebDriver driver) throws Exception {
        	String xpatyButtonTypeCard = typecardbutton_withtag.getXPath().replace(tagTitleButtonTypeCard, litButton);
        	WebdrvWrapp.clickAndWaitLoad(driver, By.xpath(xpatyButtonTypeCard));
        	
        }
    }
    
    public static boolean isPage(WebDriver driver) {
    	return (driver.getTitle().contains("INIpay"));
    }
}

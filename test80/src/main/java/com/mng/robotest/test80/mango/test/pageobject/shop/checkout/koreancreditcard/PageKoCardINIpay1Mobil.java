package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.pageobject.ElementPage;
import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;


public class PageKoCardINIpay1Mobil {
	
	private final static String tagTitleButtonTypeCard = "@TagLitTypeCard";
    public enum BodyPageKoCardINIpay1 implements ElementPage {
        terms("//label[@for='allAgree']"),
        termsTitle("//div[@id='term1Title']"),
        samsungpay("//div[@class='simplePaySec_ul_bt' and text()[contains(.,'SAMSUNGPAY')]]"),
        typecardbutton_withtag("//li[text()[contains(.,'" + tagTitleButtonTypeCard + "')]]"),
    	submitButton("//input[@type='submit']");

        private By by;
        private String xpath;
        BodyPageKoCardINIpay1 (String xPath) {
        	xpath = xPath;
            by = By.xpath(xPath);
        }

        @Override
        public By getBy() {
            return by;
        }
        public String getXPath() {
        	return xpath;
        }

        public static void clickTypeCardButton(String litButton, WebDriver driver) {
        	String xpatyButtonTypeCard = typecardbutton_withtag.getXPath().replace(tagTitleButtonTypeCard, litButton);
        	click(By.xpath(xpatyButtonTypeCard), driver).exec();
        }
    }
    
    public static boolean isPage(WebDriver driver) {
    	return (driver.getTitle().contains("INIpay"));
    }
}

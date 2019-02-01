package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.ElementPage;
import com.mng.robotest.test80.mango.test.pageobject.ElementPageFunctions;

public class PageKoCardINIpay1Mobil extends ElementPageFunctions {
    public enum KsMobile implements ElementPage {
        //1rs part
        terms("//label[@for='allAgree']"),
        termsTitle("div[@id='term1Title']"),
        samsungpay("//div[@class='simplePaySec_ul_bt' and text()[contains(.,'SAMSUNGPAY')]"),
        type("//li[text()[contains(.,'케이뱅크')]]"),

        //2nd part
        iniMon("//select[@id='intMon']"),//validation

        //3rd part
        mainButton("//div[@class[contains(.,'btn_main')]]"),

        //4rd part
        infoPayment("//li//p[@class='cont_text']"),

        //shared
        previousButton("//span[@class='btn_left']"),
        nextButton("//span[@id='cardNext2Btn']"),
    	
    	//Common submit Button
    	submitButton("//input[@type='submit']");

        private String xPath;

        KsMobile (String xPath) {
            this.xPath = xPath;
        }

        @Override
        public String getXPath() {
            return this.xPath;
        }

        @Override
        public String getXPath(Channel channel) {
            return this.xPath;
        }
    }
    
    public static boolean isPage(WebDriver driver) {
    	return (driver.getTitle().contains("INIpay"));
    }
}

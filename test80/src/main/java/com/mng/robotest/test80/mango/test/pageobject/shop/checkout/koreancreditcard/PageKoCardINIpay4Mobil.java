package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard;

import org.openqa.selenium.WebDriver;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.testmaker.service.webdriver.wrapper.ElementPage;
import com.mng.testmaker.service.webdriver.wrapper.ElementPageFunctions;

public class PageKoCardINIpay4Mobil extends ElementPageFunctions {
	
	public final static String textoPagoConExitoKR = "모바일 ISP 앱에서 결제를 정상적으로 진행하셨다면";
	
    public enum BodyPageKoCardINIpay4 implements ElementPage {
        nextButton("//span[@id='cardNext2Btn']"),
    	messagePagoOk("//p[text()[contains(.,'" + textoPagoConExitoKR + "')]]");
    	
        private String xPath;
        BodyPageKoCardINIpay4 (String xPath) {
            this.xPath = xPath;
        }

        @Override
        public String getXPath() {
            return this.xPath;
        }
    }
    
    public static boolean isPage(WebDriver driver) {
    	return (isElementInStateUntil(BodyPageKoCardINIpay4.nextButton, Visible, 0, driver));
    }
    
    public static boolean isVisibleMessagePaymentOk(WebDriver driver) {
    	return (isElementInStateUntil(BodyPageKoCardINIpay4.messagePagoOk, Visible, 0, driver));
    }
}

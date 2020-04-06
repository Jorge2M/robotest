package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageKoCardINIpay4Mobil {
	
	public final static String textoPagoConExitoKR = "모바일 ISP 앱에서 결제를 정상적으로 진행하셨다면";
	
    public enum BodyPageKoCardINIpay4 implements ElementPage {
        nextButton("//span[@id='cardNext2Btn']"),
    	messagePagoOk("//p[text()[contains(.,'" + textoPagoConExitoKR + "')]]");
    	
        private By by;
        BodyPageKoCardINIpay4 (String xPath) {
            by = By.xpath(xPath);
        }

        @Override
        public By getBy() {
            return by;
        }
    }
    
    public static boolean isPage(WebDriver driver) {
    	return (state(Visible, BodyPageKoCardINIpay4.nextButton.getBy(), driver).wait(0).check());
    }
    
    public static boolean isVisibleMessagePaymentOk(WebDriver driver) {
    	return (state(Visible, BodyPageKoCardINIpay4.messagePagoOk.getBy(), driver).wait(0).check());
    }
}

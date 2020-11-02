package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.kcp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

public abstract class ModalHundayCard extends PageObjTM {

	private static final String XPathIframeModal = 	"//iframe[@id='CARD_CERT_IFR']";
	
	public ModalHundayCard(WebDriver driver) {
		super(driver);
	}
	
    protected void gotoIframeModal() {
    	PageKcpMain.goToIframe(driver);
    	driver.switchTo().frame(driver.findElement(By.xpath(XPathIframeModal)));
    }
    
    protected void leaveIframe() {
    	driver.switchTo().defaultContent();
    }
}

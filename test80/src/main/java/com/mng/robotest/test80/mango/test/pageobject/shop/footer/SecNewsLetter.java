package com.mng.robotest.test80.mango.test.pageobject.shop.footer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.pageobject.shop.modales.ModalClubMangoLikes;

public class SecNewsLetter {

	private final WebDriver driver;
	private final AppEcom app;
	
    private final static String XPathCapaNewsLetterShop = "//div[@id[contains(.,'newsletterSubscriptionFooter')]]";
    private final static String XPathCapaNewsLetterOutlet = "//div[@id[contains(.,'panelFooterRegistroNewsletter')]]";
    
    private final static String XPathNewsLetterMsgShop = XPathCapaNewsLetterShop + "//p[@class[contains(.,'sg-text-action')]]";
    private final static String XPathNewsLetterMsgOutlet = XPathCapaNewsLetterOutlet + "//span[@class='newsletterForm__text']";
    
    private final static String XPathTextAreaMailSuscripcionShop = XPathCapaNewsLetterShop + "//input[@name='mail']";
    private final static String XPathTextAreaMailSuscripcionOutlet = XPathCapaNewsLetterOutlet + "//input[@class[contains(.,'regExpMail')]]";
	
    public SecNewsLetter(AppEcom app, WebDriver driver) {
    	this.driver = driver;
    	this.app = app;
    }
    
    private String getXPathNewsLetterMsgShop() {
        if (app==AppEcom.outlet) {
            return XPathNewsLetterMsgShop;
        }
        return XPathNewsLetterMsgOutlet;
    }
    
    private String getXPathTextAreaMailSuscripcion() {
        if (app==AppEcom.outlet) {
            return XPathTextAreaMailSuscripcionOutlet;
        }
        return XPathTextAreaMailSuscripcionShop;
    }
    
    public String getNewsLetterMsgText() {
    	By byMsg = By.xpath(getXPathNewsLetterMsgShop());
        try {
            WebElement titleNws = driver.findElement(byMsg);
            if (titleNws!=null) {
                return driver.findElement(byMsg).getText();
            }
        }
        catch (Exception e) {
            //Retornamos ""
        }
        return "";
    }
    
    public boolean newsLetterMsgContains(String literal) {
        return (getNewsLetterMsgText().contains(literal));
    }

	public void clickFooterSuscripcion() throws Exception {
		ModalClubMangoLikes.closeModalIfVisible(driver);
		String xpath = getXPathTextAreaMailSuscripcion();
		driver.findElement(By.xpath(xpath)).click();
	}

}

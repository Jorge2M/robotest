package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.mercadopago;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageMercpagoDatosTrjMobil extends PageMercpagoDatosTrj {

    static String XPathWrapperVisa = "//div[@class='ui-card__wrapper']";
    static String XPathWrapperVisaUnactive = XPathWrapperVisa + "//div[@class='ui-card']";
    static String XPathWrapperVisaActive = XPathWrapperVisa + "//div[@class[contains(.,'ui-card__brand-debvisa')]]";
    static String XPathNextButton = "//button[@id[contains(.,'next')]]";
    static String XPathBackButton = "//button[@id[contains(.,'back')]]";
    static String XPathButtonNextPay = "//button[@id='submit' and @class[contains(.,'submit-arrow')]]";
    
    private PageMercpagoDatosTrjMobil(WebDriver driver) {
    	super(driver);
    }
    
    public static PageMercpagoDatosTrjMobil newInstance(WebDriver driver) {
    	return (new PageMercpagoDatosTrjMobil(driver));
    }

    @Override
    public boolean isPageUntil(int maxSeconds) {
    	return (state(Visible, By.xpath(XPathInputCvc), driver)
    			.wait(maxSeconds).check());
    }
    
    @Override
    public void sendCaducidadTarj(String fechaVencimiento) {
        int i=0;
        while (!state(Clickable, By.xpath(XPathInputFecCaducidad), driver).wait(1).check() && i<3) {
            clickNextButton();
            i+=1;
        }
        
        driver.findElement(By.xpath(XPathInputFecCaducidad)).sendKeys(fechaVencimiento);
    }
    
    @Override
    public void sendCvc(String securityCode) {
        int i=0;
        while (!state(Clickable, By.xpath(XPathInputCvc), driver).wait(1).check() && i<3) {
            clickNextButton();
            i+=1;
        }
        
        driver.findElement(By.xpath(XPathInputCvc)).sendKeys(securityCode);        
    }
    
    public boolean isActiveWrapperVisaUntil(int maxSeconds) {
    	return (state(Visible, By.xpath(XPathWrapperVisaActive), driver)
    			.wait(maxSeconds).check());
    }
    
    public void clickNextButton() {
        driver.findElement(By.xpath(XPathNextButton)).click();
    }
    
    public void clickBackButton() {
        driver.findElement(By.xpath(XPathBackButton)).click();
    }    
    
    public boolean isClickableButtonNextPayUntil(int maxSeconds) {
    	return (state(Clickable, By.xpath(XPathButtonNextPay), driver)
    			.wait(maxSeconds).check());
    }

	public void clickButtonForPay() {
		By byElem = By.xpath(XPathButtonNextPay + " | " + XPathBotonPagar);
		click(byElem).exec();
	}
}

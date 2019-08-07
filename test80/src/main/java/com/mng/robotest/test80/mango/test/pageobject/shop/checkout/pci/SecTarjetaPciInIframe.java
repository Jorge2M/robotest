package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.pci;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;

public class SecTarjetaPciInIframe extends WebdrvWrapp implements SecTarjetaPci {

	final protected WebDriver driver;
	
	static String XPathIframe = "//iframe[@title='credit_card_form']";
    static String XPathBlock = "//div[@id='root']";
    static String XPathInputNumber = XPathBlock + "//input[@name[contains(.,'cardNumber')]]";
    static String XPathInputTitular = XPathBlock + "//input[@name[contains(.,'cardHolder')]]";
    static String XPathSelectMes = XPathBlock + "//select[@name[contains(.,'expirationMonth')]]";
    static String XPathSelectAny = XPathBlock + "//select[@name[contains(.,'expirationYear')]]";
    static String XPathInputCvc = XPathBlock + "//input[@name[contains(.,'cvc')]]";
    static String XPathInputDni = XPathBlock + "//input[@name[contains(.,'dni')]]"; //Specific for Codensa (Colombia)
    
    protected SecTarjetaPciInIframe(WebDriver driver) {
    	this.driver = driver;
    }
    
    public static SecTarjetaPciInIframe getNew(WebDriver driver) {
    	return (new SecTarjetaPciInIframe(driver));
    }
    
    protected void goToIframe() {
    	driver.switchTo().frame(driver.findElement(By.xpath(XPathIframe)));
    }
    
    protected void leaveIframe() {
    	driver.switchTo().defaultContent();
    }
    
    @Override
    public boolean isVisiblePanelPagoUntil(String nombrePago, int maxSeconds) {
        return true;
    }
    
    @Override
    public boolean isPresentInputNumberUntil(int maxSecondsToWait) {
    	goToIframe();
        boolean present = isElementPresentUntil(driver, By.xpath(XPathInputNumber), maxSecondsToWait);
        leaveIframe();
        return present;
    }
    
    @Override
    public boolean isPresentInputTitular() {
    	goToIframe();
        boolean present = isElementPresent(driver, By.xpath(XPathInputTitular));
        leaveIframe();
        return present;
    }
    
    @Override
    public boolean isPresentSelectMes() {
    	goToIframe();
        boolean present = isElementPresent(driver, By.xpath(XPathSelectMes));
        leaveIframe();
        return present;
    }
     
    @Override
    public boolean isPresentSelectAny() {
    	goToIframe();
        boolean present = isElementPresent(driver, By.xpath(XPathSelectAny));
        leaveIframe();
        return present;
    }
    
    @Override
    public boolean isPresentInputCvc() {
    	goToIframe();
        boolean present = isElementPresent(driver, By.xpath(XPathInputCvc));
        leaveIframe();
        return present;
    }
    
    @Override
    public boolean isPresentInputDni() {
    	goToIframe();
        boolean present = isElementPresent(driver, By.xpath(XPathInputDni));
        leaveIframe();
        return present;
    }    
    
    @Override
    public void inputNumber(String number) {
    	goToIframe();
    	WebElement input = driver.findElement(By.xpath(XPathInputNumber));
    	if (number.compareTo(input.getAttribute("value").replace(" ", ""))!=0) {
	        driver.findElement(By.xpath(XPathInputNumber)).clear();
	        driver.findElement(By.xpath(XPathInputNumber)).sendKeys(number);
    	}
        leaveIframe();
    }
    
    @Override
    public void inputTitular(String titular) {
    	goToIframe();
        driver.findElement(By.xpath(XPathInputTitular)).clear();
        driver.findElement(By.xpath(XPathInputTitular)).sendKeys(titular);
        leaveIframe();
    }
    
    @Override
    public void inputCvc(String cvc) {
    	goToIframe();
        driver.findElement(By.xpath(XPathInputCvc)).clear();
        driver.findElement(By.xpath(XPathInputCvc)).sendKeys(cvc);
        leaveIframe();
    }
    
    @Override
    public void inputDni(String dni) {
    	goToIframe();
        driver.findElement(By.xpath(XPathInputDni)).clear();
        driver.findElement(By.xpath(XPathInputDni)).sendKeys(dni);
        leaveIframe();
    }    
    
    @Override
    public void selectMesByVisibleText(String mes) {
    	goToIframe();
        new Select(driver.findElement(By.xpath(XPathSelectMes))).selectByVisibleText(mes);
        new Select(driver.findElement(By.xpath(XPathSelectMes))).selectByVisibleText(mes);
        leaveIframe();
    }
    
    @Override
    public void selectAnyByVisibleText(String any) {
    	goToIframe();
        new Select(driver.findElement(By.xpath(XPathSelectAny))).selectByVisibleText(any);
        new Select(driver.findElement(By.xpath(XPathSelectAny))).selectByVisibleText(any);
        leaveIframe();
    }
    
    public void inputCardNumberAndTab(String numTarj) {
    	goToIframe();
        driver.findElement(By.xpath(XPathInputNumber)).sendKeys(numTarj, Keys.TAB);
        leaveIframe();
    }
}
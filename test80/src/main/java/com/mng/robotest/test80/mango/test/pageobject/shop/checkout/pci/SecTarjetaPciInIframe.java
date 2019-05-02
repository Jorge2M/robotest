package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.pci;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

public class SecTarjetaPciInIframe extends WebdrvWrapp implements SecTarjetaPci {

	static String XPathIframe = "//iframe[@title='credit_card_form']";
    static String XPathBlock = "//div[@id='root']";
    static String XPathInputNumber = XPathBlock + "//input[@name[contains(.,'cardNumber')]]";
    static String XPathInputTitular = XPathBlock + "//input[@name[contains(.,'cardHolder')]]";
    static String XPathSelectMes = XPathBlock + "//select[@name[contains(.,'expirationMonth')]]";
    static String XPathSelectAny = XPathBlock + "//select[@name[contains(.,'expirationYear')]]";
    static String XPathInputCvc = XPathBlock + "//input[@name[contains(.,'cvc')]]";
    static String XPathInputDni = XPathBlock + "//input[@name[contains(.,'dni')]]"; //Specific for Codensa (Colombia)
    
    private SecTarjetaPciInIframe() {}
    
    public static SecTarjetaPciInIframe make() {
    	return (new SecTarjetaPciInIframe());
    }
    
    private void goToIframe(WebDriver driver) {
    	driver.switchTo().frame(driver.findElement(By.xpath(XPathIframe)));
    }
    
    private void leaveIframe(WebDriver driver) {
    	driver.switchTo().defaultContent();
    }
    
    @Override
    public boolean isVisiblePanelPagoUntil(String nombrePago, int maxSeconds, WebDriver driver) {
        return true;
    }
    
    @Override
    public boolean isPresentInputNumberUntil(int maxSecondsToWait, WebDriver driver) {
    	goToIframe(driver);
        boolean present = isElementPresentUntil(driver, By.xpath(XPathInputNumber), maxSecondsToWait);
        leaveIframe(driver);
        return present;
    }
    
    @Override
    public boolean isPresentInputTitular(WebDriver driver) {
    	goToIframe(driver);
        boolean present = isElementPresent(driver, By.xpath(XPathInputTitular));
        leaveIframe(driver);
        return present;
    }
    
    @Override
    public boolean isPresentSelectMes(WebDriver driver) {
    	goToIframe(driver);
        boolean present = isElementPresent(driver, By.xpath(XPathSelectMes));
        leaveIframe(driver);
        return present;
    }
     
    @Override
    public boolean isPresentSelectAny(WebDriver driver) {
    	goToIframe(driver);
        boolean present = isElementPresent(driver, By.xpath(XPathSelectAny));
        leaveIframe(driver);
        return present;
    }
    
    @Override
    public boolean isPresentInputCvc(WebDriver driver) {
    	goToIframe(driver);
        boolean present = isElementPresent(driver, By.xpath(XPathInputCvc));
        leaveIframe(driver);
        return present;
    }
    
    @Override
    public boolean isPresentInputDni(WebDriver driver) {
    	goToIframe(driver);
        boolean present = isElementPresent(driver, By.xpath(XPathInputDni));
        leaveIframe(driver);
        return present;
    }    
    
    @Override
    public void inputNumber(String number, WebDriver driver) {
    	goToIframe(driver);
        driver.findElement(By.xpath(XPathInputNumber)).clear();
        driver.findElement(By.xpath(XPathInputNumber)).sendKeys(number);
        leaveIframe(driver);
    }
    
    @Override
    public void inputTitular(String titular, WebDriver driver) {
    	goToIframe(driver);
        driver.findElement(By.xpath(XPathInputTitular)).clear();
        driver.findElement(By.xpath(XPathInputTitular)).sendKeys(titular);
        leaveIframe(driver);
    }
    
    @Override
    public void inputCvc(String cvc, WebDriver driver) {
    	goToIframe(driver);
        driver.findElement(By.xpath(XPathInputCvc)).clear();
        driver.findElement(By.xpath(XPathInputCvc)).sendKeys(cvc);
        leaveIframe(driver);
    }
    
    @Override
    public void inputDni(String dni, WebDriver driver) {
    	goToIframe(driver);
        driver.findElement(By.xpath(XPathInputDni)).clear();
        driver.findElement(By.xpath(XPathInputDni)).sendKeys(dni);
        leaveIframe(driver);
    }    
    
    @Override
    public void selectMesByVisibleText(String mes, WebDriver driver) {
    	goToIframe(driver);
        new Select(driver.findElement(By.xpath(XPathSelectMes))).selectByVisibleText(mes);
        new Select(driver.findElement(By.xpath(XPathSelectMes))).selectByVisibleText(mes);
        leaveIframe(driver);
    }
    
    @Override
    public void selectAnyByVisibleText(String any, WebDriver driver) {
    	goToIframe(driver);
        new Select(driver.findElement(By.xpath(XPathSelectAny))).selectByVisibleText(any);
        new Select(driver.findElement(By.xpath(XPathSelectAny))).selectByVisibleText(any);
        leaveIframe(driver);
    }
}
package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.pci;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

public class SecTarjetaPciNotInIframeMobil extends WebdrvWrapp implements SecTarjetaPci {

	private final WebDriver driver;
	
    static String XPathBlock = "//div[@data-form-card-content='form']";
    static String XPathInputNumber = XPathBlock + "//input[@id[contains(.,'number-card')] or @id[contains(.,'card-pci')]]";
    static String XPathInputTitular = XPathBlock + "//input[@id[contains(.,'card-holder')] or @id[contains(.,'holder-name-pci')]]";
    static String XPathSelectMes = XPathBlock + "//select[@id[contains(.,'month')]]";
    static String XPathSelectAny = XPathBlock + "//select[@id[contains(.,'year')]]";
    static String XPathInputCvc = XPathBlock + "//input[@id[contains(.,'cvc')]]";
    static String XPathInputDni = XPathBlock + "//input[@id[contains(.,'dni')]]"; //Specific for Codensa (Colombia)
    
    private SecTarjetaPciNotInIframeMobil(WebDriver driver) {
    	this.driver = driver;
    }
    
    public static SecTarjetaPciNotInIframeMobil getNew(WebDriver driver) {
    	return (new SecTarjetaPciNotInIframeMobil(driver));
    }
    
    @Override
    public boolean isVisiblePanelPagoUntil(String nombrePago, int maxSeconds) {
        return true;
    }
    
    @Override
    public boolean isPresentInputNumberUntil(int maxSecondsToWait) {
        return (isElementPresentUntil(driver, By.xpath(XPathInputNumber), maxSecondsToWait));
    }
    
    @Override
    public boolean isPresentInputTitular() {
        return (isElementPresent(driver, By.xpath(XPathInputTitular)));
    }
    
    @Override
    public boolean isPresentSelectMes() {
        return (isElementPresent(driver, By.xpath(XPathSelectMes)));
    }
     
    @Override
    public boolean isPresentSelectAny() {
        return (isElementPresent(driver, By.xpath(XPathSelectAny)));
    }
    
    @Override
    public boolean isPresentInputCvc() {
        return (isElementPresent(driver, By.xpath(XPathInputCvc)));
    }
    
    @Override
    public boolean isPresentInputDni() {
        return (isElementPresent(driver, By.xpath(XPathInputDni)));
    }    
    
    @Override
    public void inputNumber(String number) {
        driver.findElement(By.xpath(XPathInputNumber)).clear();
        driver.findElement(By.xpath(XPathInputNumber)).sendKeys(number);
    }
    
    @Override
    public void inputTitular(String titular) {
        driver.findElement(By.xpath(XPathInputTitular)).clear();
        driver.findElement(By.xpath(XPathInputTitular)).sendKeys(titular);
    }
    
    @Override
    public void inputCvc(String cvc) {
        driver.findElement(By.xpath(XPathInputCvc)).clear();
        driver.findElement(By.xpath(XPathInputCvc)).sendKeys(cvc);
    }
    
    @Override
    public void inputDni(String dni) {
        driver.findElement(By.xpath(XPathInputDni)).clear();
        driver.findElement(By.xpath(XPathInputDni)).sendKeys(dni);
    }    
    
    @Override
    public void selectMesByVisibleText(String mes) {
        new Select(driver.findElement(By.xpath(XPathSelectMes))).selectByVisibleText(mes);
    }
    
    @Override
    public void selectAnyByVisibleText(String any) {
        new Select(driver.findElement(By.xpath(XPathSelectAny))).selectByVisibleText(any);
    }
}
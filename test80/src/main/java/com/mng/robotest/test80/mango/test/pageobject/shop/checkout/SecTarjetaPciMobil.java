package com.mng.robotest.test80.mango.test.pageobject.shop.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

@SuppressWarnings("javadoc")
public class SecTarjetaPciMobil extends WebdrvWrapp implements SecTarjetaPci {

    static String XPathBlock = "//div[@data-form-card-content='form']";
    static String XPathInputNumber = XPathBlock + "//input[@id[contains(.,'number-card')] or @id[contains(.,'card-pci')]]";
    static String XPathInputTitular = XPathBlock + "//input[@id[contains(.,'card-holder')] or @id[contains(.,'holder-name-pci')]]";
    static String XPathSelectMes = XPathBlock + "//select[@id[contains(.,'month')]]";
    static String XPathSelectAny = XPathBlock + "//select[@id[contains(.,'year')]]";
    static String XPathInputCvc = XPathBlock + "//input[@id[contains(.,'cvc')]]";
    static String XPathInputDni = XPathBlock + "//input[@id[contains(.,'dni')]]"; //Specific for Codensa (Colombia)
    
    @Override
    public boolean isVisiblePanelPagoUntil(String nombrePago, int maxSeconds, WebDriver driver) {
        return true;
    }
    
    @Override
    public boolean isPresentInputNumberUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementPresentUntil(driver, By.xpath(XPathInputNumber), maxSecondsToWait));
    }
    
    @Override
    public boolean isPresentInputTitular(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathInputTitular)));
    }
    
    @Override
    public boolean isPresentSelectMes(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathSelectMes)));
    }
     
    @Override
    public boolean isPresentSelectAny(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathSelectAny)));
    }
    
    @Override
    public boolean isPresentInputCvc(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathInputCvc)));
    }
    
    @Override
    public boolean isPresentInputDni(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathInputDni)));
    }    
    
    @Override
    public void inputNumber(String number, WebDriver driver) {
        driver.findElement(By.xpath(XPathInputNumber)).clear();
        driver.findElement(By.xpath(XPathInputNumber)).sendKeys(number);
    }
    
    @Override
    public void inputTitular(String titular, WebDriver driver) {
        driver.findElement(By.xpath(XPathInputTitular)).clear();
        driver.findElement(By.xpath(XPathInputTitular)).sendKeys(titular);
    }
    
    @Override
    public void inputCvc(String cvc, WebDriver driver) {
        driver.findElement(By.xpath(XPathInputCvc)).clear();
        driver.findElement(By.xpath(XPathInputCvc)).sendKeys(cvc);
    }
    
    @Override
    public void inputDni(String dni, WebDriver driver) {
        driver.findElement(By.xpath(XPathInputDni)).clear();
        driver.findElement(By.xpath(XPathInputDni)).sendKeys(dni);
    }    
    
    @Override
    public void selectMesByVisibleText(String mes, WebDriver driver) {
        new Select(driver.findElement(By.xpath(XPathSelectMes))).selectByVisibleText(mes);
    }
    
    @Override
    public void selectAnyByVisibleText(String any, WebDriver driver) {
        new Select(driver.findElement(By.xpath(XPathSelectAny))).selectByVisibleText(any);
    }
}
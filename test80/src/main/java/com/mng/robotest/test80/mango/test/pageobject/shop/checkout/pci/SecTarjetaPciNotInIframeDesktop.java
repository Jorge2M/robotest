package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.pci;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;


public class SecTarjetaPciNotInIframeDesktop extends WebdrvWrapp implements SecTarjetaPci {

    static String XPathBlock = "//span[@id[contains(.,'panelTarjetasForm')]]";
    static String XPathInputNumber = XPathBlock + "//input[@id[contains(.,'cardNumber')] or @id[contains(.,'cardnumber')] or @id[contains(.,'msu_cardpan')]]";
    static String XPathInputTitular = XPathBlock + "//input[@data-encrypted-name[contains(.,'holderName')] or @class[contains(.,'holdername')] or @class[contains(.,'holderName')] or @class[contains(.,'msu_nameoncard')]]";
    static String XPathSelectMes = XPathBlock + "//select[@class[contains(.,'selectMonth')] or @class[contains(.,'select gmo_month')] or @class[contains(.,'select msu_month')]]";
    static String XPathSelectAny = XPathBlock + "//select[@class[contains(.,'selectYear')] or @class[contains(.,'select gmo_year')] or @class[contains(.,'select msu_year')]]";
    static String XPathInputCvc = XPathBlock + "//input[@class[contains(.,'CVCInput')] or @class[contains(.,'CWInput')]]";
    static String XPathInputDni = XPathBlock + "//input[@class[contains(.,'falcon_dni')]]"; //Specific for Codensa (Colombia)
    
    private SecTarjetaPciNotInIframeDesktop() {}
    public static SecTarjetaPciNotInIframeDesktop make() {
    	return (new SecTarjetaPciNotInIframeDesktop());
    }
    
    public static String getXPath_PanelPago(String nombrePago) {
        return (XPathBlock + "//*[@id[contains(.,'CardName')] and text()='" + nombrePago + "']");
    }
    
    @Override
    public boolean isVisiblePanelPagoUntil(String nombrePago, int maxSeconds, WebDriver driver) {
        String xpathPanelPago = getXPath_PanelPago(nombrePago);
        return (isElementVisibleUntil(driver, By.xpath(xpathPanelPago), maxSeconds));
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
        selectOption(By.xpath(XPathSelectMes), mes, OptionSelect.ByVisibleText, driver);
    }
    
    @Override
    public void selectAnyByVisibleText(String any, WebDriver driver) {
        selectOption(By.xpath(XPathSelectAny), any, OptionSelect.ByVisibleText, driver);
    }
}
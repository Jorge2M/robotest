package com.mng.robotest.test80.mango.test.pageobject.shop.checkout;

import java.util.StringTokenizer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;


public class SecKlarnaDeutsch extends WebdrvWrapp {

    static String XPathCapaKlarnaMobil = "//div[@class[contains(.,'klarna')] and @class[contains(.,'show')]]";
    static String XPathCapaKlarnaDesktop = "//div[@class[contains(.,'klarnaInput')]]";
    static String XPathDiaNacimiento = "//select[@id[contains(.,'birthDay')]]";
    static String XPathMesNacimiento = "//select[@id[contains(.,'birthMonth')]]";
    static String XPathAnyNacimiento = "//select[@id[contains(.,'birthYear')]]";
    static String XPathRadioAceptoDesktop = "//div[@class[contains(.,'klarnaTerms')]]//input[@type='checkbox']";
    static String XPathLinkRadioAceptoMobil = "//div[@class[contains(.,'klarnaTerms')]]";
    
    public static String getXPath_capaKlarna(Channel channel) {
        if (channel==Channel.movil_web) {
            return XPathCapaKlarnaMobil;
        }
        return XPathCapaKlarnaDesktop; 
    }
    
    public static String getXPath_linkRadioAcepto(Channel channel) {
        if (channel==Channel.movil_web) {
            return XPathLinkRadioAceptoMobil;
        }
        return XPathRadioAceptoDesktop;
    }
    
    public static boolean isVisibleUntil(Channel channel, int maxSecondsToWait, WebDriver driver) {
        return (isElementVisibleUntil(driver, By.xpath(getXPath_capaKlarna(channel)), maxSecondsToWait));
    }
    
    public static boolean isVisibleSelectDiaNacimientoUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementVisibleUntil(driver, By.xpath(XPathDiaNacimiento), maxSecondsToWait));
    }
    
    /**
     * @param fechaNaci en formato "dd-mm-aaaa"
     */
    public static void selectFechaNacimiento(String fechaNaci, WebDriver driver) {
        StringTokenizer fechaTokenizada = (new StringTokenizer(fechaNaci, "-"));
        moveToSelectDiaNacimiento(driver);
        selectDiaNacimiento(fechaTokenizada.nextToken(), driver);
        selectMesNacimiento(fechaTokenizada.nextToken(), driver);
        selectAnyNacimiento(fechaTokenizada.nextToken(), driver);
    }
    
    public static void moveToSelectDiaNacimiento(WebDriver driver) {
    	moveToElement(By.xpath(XPathDiaNacimiento), driver);
    }
    
    public static void selectDiaNacimiento(String value, WebDriver driver) {
        new Select(driver.findElement(By.xpath(XPathDiaNacimiento))).selectByValue(value);
    }
    
    public static void selectMesNacimiento(String value, WebDriver driver) {
        int mesInt = Integer.valueOf(value).intValue();
        new Select(driver.findElement(By.xpath(XPathMesNacimiento))).selectByValue(String.valueOf(mesInt));
    }    
    
    public static void selectAnyNacimiento(String value, WebDriver driver) {
        new Select(driver.findElement(By.xpath(XPathAnyNacimiento))).selectByValue(value);
    } 
    
    public static void clickAcepto(Channel channel, WebDriver driver) {
        moveToRadioAcepto(channel, driver);
        String xpathLinkRadio = getXPath_linkRadioAcepto(channel);
        driver.findElement(By.xpath(xpathLinkRadio)).click();
    }
    
    public static void moveToRadioAcepto(Channel channel, WebDriver driver) {
        String xpathLinkRadio = getXPath_linkRadioAcepto(channel);
        moveToElement(By.xpath(xpathLinkRadio), driver);
    }    
}
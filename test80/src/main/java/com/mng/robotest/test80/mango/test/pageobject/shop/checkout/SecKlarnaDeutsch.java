package com.mng.robotest.test80.mango.test.pageobject.shop.checkout;

import java.util.StringTokenizer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecKlarnaDeutsch extends PageObjTM {

	private final Channel channel;
	
    private final static String XPathCapaKlarnaMobil = "//div[@class[contains(.,'klarna')] and @class[contains(.,'show')]]";
    private final static String XPathCapaKlarnaDesktop = "//div[@class[contains(.,'klarnaInput')]]";
    private final static String XPathDiaNacimiento = "//select[@id[contains(.,'birthDay')]]";
    private final static String XPathMesNacimiento = "//select[@id[contains(.,'birthMonth')]]";
    private final static String XPathAnyNacimiento = "//select[@id[contains(.,'birthYear')]]";
    private final static String XPathRadioAceptoDesktop = "//div[@class[contains(.,'klarnaTerms')]]//input[@type='checkbox']";
    private final static String XPathLinkRadioAceptoMobil = "//div[@class[contains(.,'klarnaTerms')]]";
    
    public SecKlarnaDeutsch(Channel channel, WebDriver driver) {
    	super(driver);
    	this.channel = channel;
    }
    
    public String getXPath_capaKlarna() {
        if (channel.isDevice()) {
            return XPathCapaKlarnaMobil;
        }
        return XPathCapaKlarnaDesktop; 
    }
    
    public String getXPath_linkRadioAcepto() {
        if (channel.isDevice()) {
            return XPathLinkRadioAceptoMobil;
        }
        return XPathRadioAceptoDesktop;
    }

	public boolean isVisibleUntil(int maxSeconds) {
		String xpath = getXPath_capaKlarna();
		return (state(Visible, By.xpath(xpath)).wait(maxSeconds).check());
	}

	public boolean isVisibleSelectDiaNacimientoUntil(int maxSeconds) {
		return (state(Visible, By.xpath(XPathDiaNacimiento)).wait(maxSeconds).check());
	}

    /**
     * @param fechaNaci en formato "dd-mm-aaaa"
     */
    public void selectFechaNacimiento(String fechaNaci) {
        StringTokenizer fechaTokenizada = (new StringTokenizer(fechaNaci, "-"));
        moveToSelectDiaNacimiento();
        selectDiaNacimiento(fechaTokenizada.nextToken());
        selectMesNacimiento(fechaTokenizada.nextToken());
        selectAnyNacimiento(fechaTokenizada.nextToken());
    }
    
    public void moveToSelectDiaNacimiento() {
    	moveToElement(By.xpath(XPathDiaNacimiento), driver);
    }
    
    public void selectDiaNacimiento(String value) {
        new Select(driver.findElement(By.xpath(XPathDiaNacimiento))).selectByValue(value);
    }
    
    public void selectMesNacimiento(String value) {
        int mesInt = Integer.valueOf(value).intValue();
        new Select(driver.findElement(By.xpath(XPathMesNacimiento))).selectByValue(String.valueOf(mesInt));
    }    
    
    public void selectAnyNacimiento(String value) {
        new Select(driver.findElement(By.xpath(XPathAnyNacimiento))).selectByValue(value);
    } 
    
    public void clickAcepto() {
        moveToRadioAcepto();
        String xpathLinkRadio = getXPath_linkRadioAcepto();
        driver.findElement(By.xpath(xpathLinkRadio)).click();
    }
    
    public void moveToRadioAcepto() {
        String xpathLinkRadio = getXPath_linkRadioAcepto();
        moveToElement(By.xpath(xpathLinkRadio), driver);
    }    
}
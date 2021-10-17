package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.assist;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.test80.mango.test.beans.Pago;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


/**
 * Se trata de un objeto que intenta englobar todas las páginas de pasarelas diversas que permiten la introducción de datos de tarjetas
 * @author jorge.munoz
 *
 */
public class PageAssist1rst {

    static String XPathLogoAssistDesktop = "//div[@id[contains(.,'AssistLogo')]]";
    static String XPathLogoAssistMobil = "//div[@class='Logo']/img";
    static String XPathInputNumTrjDesktop = "//input[@id='CardNumber']";
    static String XPathInputMMCaducDesktop = "//input[@id='ExpireMonth']";
    static String XPathInputAACaducDesktop = "//input[@id='ExpireYear']";
    static String XPathInputNumTrjMovil = "//input[@id='CardNumber']";
    static String XPathSelectMMCaducMovil = "//select[@id='ExpireMonth']";
    static String XPathSelectAACaducMovil = "//select[@id='ExpireYear']";
    static String XPathInputTitular = "//input[@id='Cardholder']";
    static String XPathInputCvc = "//input[@id[contains(.,'CVC2')] or @id[contains(.,'psw_CVC2')]]";
    static String XPathBotonPagoDesktopAvailable = "//input[@class[contains(.,'button_pay')] and not(@disabled)]";
    static String XPathBotonPagoMovilAvailable = "//input[@type='Submit' and not(@disabled)]";
    static String XPathBotonPagoDesktop = "//input[@class[contains(.,'button_pay')]]";
    static String XPathBotonPagoMobil = "//input[@type='Submit' and not(@disabled)]";

    public static String getXPath_LogoAssist(Channel channel) {
        if (channel.isDevice()) {
            return XPathLogoAssistMobil; 
        }
        return XPathLogoAssistDesktop;
    }
    
    public static String getXPath_buttonPago(Channel channel) {
        if (channel.isDevice()) {
            return XPathBotonPagoMovilAvailable;
        }
        return XPathBotonPagoDesktopAvailable;
    }
    
    public static boolean isPresentLogoAssist(Channel channel, WebDriver driver) {
        String xpathLogo = getXPath_LogoAssist(channel);
        return (state(Present, By.xpath(xpathLogo), driver).check());
    }
    
    public static boolean isPresentInputsForTrjData(Channel channel, WebDriver driver) {
        boolean inputsOk = true;
        if (channel.isDevice()) {
        	if (!state(Present, By.xpath(XPathInputNumTrjMovil), driver).check() ||
        		!state(Present, By.xpath(XPathSelectMMCaducMovil), driver).check() ||
        		!state(Present, By.xpath(XPathSelectAACaducMovil), driver).check()) {
                inputsOk = false;
        	}
        } else {
        	if (!state(Present, By.xpath(XPathInputNumTrjDesktop), driver).check() ||
        		!state(Present, By.xpath(XPathInputMMCaducDesktop), driver).check() ||
        		!state(Present, By.xpath(XPathInputAACaducDesktop), driver).check()) {
        		inputsOk = false;
            }
        }
        
        if (!state(Present, By.xpath(XPathInputTitular), driver).check() ||
        	!state(Present, By.xpath(XPathInputCvc), driver).check()) {
            inputsOk = false;
        }
        
        return inputsOk;
    }
    
    public static void inputDataPagoAndWaitSubmitAvailable(Pago pago, Channel channel, WebDriver driver) throws Exception {
        //Input data
        if (channel.isDevice()) {
            driver.findElement(By.xpath(XPathInputNumTrjMovil)).sendKeys(pago.getNumtarj());
            new Select(driver.findElement(By.xpath(XPathSelectMMCaducMovil))).selectByValue(pago.getMescad());
            new Select(driver.findElement(By.xpath(XPathSelectAACaducMovil))).selectByValue("20" + pago.getAnycad()); //Atención con el efecto 2100!!!
        } else {
            driver.findElement(By.xpath(XPathInputNumTrjDesktop)).sendKeys(pago.getNumtarj());
            waitForPageLoaded(driver);
            driver.findElement(By.xpath(XPathInputMMCaducDesktop)).sendKeys(pago.getMescad());
            driver.findElement(By.xpath(XPathInputAACaducDesktop)).sendKeys(pago.getAnycad());
            waitForPageLoaded(driver);
        }
        
        driver.findElement(By.xpath(XPathInputTitular)).sendKeys(pago.getTitular());
        UtilsMangoTest.findDisplayedElement(driver, By.xpath(XPathInputCvc)).sendKeys(pago.getCvc());
        waitForPageLoaded(driver);
        
        //Wait for button available
        waitForBotonAvailable(channel, 1/*maxSecondsToWait*/, driver);
    }
    
	public static void clickBotonPago(Channel channel, WebDriver driver) {
		if (channel.isDevice()) {
			click(By.xpath(XPathBotonPagoMobil), driver).exec();
		} else {
			click(By.xpath(XPathBotonPagoDesktop), driver).exec();
		}
	}

    public static void waitForBotonAvailable(Channel channel, int maxSecondsToWait, WebDriver driver) {
        String xpathBoton = getXPath_buttonPago(channel);
        new WebDriverWait(driver, maxSecondsToWait).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathBoton)));
    }
    
    public static boolean invisibilityBotonPagoUntil(int maxSeconds, Channel channel, WebDriver driver) {
        String xpathBoton = getXPath_buttonPago(channel);
        return (state(Invisible, By.xpath(xpathBoton), driver)
        		.wait(maxSeconds).check());
     }
}


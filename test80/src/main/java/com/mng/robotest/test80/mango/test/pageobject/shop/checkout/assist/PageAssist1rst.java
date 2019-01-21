package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.assist;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

@SuppressWarnings("javadoc")
/**
 * Se trata de un objeto que intenta englobar todas las páginas de pasarelas diversas que permiten la introducción de datos de tarjetas
 * @author jorge.munoz
 *
 */
public class PageAssist1rst extends WebdrvWrapp {

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
        if (channel==Channel.movil_web)
            return XPathLogoAssistMobil; 
        
        return XPathLogoAssistDesktop;
    }
    
    public static String getXPath_buttonPago(Channel channel) {
        if (channel==Channel.movil_web)
            return XPathBotonPagoMovilAvailable;
        
        return XPathBotonPagoDesktopAvailable;
    }
    
    public static boolean isPresentLogoAssist(Channel channel, WebDriver driver) {
        String xpathLogo = getXPath_LogoAssist(channel);
        return (isElementPresent(driver, By.xpath(xpathLogo)));
    }
    
    public static boolean isPresentInputsForTrjData(Channel channel, WebDriver driver) {
        boolean inputsOk = true;
        if (channel==Channel.movil_web) {
            if (!isElementPresent(driver, By.xpath(XPathInputNumTrjMovil)) ||
                !isElementPresent(driver, By.xpath(XPathSelectMMCaducMovil)) ||
                !isElementPresent(driver, By.xpath(XPathSelectAACaducMovil)))
                inputsOk = false;
        }
        else {
            if (!isElementPresent(driver, By.xpath(XPathInputNumTrjDesktop)) ||
                !isElementPresent(driver, By.xpath(XPathInputMMCaducDesktop)) ||
                !isElementPresent(driver, By.xpath(XPathInputAACaducDesktop)))
                inputsOk = false;
        }
        
        if (!isElementPresent(driver, By.xpath(XPathInputTitular)) ||
            !isElementPresent(driver, By.xpath(XPathInputCvc)))
            inputsOk = false;
        
        return inputsOk;
    }
    
    public static void inputDataPagoAndWaitSubmitAvailable(Pago pago, Channel channel, WebDriver driver) throws Exception {
        //Input data
        if (channel==Channel.movil_web) {
            driver.findElement(By.xpath(XPathInputNumTrjMovil)).sendKeys(pago.getNumtarj());
            new Select(driver.findElement(By.xpath(XPathSelectMMCaducMovil))).selectByValue(pago.getMescad());
            new Select(driver.findElement(By.xpath(XPathSelectAACaducMovil))).selectByValue("20" + pago.getAnycad()); //Atención con el efecto 2100!!!
            
        }
        else {
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
    
    public static void clickBotonPago(Channel channel, WebDriver driver) throws Exception {
        if (channel==Channel.movil_web)
            clickAndWaitLoad(driver, By.xpath(XPathBotonPagoMobil));
        else
            clickAndWaitLoad(driver, By.xpath(XPathBotonPagoDesktop));
    }
    
    public static void waitForBotonAvailable(Channel channel, int maxSecondsToWait, WebDriver driver) {
        String xpathBoton = getXPath_buttonPago(channel);
        new WebDriverWait(driver, maxSecondsToWait).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathBoton)));
    }
    
    public static boolean invisibilityBotonPagoUntil(int maxSecondsWait, Channel channel, WebDriver driver) {
        String xpathBoton = getXPath_buttonPago(channel);
        return (isElementInvisibleUntil(driver, By.xpath(xpathBoton), maxSecondsWait)); 
     }
}


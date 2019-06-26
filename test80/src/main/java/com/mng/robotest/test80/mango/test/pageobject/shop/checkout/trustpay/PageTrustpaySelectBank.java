package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.trustpay;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;


public class PageTrustpaySelectBank extends WebdrvWrapp {
    
    static String XPathListOfPayments = "//ul[@id='paymentMethods']";
    static String XPathCabeceraStep = "//h2[@id[contains(.,'stageheader')]]";
    static String XPathInputIconoTrustpay = "//input[@type='submit' and @name='brandName']";
    static String XPathButtonPago = "//input[@type='submit']";
    static String XPathSelectBancos = "//select[@id='trustPayBankList']";
    static String XPathButtonPayDesktop = "//input[@class[contains(.,'paySubmittrustpay')]]";
    static String XPathButtonContinueMobil = "//input[@type='submit' and @value='continue']";
    
    public static String getXPathEntradaPago(String nombrePago, Channel channel) {
        if (channel==Channel.movil_web) {
            return (XPathListOfPayments + "/li/input[@class[contains(.,'" + nombrePago.toLowerCase() + "')]]");
        }
        return (XPathListOfPayments + "/li[@data-variant[contains(.,'" + nombrePago.toLowerCase() + "')]]");
    }
    
    public static boolean isPresentEntradaPago(String nombrePago, Channel channel, WebDriver driver) {
        String xpathPago = getXPathEntradaPago(nombrePago, channel);
        return (isElementPresent(driver, By.xpath(xpathPago)));
    }
    
    public static boolean isPresentCabeceraStep(String nombrePago, Channel channel, WebDriver driver) {
        String xpathCab = getXPathEntradaPago(nombrePago, channel);
        return (isElementPresent(driver, By.xpath(xpathCab)));
    }
    
    public static boolean isPresentButtonPago(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathButtonPago)));
    }
    
    public static boolean isPresentSelectBancos(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathSelectBancos))); 
    }
    
    public static void selectBankThatContains(ArrayList<String> strContains, Channel channel, WebDriver driver) throws Exception {
        //En el caso de móvil para que aparezca el desplegable se ha de seleccionar el icono del banco
        if (channel==Channel.movil_web) {
            if (!isElementVisible(driver, By.xpath(XPathSelectBancos)))
                clickIconoBanco(driver);
        }
        
        Select selectBank = new Select(driver.findElement(By.xpath(XPathSelectBancos)));
        List<WebElement> elements = selectBank.getOptions();
        WebElement elementFind = null;
        for (WebElement element : elements) {
            if (element.getAttribute("value")!=null &&
                stringContainsAnyValue(element.getText(), strContains)) {
                elementFind = element;
                break;
            }
        }
        
        if (elementFind!=null) {
            selectBank.selectByValue(elementFind.getAttribute("value"));
        }
    }
    
    private static boolean stringContainsAnyValue(String str, ArrayList<String> listOfValues) {
        for (String strValue : listOfValues) {
            if (str.contains(strValue)) {
                return true;
            }
        }
        
        return false;
    }
    
    public static void clickIconoBanco(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathInputIconoTrustpay));
    }
    
    public static void clickButtonToContinuePay(Channel channel, WebDriver driver) throws Exception {
        if (channel==Channel.movil_web) {
            clickAndWaitLoad(driver, By.xpath(XPathButtonContinueMobil));
        } else {
            clickAndWaitLoad(driver, By.xpath(XPathButtonPayDesktop));
        }
    }
}
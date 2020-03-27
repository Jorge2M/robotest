package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.dotpay;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.conf.Channel;
import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageDotpay1rst {
	
    static String XPathListOfPayments = "//ul[@id='paymentMethods']";
    static String XPathCabeceraStep = "//h2[@id[contains(.,'stageheader')]]";
    static String XPathInputIconoDotpay = "//input[@type='submit' and @name='brandName']";
    static String XPathButtonPago = "//input[@name='pay' and @type='submit']";
    
    public static String getXPathEntradaPago(String nombrePago, Channel channel) {
        if (channel==Channel.movil_web) {
            return (XPathListOfPayments + "/li/input[@class[contains(.,'" + nombrePago.toLowerCase() + "')]]");
        }
        return (XPathListOfPayments + "/li[@data-variant[contains(.,'" + nombrePago.toLowerCase() + "')]]");
    }
    
    public static boolean isPresentEntradaPago(String nombrePago, Channel channel, WebDriver driver) {
        String xpathPago = getXPathEntradaPago(nombrePago, channel);
        return (state(Present, By.xpath(xpathPago), driver).check());
    }
    
    public static boolean isPresentCabeceraStep(String nombrePago, Channel channel, WebDriver driver) {
        String xpathCab = getXPathEntradaPago(nombrePago, channel);
        return (state(Present, By.xpath(xpathCab), driver).check());
    }
    
    public static boolean isPresentButtonPago(WebDriver driver) {
    	return (state(Present, By.xpath(XPathButtonPago), driver).check());
    }
    
    public static void clickToPay(Channel channel, WebDriver driver) throws Exception {
        if (channel==Channel.movil_web) {
            clickAndWaitLoad(driver, By.xpath(XPathInputIconoDotpay));
        } else {
            clickAndWaitLoad(driver, By.xpath(XPathButtonPago));
        }
    }
}

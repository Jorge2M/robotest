package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.multibanco;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.utils.otras.Channel;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;


public class PageMultibanco1rst extends WebdrvWrapp {
	final static String TagEmail = "@TagEmail";
    final static String XPathListOfPayments = "//ul[@id='paymentMethods']";
    final static String XPathCabeceraStep = "//h2[@id[contains(.,'stageheader')]]";
    final static String XPathInputIconoMultibanco = "//input[@type='submit' and @name='brandName']";
    final static String XPathButtonPagoDesktop = "//input[@class[contains(.,'paySubmit')] and @type='submit']";
    final static String XPathButtonContinueMobil = "//input[@type='submit' and @value='continuar']";
    final static String XPathInputEmailWithTag = "//input[@id[contains(.,'multibanco')] and @value[contains(.,'" + TagEmail + "')]]";
    
    public static String getXPathEntradaPago(String nombrePago, Channel channel) {
        if (channel==Channel.movil_web) {
            return (XPathListOfPayments + "//input[@class[contains(.,'" + nombrePago.toLowerCase() + "')]]");
        }
        return (XPathListOfPayments + "/li[@data-variant[contains(.,'" + nombrePago.toLowerCase() + "')]]");
    }
    
    public static String getXPathButtonContinuePay(Channel channel) {
        if (channel==Channel.movil_web) {
            return XPathButtonContinueMobil;
        }
        return XPathButtonPagoDesktop;
    }
    
    public static String getXPathInputEmail(String email) {
    	return XPathInputEmailWithTag.replace(TagEmail, email);
    }
    
    public static boolean isPresentEntradaPago(String nombrePago, Channel channel, WebDriver driver) {
        String xpathPago = getXPathEntradaPago(nombrePago, channel);
        return (isElementPresent(driver, By.xpath(xpathPago)));
    }
    
    public static boolean isPresentCabeceraStep(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathCabeceraStep)));
    }
    
    public static boolean isPresentButtonPagoDesktop(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathButtonPagoDesktop)));
    }
    
    public static boolean isPresentEmailUsr(String emailUsr, WebDriver driver) {
        String xpathEmail = getXPathInputEmail(emailUsr);
        return (isElementPresent(driver, By.xpath(xpathEmail)));
    }

    public static void continueToNextPage(Channel channel, WebDriver driver) throws Exception {
        //En el caso de móvil hemos de seleccionar el icono de banco para visualizar el botón de continue
        if (channel==Channel.movil_web) {
            String xpathButton = getXPathButtonContinuePay(channel);
            if (!isElementVisible(driver, By.xpath(xpathButton))) {
                clickIconoBanco(driver);
            }
        }
        
        clickButtonContinuePay(channel, driver);
    }
    
    public static void clickIconoBanco(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathInputIconoMultibanco));
    }
    
    public static void clickButtonContinuePay(Channel channel, WebDriver driver) throws Exception {
        String xpathButton = getXPathButtonContinuePay(channel);
        clickAndWaitLoad(driver, By.xpath(xpathButton));
    }
    

}

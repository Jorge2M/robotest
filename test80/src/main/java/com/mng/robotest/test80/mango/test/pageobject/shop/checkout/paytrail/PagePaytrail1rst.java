package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paytrail;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.mng.testmaker.conf.Channel;
import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PagePaytrail1rst {
    static String XPathListOfPayments = "//ul[@id='paymentMethods']";
    static String XPathCabeceraStep = "//h2[@id[contains(.,'stageheader')]]";
    static String XPathInputIconoPaytrail = "//input[@type='submit' and @name='brandName']";    
    static String XPathButtonPagoDesktop = "//input[@class[contains(.,'paySubmit')] and @type='submit']";
    static String XPathButtonContinueMobil = "//input[@type='submit' and @name[contains(.,'ebanking')]]";
    static String XPathSelectBancos = "//select[@id[contains(.,'ebanking')]]";
    
    public static String getXPathEntradaPago(String nombrePago) {
        return (XPathListOfPayments + "//input[@value[contains(.,'" + nombrePago.toLowerCase() + "')] or @value[contains(.,'" + nombrePago + "')]]");
    }
    
    public static boolean isPresentEntradaPago(String nombrePago, WebDriver driver) {
        String xpathPago = getXPathEntradaPago(nombrePago);
        return (state(Present, By.xpath(xpathPago), driver).check());
    }
    
    public static boolean isPresentButtonPago(WebDriver driver) {
    	return (state(Present, By.xpath(XPathButtonPagoDesktop), driver).check());
    }

    public static boolean isPresentSelectBancos(WebDriver driver) {
    	return (state(Present, By.xpath(XPathSelectBancos), driver).check());
    }
    
    public static boolean isVisibleSelectBancosUntil(int maxSeconds, WebDriver driver) {
    	return (state(Visible, By.xpath(XPathSelectBancos), driver).wait(maxSeconds).check());
    }    
    
    public static void clickButtonContinue(Channel channel, WebDriver driver) throws Exception {
        if (channel==Channel.movil_web) {
            clickAndWaitLoad(driver, By.xpath(XPathButtonContinueMobil));
        } else {
            clickAndWaitLoad(driver, By.xpath(XPathButtonPagoDesktop));
        }
    }
    
    public static void selectBanco(String visibleText, Channel channel, WebDriver driver) throws Exception {
        //En el caso de móvil hemos de seleccionar el icono del banco para visualizar el desplegable
        if (channel==Channel.movil_web) {
        	if (state(Visible, By.xpath(XPathSelectBancos), driver).check()) {
                clickIconoBanco(driver);
            }
        }
            
        new Select(driver.findElement(By.xpath(XPathSelectBancos))).selectByVisibleText(visibleText);
    }
    
    public static void clickIconoBanco(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathInputIconoPaytrail));
    }    
}
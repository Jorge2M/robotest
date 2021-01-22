package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.multibanco;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageMultibanco1rst {
	
	final static String TagEmail = "@TagEmail";
    final static String XPathListOfPayments = "//ul[@id='paymentMethods']";
    final static String XPathCabeceraStep = "//h2[@id[contains(.,'stageheader')]]";
    final static String XPathInputIconoMultibanco = "//input[@type='submit' and @name='brandName']";
    final static String XPathButtonPagoDesktop = "//input[@class[contains(.,'paySubmit')] and @type='submit']";
    final static String XPathButtonContinueMobil = "//input[@type='submit' and @value='continuar']";
    final static String XPathInputEmailWithTag = "//input[@id[contains(.,'multibanco')] and @value[contains(.,'" + TagEmail + "')]]";
    
    public static String getXPathEntradaPago(String nombrePago, Channel channel) {
        if (channel.isDevice()) {
            return (XPathListOfPayments + "//input[@class[contains(.,'" + nombrePago.toLowerCase() + "')]]");
        }
        return (XPathListOfPayments + "/li[@data-variant[contains(.,'" + nombrePago.toLowerCase() + "')]]");
    }
    
    public static String getXPathButtonContinuePay(Channel channel) {
        if (channel.isDevice()) {
            return XPathButtonContinueMobil;
        }
        return XPathButtonPagoDesktop;
    }
    
    public static String getXPathInputEmail(String email) {
    	return XPathInputEmailWithTag.replace(TagEmail, email);
    }
    
    public static boolean isPresentEntradaPago(String nombrePago, Channel channel, WebDriver driver) {
        String xpathPago = getXPathEntradaPago(nombrePago, channel);
        return (state(Present, By.xpath(xpathPago), driver).check());
    }
    
    public static boolean isPresentCabeceraStep(WebDriver driver) {
    	return (state(Present, By.xpath(XPathCabeceraStep), driver).check());
    }
    
    public static boolean isPresentButtonPagoDesktop(WebDriver driver) {
    	return (state(Present, By.xpath(XPathButtonPagoDesktop), driver).check());
    }
    
    public static boolean isPresentEmailUsr(String emailUsr, WebDriver driver) {
        String xpathEmail = getXPathInputEmail(emailUsr);
        return (state(Present, By.xpath(xpathEmail), driver).check());
    }

    public static void continueToNextPage(Channel channel, WebDriver driver) {
        //En el caso de móvil hemos de seleccionar el icono de banco para visualizar el botón de continue
        if (channel.isDevice()) {
            String xpathButton = getXPathButtonContinuePay(channel);
            if (!state(Visible, By.xpath(xpathButton), driver).check()) {
                clickIconoBanco(driver);
            }
        }
        
        clickButtonContinuePay(channel, driver);
    }

	public static void clickIconoBanco(WebDriver driver) {
		click(By.xpath(XPathInputIconoMultibanco), driver).exec();
	}

	public static void clickButtonContinuePay(Channel channel, WebDriver driver) {
		String xpathButton = getXPathButtonContinuePay(channel);
		click(By.xpath(xpathButton), driver).exec();
	}

}

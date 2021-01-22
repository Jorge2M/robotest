package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.trustpay;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.github.jorge2m.testmaker.conf.Channel;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageTrustpaySelectBank {
    
    static String XPathListOfPayments = "//ul[@id='paymentMethods']";
    static String XPathCabeceraStep = "//h2[@id[contains(.,'stageheader')]]";
    static String XPathInputIconoTrustpay = "//input[@type='submit' and @name='brandName']";
    static String XPathButtonPago = "//input[@type='submit']";
    static String XPathSelectBancos = "//select[@id='trustPayBankList']";
    static String XPathButtonPayDesktop = "//input[@class[contains(.,'paySubmittrustpay')]]";
    static String XPathButtonContinueMobil = "//input[@type='submit' and @value='continue']";
    
    public static String getXPathEntradaPago(String nombrePago, Channel channel) {
        if (channel.isDevice()) {
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
    
    public static boolean isPresentSelectBancos(WebDriver driver) {
    	return (state(Present, By.xpath(XPathSelectBancos), driver).check());
    }
    
    public static void selectBankThatContains(ArrayList<String> strContains, Channel channel, WebDriver driver) {
        //En el caso de móvil para que aparezca el desplegable se ha de seleccionar el icono del banco
        if (channel.isDevice()) {
        	if (!state(Visible, By.xpath(XPathSelectBancos), driver).check()) {
                clickIconoBanco(driver);
        	}
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

	public static void clickIconoBanco(WebDriver driver) {
		click(By.xpath(XPathInputIconoTrustpay), driver).exec();
	}

    public static void clickButtonToContinuePay(Channel channel, WebDriver driver) {
        if (channel.isDevice()) {
        	click(By.xpath(XPathButtonContinueMobil), driver).exec();
        } else {
        	click(By.xpath(XPathButtonPayDesktop), driver).exec();
        }
    }
}
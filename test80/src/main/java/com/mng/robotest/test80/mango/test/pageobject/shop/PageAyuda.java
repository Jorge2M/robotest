package com.mng.robotest.test80.mango.test.pageobject.shop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;


public class PageAyuda extends WebdrvWrapp {
    static String XPathCabPreguntasFreq = "//h1[text()[contains(.,'Preguntas frecuentes')]]";
    
    static String XPathFAQMobile = "//li[@class='leaf']//a[text()[contains(.,'Preguntas frecuentes')]]";

    public static String getXPathTelefono(String telefono) {
        return ("//*[@class='text_container']/p[text()[contains(.,'" + telefono + "')]]");
    }
    
    public static boolean isPresentCabPreguntasFreq(Channel channel, WebDriver driver) {
    	if (channel==Channel.movil_web)
    		return (isElementPresent(driver, By.xpath(XPathFAQMobile)));
        return (isElementPresent(driver, By.xpath(XPathCabPreguntasFreq)));
    }
    
    public static boolean isPresentTelefono(WebDriver driver, String telefono) {
        return isElementPresent(driver, By.xpath(getXPathTelefono(telefono)));
    }
}

package com.mng.robotest.test80.mango.test.pageobject.shop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.conf.Channel;
import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageAyuda {
	
    static String XPathCabPreguntasFreq = "//h1[text()[contains(.,'Preguntas frecuentes')]]";
    
    static String XPathFAQMobile = "//li[@class='leaf']//a[text()[contains(.,'Preguntas frecuentes')]]";

    public static String getXPathTelefono(String telefono) {
        return ("//*[@class='text_container']/p[text()[contains(.,'" + telefono + "')]]");
    }

	public static boolean isPresentCabPreguntasFreq(Channel channel, WebDriver driver) {
		if (channel==Channel.movil_web) {
			return (state(Present, By.xpath(XPathFAQMobile), driver).check());
		}
		return (state(Present, By.xpath(XPathCabPreguntasFreq), driver).check());
	}

	public static boolean isPresentTelefono(WebDriver driver, String telefono) {
		String xpath = getXPathTelefono(telefono);
		return (state(Present, By.xpath(xpath), driver).check());
	}
}

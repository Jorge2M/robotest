package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.tmango;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageAmexInputTarjeta {

    static String XPathIconoBancoSabadell = "//div[@class='logoEntidad']/img[@src[contains(.,'entidad/81.png')]]";
    static String XPathInputNumTarj = "//input[@id[contains(.,'inputCard')]]";
    static String XPathInputMesCad = "//input[@id[contains(.,'cad1')] and @maxlength=2]";
    static String XPathInputAnyCad = "//input[@id[contains(.,'cad2')] and @maxlength=2]";
    static String XPathInputCvc = "//input[@id[contains(.,'codseg')] and @maxlength=4]";
    static String XPathPagarButton = "//button[@id[contains(.,'divImgAceptar')]]";
    
    public static boolean isPasarelaBancoSabadellUntil(int maxSeconds, WebDriver driver) {
    	return (state(Present, By.xpath(XPathIconoBancoSabadell), driver)
    			.wait(maxSeconds).check());
    }
    
    public static void inputDataTarjeta(String numTarj, String mesCad, String anyCad, String Cvc, WebDriver driver) {
        driver.findElement(By.xpath(XPathInputNumTarj)).sendKeys(numTarj);
        driver.findElement(By.xpath(XPathInputMesCad)).sendKeys(mesCad);
        driver.findElement(By.xpath(XPathInputAnyCad)).sendKeys(anyCad);
        driver.findElement(By.xpath(XPathInputCvc)).sendKeys(Cvc);
    }

    public static boolean isPresentNumTarj(WebDriver driver) {
    	return (state(Present, By.xpath(XPathInputNumTarj), driver).check());
    }
    
    public static boolean isPresentInputMesCad(WebDriver driver) {
    	return (state(Present, By.xpath(XPathInputMesCad), driver).check());
    }
    
    public static boolean isPresentInputAnyCad(WebDriver driver) {
    	return (state(Present, By.xpath(XPathInputAnyCad), driver).check());
    }
    
    public static boolean isPresentInputCvc(WebDriver driver) {
    	return (state(Present, By.xpath(XPathInputCvc), driver).check());
    }

    public static boolean isPresentPagarButton(WebDriver driver) {
    	return (state(Present, By.xpath(XPathPagarButton), driver).check());
    }

	public static void clickPagarButton(WebDriver driver) {
		click(By.xpath(XPathPagarButton), driver).exec();
	}
}

package com.mng.robotest.test80.mango.test.pageobject.shop.identificacion;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import com.mng.robotest.test80.mango.test.pageobject.shop.AllPages;


public class PageRecuperaPasswd {
    
    static String xpathInputCorreo = "//input[@type='text' and @id[contains(.,'RPemail')]]";
    static String xpathButtonEnviar = "//input[@type='submit' and @id[contains(.,'ResetPassword')]]";
    static String xpathMsgRevisaTuEmail = "//div[text()[contains(.,'REVISA TU EMAIL')]]";
    static String xpathButtonIrDeShopping = "//div[@id[contains(.,'IrShopping')]]/a";
    
    public static boolean isPageUntil(int maxSecondsToWait, WebDriver driver) {
        return AllPages.isPresentElementWithTextUntil("RECUPERA TU CONTRASEÑA", maxSecondsToWait, driver);
    }
    
    public static boolean isPresentInputCorreo(WebDriver driver) {
    	return (state(Present, By.xpath(xpathInputCorreo), driver).check());
    }
    
    public static void inputEmail(String email, WebDriver driver) {
        driver.findElement(By.xpath(xpathInputCorreo)).clear();
        driver.findElement(By.xpath(xpathInputCorreo)).sendKeys(email);
    }

	public static void clickEnviar(WebDriver driver) {
		click(By.xpath(xpathButtonEnviar), driver).exec();
	}

    public static boolean isVisibleRevisaTuEmailUntil(int maxSeconds, WebDriver driver) {
    	return (state(Visible, By.xpath(xpathMsgRevisaTuEmail), driver)
    			.wait(maxSeconds).check());
    }

    public static boolean isVisibleButtonIrDeShopping(WebDriver driver) {
    	return (state(Visible, By.xpath(xpathButtonIrDeShopping), driver).check());
    }
        
}

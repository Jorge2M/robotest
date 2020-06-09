package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.sofort;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


/**
 * Page4: la página de entrada usuario/password
 * @author jorge.munoz
 *
 */
public class PageSofort4th {
	
    static String XPathSubmitButton = "//form//button[@class[contains(.,'primary')]]";
    static String XPathInputUser = "//input[@id[contains(.,'LOGINNAMEUSERID')]]";
    static String XPathInputPass = "//input[@id[contains(.,'USERPIN')] and @type='password']";
    static String XPathFormSelCta = "//form[@action[contains(.,'select_account')]]";
    static String XPathInputRadioCtas = "//input[@id[contains(.,'account')] and @type='radio']";
    static String XPathInputTAN = "//input[(@id[contains(.,'BackendFormTAN')] or @id[contains(.,'BackendFormTan')]) and @type='text']";
    
    public static boolean isPage(WebDriver driver) {
        if (driver.getTitle().toLowerCase().contains("sofort") && 
        	state(Visible, By.xpath(XPathInputUser), driver).check()) {
            return true;
        }
        return false;
    }
    
    public static void inputUserPass(WebDriver driver, String user, String password) {
        driver.findElement(By.xpath(XPathInputUser)).sendKeys(user);
        driver.findElement(By.xpath(XPathInputPass)).sendKeys(password);        
    }

	public static void clickSubmitButton(WebDriver driver) {
		click(By.xpath(XPathSubmitButton), driver).exec();
	}

    public static boolean isVisibleFormSelCta(WebDriver driver) {
    	return (state(Visible, By.xpath(XPathFormSelCta), driver).check());
    }
    
    public static void selectRadioCta(WebDriver driver, int posCta) {
        driver.findElements(By.xpath(XPathInputRadioCtas)).get(posCta).click();
    }
    
    public static boolean isVisibleInputTAN(WebDriver driver) {
    	return (state(Visible, By.xpath(XPathInputTAN), driver).check());
    }
    
    public static void inputTAN(WebDriver driver, String TAN) {
        driver.findElement(By.xpath(XPathInputTAN)).sendKeys(TAN);    
    }        
}

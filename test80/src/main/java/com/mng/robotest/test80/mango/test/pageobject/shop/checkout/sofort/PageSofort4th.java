package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.sofort;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageSofort4th extends PageObjTM {
	
    private final static String XPathSubmitButton = "//form//button[@class[contains(.,'primary')]]";
    private final static String XPathInputUser = "//input[@id[contains(.,'LOGINNAMEUSERID')]]";
    private final static String XPathInputPass = "//input[@id[contains(.,'USERPIN')] and @type='password']";
    private final static String XPathFormSelCta = "//form[@action[contains(.,'select_account')]]";
    private final static String XPathInputRadioCtas = "//input[@id[contains(.,'account')] and @type='radio']";
    private final static String XPathInputTAN = "//input[(@id[contains(.,'BackendFormTAN')] or @id[contains(.,'BackendFormTan')]) and @type='text']";
    
    public PageSofort4th(WebDriver driver) {
    	super(driver);
    }
    
    public boolean isPage() {
        if (driver.getTitle().toLowerCase().contains("sofort") && 
        	state(Visible, By.xpath(XPathInputUser)).check()) {
            return true;
        }
        return false;
    }
    
    public void inputUserPass(String user, String password) {
        driver.findElement(By.xpath(XPathInputUser)).sendKeys(user);
        driver.findElement(By.xpath(XPathInputPass)).sendKeys(password);        
    }

	public void clickSubmitButton() {
		click(By.xpath(XPathSubmitButton), driver).exec();
	}

    public boolean isVisibleFormSelCta() {
    	return (state(Visible, By.xpath(XPathFormSelCta)).check());
    }
    
    public void selectRadioCta(int posCta) {
        driver.findElements(By.xpath(XPathInputRadioCtas)).get(posCta).click();
    }
    
    public boolean isVisibleInputTAN() {
    	return (state(Visible, By.xpath(XPathInputTAN)).check());
    }
    
    public void inputTAN(String TAN) {
        driver.findElement(By.xpath(XPathInputTAN)).sendKeys(TAN);    
    }        
}

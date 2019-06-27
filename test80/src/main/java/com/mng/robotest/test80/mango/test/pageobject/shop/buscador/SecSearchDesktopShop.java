package com.mng.robotest.test80.mango.test.pageobject.shop.buscador;

import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.Mensajes;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SecSearchDesktopShop extends WebdrvWrapp implements SecSearch {
	
	private final WebDriver driver;
	
    private final static String XPathIconoLupa = "//span[@class[contains(.,'icon-outline-search')]]";
    private final static String XPathInputBuscador = "//input[@class='search-input']";
    private final static String XPathCloseAspa = "//span[@class[contains(.,'icon-outline-close')]]";
    
    private SecSearchDesktopShop(WebDriver driver) {
    	this.driver = driver;
    }
    
    public static SecSearchDesktopShop getNew(WebDriver driver) {
    	return (new SecSearchDesktopShop(driver));
    }
    
    @Override
    public void search(String referencia) throws Exception {
        new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Mensajes.getXPathCapaCargando())));
        selectLupa();
        setTextAndReturn(referencia);
    }
    
    @Override
	public void close() throws Exception {
		clickAndWaitLoad(driver, By.xpath(XPathCloseAspa));
	}

    private void selectLupa() throws Exception {
        driver.findElement(By.xpath(XPathIconoLupa)).click(); 
        WebdrvWrapp.isElementVisibleUntil(driver, By.xpath(XPathInputBuscador), 1);
    }
    
    private void setTextAndReturn(String referencia) throws Exception {
        WebElement input = getElementVisible(driver, By.xpath(XPathInputBuscador));
        sendKeysWithRetry(5, input, referencia);
        input.sendKeys(Keys.RETURN);
        waitForPageLoaded(driver);
    }
}

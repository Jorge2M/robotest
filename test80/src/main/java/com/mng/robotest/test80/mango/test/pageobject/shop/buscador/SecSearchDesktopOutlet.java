package com.mng.robotest.test80.mango.test.pageobject.shop.buscador;

import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.Mensajes;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SecSearchDesktopOutlet extends WebdrvWrapp implements SecSearch {
	
	private final WebDriver driver;
	
    private final static String XPathIconoLupa = "//span[@class='menu-search-icon']";
    private final static String XPathInputBuscador = "//input[@class='search-input']";
    private final static String XPathCloseAspa = "//span[@class='search-close']";
    
    private SecSearchDesktopOutlet(WebDriver driver) {
    	this.driver = driver;
    }
    
    public static SecSearchDesktopOutlet getNew(WebDriver driver) {
    	return (new SecSearchDesktopOutlet(driver));
    }
    
    @Override
    public void search(String referencia) throws Exception {
        new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Mensajes.getXPathCapaCargando())));
        selectBuscador();
        setTextAndReturn(referencia);
    }
    
    @Override
	public void close() throws Exception {
    	clickAndWaitLoad(driver, By.xpath(XPathCloseAspa));
	}

    private void selectBuscador() throws Exception {
        driver.findElement(By.xpath(XPathIconoLupa)).click(); 
        if (!isElementVisibleUntil(driver, By.xpath(XPathInputBuscador), 1)) {
        	driver.findElement(By.xpath(XPathIconoLupa)).click();
        	isElementVisibleUntil(driver, By.xpath(XPathInputBuscador), 1);
        }
        
        //No nos queda más remedio que incluir un delay puesto que el input_subrayado toma su tiempo para expandirse hacia la derecha
        Thread.sleep(700);
    }
    
    /**
     * Introducimos la referencia en el buscador y seleccionamos RETURN
     */
    private void setTextAndReturn(String referencia) throws Exception {
        WebElement input = getElementVisible(driver, By.xpath(XPathInputBuscador));
        sendKeysWithRetry(5, input, referencia);
        input.sendKeys(Keys.RETURN);
        waitForPageLoaded(driver);
    }
}

package com.mng.robotest.test80.mango.test.pageobject.shop.buscador;

import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.Mensajes;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class SecBuscadorDesktop extends WebdrvWrapp {
    private final static String XPathInputBuscador = "//input[@class[contains(.,'search-input')]]";
    private final static String XPathInputLupaShopDesktop = "//span[@class='menu-search-icon']";
    
    /**
     * Seleccionamos el icono de lupa/el label del buscador hasta que conseguimos que esté activado el input para poder introducir texto
     */
    public static void selectBuscador(WebDriver driver) throws Exception {
        driver.findElement(By.xpath(XPathInputLupaShopDesktop)).click(); 
        int maxSeconds = 1;
        if (!isInputBuscadorPresentUntil(maxSeconds, driver)) {
        	driver.findElement(By.xpath(XPathInputLupaShopDesktop)).click();
        	isInputBuscadorPresentUntil(maxSeconds, driver);
        }
        
        //No nos queda más remedio que incluir un delay puesto que el input_subrayado toma su tiempo para expandirse hacia la derecha
        Thread.sleep(700);
    }
    
    /**
     * Busca un determinado artículo por su referencia y no espera a la página de resultado
     */
    public static void buscarReferenciaNoWait(String referencia, WebDriver driver) 
    throws Exception {
        new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Mensajes.getXPathCapaCargando())));
        selectBuscador(driver);
        setTextAndReturn(referencia, driver);
    }
    
    public static boolean isInputBuscadorPresentUntil(int maxSecondsToWait, WebDriver driver) {
    	return (isElementPresentUntil(driver, By.xpath(XPathInputBuscador), maxSecondsToWait));
    }
    
    /**
     * Introducimos la referencia en el buscador y seleccionamos RETURN
     */
    public static void setTextAndReturn(String referencia, WebDriver driver) 
    throws Exception {
        WebElement input = getElementVisible(driver, By.xpath(XPathInputBuscador));
        sendKeysWithRetry(5, input, referencia);
        input.sendKeys(Keys.RETURN);
        waitForPageLoaded(driver);
    }
}

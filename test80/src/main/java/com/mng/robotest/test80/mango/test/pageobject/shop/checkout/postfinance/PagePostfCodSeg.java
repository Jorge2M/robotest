package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.postfinance;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;


public class PagePostfCodSeg extends WebdrvWrapp {

    static String XPathAceptarButton = "//form/input[@id='btn_Accept']";
    static String XPathInputCodSeg = "//input[@id='postfinanceCardId']";
    static String XPathButtonWeiter = "//button[@class='efinance-button' and text()[contains(.,'Weiter')]]";
    
    /**
     * @return si se trata de la pasarela de test
     * Para CI y PRE la pasarela de pago es diferente a la de PRO
     *   PRE/CI-Postfinance Card-> https://e-payment.postfinance.ch/ncol/test/orderstandard.asp
     *   PRO-PostFinance Card ->   https://epayment.postfinance.ch/pfcd/authentication/arh/cardIdForm
     *   PRE-Postfinance -> https://e-payment.postfinance.ch/ncol/test/orderstandard.asp
     *   PRO-PostFinance -> https://epayment.postfinance.ch/pfef/authentication/v2
     */ 
    public static boolean isPasarelaTest(WebDriver driver) {
        return (driver.getCurrentUrl().contains("test"));
    }
    
    /**
     * @return si se trata de la pasarela de Postfinance correcta de test (hay de 2 tipos "e-finance" y "Card")
     */
    public static boolean isPasarelaPostfinanceTest(WebDriver driver, String metodoPago) {
        boolean isPasarela = false;
        if (metodoPago.toUpperCase().contains("E-FINANCE") && driver.getTitle().contains("e-finance"))
            isPasarela = true;
            
        if (metodoPago.toUpperCase().contains("CARD") && driver.getTitle().contains("Card"))
            isPasarela = true;
            
        return isPasarela;
    }
    
    /**
     * @return si se trata de la pasarela de Postfinance correcta de pro
     */
    public static boolean isPasarelaPostfinanceProUntil(int maxSecondsToWait, WebDriver driver) {
    	return (titleContainsUntil(driver, "E-Payment", maxSecondsToWait));
        //return (driver.getTitle().contains("E-Payment"));
    }
    
    /**
     * @return si existe un botón "Aceptar" en la página1 de efinance y card
     */
    public static boolean isPresentButtonAceptar(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathAceptarButton)));
    }
    
    public static void clickAceptarButton(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathAceptarButton));
    }
    
    /**
     * @return si existe o no el input del código de seguridad (en "e-finance" no existe y en "card" sí)
     */
    public static boolean isPresentInputCodSeg(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathInputCodSeg)));
    }
    
    public static boolean isPresentButtonWeiter(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathButtonWeiter)));
    }
    
    public static void inputCodigoSeguridad(WebDriver driver, String codigoSeg) {
        driver.findElement(By.xpath(XPathInputCodSeg)).sendKeys(codigoSeg);
    }

	public static void waitLoadPage() throws Exception {
		Thread.sleep(5000);
	}
	
}
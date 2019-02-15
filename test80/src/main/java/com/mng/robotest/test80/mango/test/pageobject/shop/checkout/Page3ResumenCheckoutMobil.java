package com.mng.robotest.test80.mango.test.pageobject.shop.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen; 

/**
 * @author jorge.muñoz
 *
 */

public class Page3ResumenCheckoutMobil extends WebdrvWrapp {
    
    static String XPathLink3Resumen = "//h2[@class[contains(.,'xwing-toggle')] and @data-toggle='step3']";
    static String XPathButtonConfirmarPago = "//button[@id[contains(.,'complete-step3')]]";
    static String XPathPrecioTotal = "//div[@class[contains(.,'summary-total-price')]]/p";
    static String XPathDescuento = "//div[@class[contains(.,'summary-subtotal-price')]]/p/span[@class='price-negative']/..";
    static String XPathDireccionEnvioText = "//p[@class='summary-info-subdesc']";
    
    public static void clickConfirmarPago(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonConfirmarPago), TypeOfClick.javascript);
    }
    
    public static boolean isClickableButtonConfirmarPagoUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementClickableUntil(driver, By.xpath(XPathButtonConfirmarPago), maxSecondsToWait));
    }
    
    public static void clickConfirmaPagoAndWait(int maxSecondsToWait, WebDriver driver) throws Exception {
        clickConfirmarPago(driver);
        
        //Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no funciona así que ejecutamos un 2o 
        if (isClickableButtonConfirmarPagoUntil(0, driver)) {
        	clickConfirmarPago(driver);  
        	if (isClickableButtonConfirmarPagoUntil(1, driver))
        		clickConfirmarPago(driver);  
        }
        
        PageRedirectPasarelaLoading.isPageNotVisibleUntil(maxSecondsToWait, driver);
    }

    public static String getPrecioTotalFromResumen(WebDriver driver) throws Exception {
        String precioTotal = "";
        
        //En el caso de móvil, si estamos en el Paso-2 "Datos del pago" iremos al Paso-3 de "Resumen" puesto que es allí donde se encuentra el importe total. 
        //Finalmente volveremos al Paso-2
        if (Page2DatosPagoCheckoutMobil.isClickableButtonVerResumenUntil(driver, 0/*seconds*/)) {
            Page2DatosPagoCheckoutMobil.clickButtonVerResumen(driver);
            precioTotal = PageCheckoutWrapper.formateaPrecioTotal(XPathPrecioTotal, driver);
            if (precioTotal.indexOf("0")==0)
                //Si el total es 0 podríamos estar en el caso de saldo en cuenta (el importe total = importe del descuento)
                precioTotal = PageCheckoutWrapper.formateaPrecioTotal(XPathDescuento, driver);
            Page2DatosPagoCheckoutMobil.clickLink2DatosPagoAndWait(driver);
        }
        else {
            precioTotal = PageCheckoutWrapper.formateaPrecioTotal(XPathPrecioTotal, driver);
            if (precioTotal.indexOf("0")==0)
                //Si el total es 0 podríamos estar en el caso de saldo en cuenta (el importe total = importe del descuento)
                precioTotal = PageCheckoutWrapper.formateaPrecioTotal(XPathDescuento, driver);
        }
        
        return (ImporteScreen.normalizeImportFromScreen(precioTotal));
    }

    public static String getTextDireccionEnvioCompleta(WebDriver driver) {
        if (isElementPresent(driver, By.xpath(XPathDireccionEnvioText)))
            return (driver.findElement(By.xpath(XPathDireccionEnvioText)).getText());
        
        return "";
    }    
}

package com.mng.robotest.test80.mango.test.pageobject.shop;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;


public class SecKrediKartiCheckout {

    static String XPathFormularioTarjeta = "//div[@class='msuFormularioTarjeta']";
    static String XPathInputCardNumber = XPathFormularioTarjeta + "//input[@id[contains(.,'cardNumber')] or @id[contains(.,'msu_cardpan')] or @id[contains(.,'number-card')]]";
    static String XPathCapaPagoPlazoMobil = "//table[@class[contains(.,'installment-msu')]]";
    static String XPathCapaPagoPlazoDesktop = "//div[@class[contains(.,'installmentsTable')]]";            
    
    public static String getXPATH_capaPagoPlazo(Channel channel) {
        if (channel==Channel.movil_web)
            return XPathCapaPagoPlazoMobil;
        
        return XPathCapaPagoPlazoDesktop;
    }
    
    /**
     * XPATH correspondiente a uno de los radiobuttons asociados a las opciones de pago a plazo
     * @param posicion: posición de la opción en la lista
     */    
    public static String getXPATH_checkPlazos(int posicion, Channel channel) {
        String xpathDivPlazo = getXPATH_capaPagoPlazo(channel);
        if (channel==Channel.movil_web)
            return xpathDivPlazo + "//div[@class[contains(.,'custom-radio')] and @data-custom-radio-id][" + posicion + "]";
        
        return xpathDivPlazo + "//input[@type='radio' and @name='installment'][" + posicion + "]"; 
    }
 
    /**
     * Introduce un número de tarjeta y ejecuta un TAB
     */
    public static void inputCardNumberAndTab(WebDriver driver, String numTarj) {
        driver.findElement(By.xpath(XPathInputCardNumber)).sendKeys(numTarj, Keys.TAB);
    }
    
    /**
     * Nos dice si está visible la capa de pago a plazos (la que aparece después de introducir el número de tarjeta)
     */
    public static boolean isVisiblePagoAPlazoUntil(WebDriver driver, Channel channel, int seconds) {
        boolean isVisible = true;
        String xpathCapaPlazo = getXPATH_capaPagoPlazo(channel);
        try {
            new WebDriverWait(driver, seconds).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathCapaPlazo)));
        }
        catch (Exception e) {
            isVisible = false;
        }
        
        return isVisible;
    }
    
    /**
     * Selecciona uno de los radiobuttons asociados a las opciones de pago a plazo
     * @param posicion: posición de la opción en la lista
     */
    public static void clickRadioPlazo(WebDriver driver, int posicion, Channel channel) {
        driver.findElement(By.xpath(getXPATH_checkPlazos(posicion, channel))).click();
    }
}

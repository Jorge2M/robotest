package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.tmango;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecTMango {

    public enum TipoPago {pagoHabitual, tresMeses, seisMeses, pagoUnico}
    
    static String XPathSectionMobil = "//div[@class[contains(.,'mango_card')] and @class[contains(.,'show')]]"; 
    static String XPathSectionDesktop = "//div[@id='mangoCardContent']"; 
    
    /**
     * @return el literal descriptivo que debería aparecer a la derecha de cada uno de los radiobuttons
     */
    public static String getDescripcionTipoPago(TipoPago tipoPago) {
        switch (tipoPago) {
        case pagoHabitual:
            return "La modalidad de pago habitual que tengas elegida para tu Tarjeta MANGO";
        case tresMeses:
            return "3 meses sin intereses";
        case seisMeses:
            return "6 meses sin intereses";
        case pagoUnico:
            return "Pago único a final de mes";
        default:
            return "";
        }
    }
    
    /**
     * @return el xpath que engloba la capa con la sección
     */
    public static String getXPath_section(Channel channel) {
        if (channel==Channel.movil_web) {
            return XPathSectionMobil;
        }
        return XPathSectionDesktop;
    }
    
    /**
     * @return el xpath correspondiente a los elementos que contienen el texto con los labels de cada una de las modalidades
     */
    public static String getXPATH_labelsCheckModalidad(Channel channel) {
        String xpathSection = getXPath_section(channel); 
        if (channel==Channel.movil_web) {
            return (xpathSection + "//p[@class='method-name']");
        }
        return (xpathSection + "//input/../label/span");
    }
    
    /**
     * @return xpath correspondiente al elemento que contiene un determinado texto de modalidad
     */
    public static String getXPATH_labelModalidad(TipoPago tipoPago, Channel channel) {
        String litModalidad = SecTMango.getDescripcionTipoPago(tipoPago);
        String xpathLabelsMod = getXPATH_labelsCheckModalidad(channel);
        return (xpathLabelsMod + "[text()[contains(.,'" + litModalidad + "')]]");
    }
    
    /**
     * @return el xpath correspondiente al elemento que puede recibir el click para el check de la modalidad de pago
     */
    public static String getXPATH_clickModalidad(TipoPago tipoPago, Channel channel) {
        String xpathLabelMod = getXPATH_labelModalidad(tipoPago, channel);
        if (channel==Channel.movil_web) {
            return (xpathLabelMod + "/..");
        }
        return (xpathLabelMod + "/../../input");
    }
    
    public static boolean isVisibleUntil(Channel channel, int maxSeconds, WebDriver driver) {
    	String xpath = getXPath_section(channel);
    	return (state(Visible, By.xpath(xpath), driver).wait(maxSeconds)
    			.check());
    }
    
    /**
     * @return si una determinada modalidad está disponible
     */
    public static boolean isModalidadDisponible(WebDriver driver, TipoPago tipoPago, Channel channel) {
    	String xpath = getXPATH_labelModalidad(tipoPago, channel);
    	return (state(Present, By.xpath(xpath), driver).check());
    }
    
    /**
     * Selecciona una modalidad de pago especificada por su literal
     */
    public static void clickModalidad(WebDriver driver, TipoPago tipoPago, Channel channel) {
        driver.findElement(By.xpath(getXPATH_clickModalidad(tipoPago, channel))).click();
    }
}

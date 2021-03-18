package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.tmango;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecTMango extends PageObjTM {

    public enum TipoPago {pagoHabitual, tresMeses, seisMeses, pagoUnico}
    
    private final Channel channel;
    
    private final static String XPathSectionMobil = "//div[@class[contains(.,'mango_card')] and @class[contains(.,'show')]]"; 
    private final static String XPathSectionDesktop = "//div[@id='mangoCardContent']"; 
    
    public SecTMango(Channel channel, WebDriver driver) {
    	super(driver);
    	this.channel = channel;
    }
    
    public String getDescripcionTipoPago(TipoPago tipoPago) {
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
    
    public String getXPath_section() {
        if (channel.isDevice()) {
            return XPathSectionMobil;
        }
        return XPathSectionDesktop;
    }
    
    public String getXPATH_labelsCheckModalidad() {
        String xpathSection = getXPath_section(); 
        if (channel.isDevice()) {
            return (xpathSection + "//p[@class='method-name']");
        }
        return (xpathSection + "//input/../label/span");
    }
    
    public String getXPATH_labelModalidad(TipoPago tipoPago) {
        String litModalidad = getDescripcionTipoPago(tipoPago);
        String xpathLabelsMod = getXPATH_labelsCheckModalidad();
        return (xpathLabelsMod + "[text()[contains(.,'" + litModalidad + "')]]");
    }
    
    public String getXPATH_clickModalidad(TipoPago tipoPago) {
        String xpathLabelMod = getXPATH_labelModalidad(tipoPago);
        if (channel.isDevice()) {
            return (xpathLabelMod + "/..");
        }
        return (xpathLabelMod + "/../../input");
    }
    
    public boolean isVisibleUntil(int maxSeconds) {
    	String xpath = getXPath_section();
    	return (state(Visible, By.xpath(xpath)).wait(maxSeconds).check());
    }
    
    public boolean isModalidadDisponible(TipoPago tipoPago) {
    	String xpath = getXPATH_labelModalidad(tipoPago);
    	return (state(Present, By.xpath(xpath)).check());
    }
    
    public void clickModalidad(TipoPago tipoPago) {
        driver.findElement(By.xpath(getXPATH_clickModalidad(tipoPago))).click();
    }
}

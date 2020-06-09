package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.ideal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecIdeal {
	
	//HAY QUE AÑADIR LAS OPCIONES DE PRE Y QUITAR LAS DE PRO
    public enum BancoSeleccionado {
    	KiesEenBank("Kies een bank", "selected"),
    	TestIssuer("Test Issuer", "1121"),
    	TestIssuer2("Test Issuer 2", "1151"),
    	TestIssuer3("Test Issuer 3", "1152"),
    	TestIssuer4("Test Issuer 4", "1153"),
    	TestIssuer5("Test Issuer 5", "1154"),
    	TestIssuer6("Test Issuer 6", "1155"),
    	TestIssuer7("Test Issuer 7", "1156"),
    	TestIssuer8("Test Issuer 8", "1157"),
    	TestIssuer9("Test Issuer 9", "1158"),
    	TestIssuer10("Test Issuer 10", "1159"),
    	TestIssuerRefused("Test Issuer Refused", "1160"),
    	TestIssuerPending("Test Issuer Pending", "1161"),
    	TestIssuerCancelled("Test Issuer Cancelled", "1162");
    	
    	public String nombre; 
    	public String valueOption;
    	
    	private BancoSeleccionado(String nombre, String valueOption) {
    		this.nombre = nombre;
    		this.valueOption = valueOption;
    	}};

    static String XPathCardConditions = "//div[@id='textoCondicionesTarjeta']";
    static String XPathSelectorBankIdeal = XPathCardConditions + "//div[@id='ideal-bank-selector']";
    static String XPathListBankIdeal = XPathSelectorBankIdeal + "//select[@name[contains(.,'panelTarjetasForm')] and @class='bank-select']";
    
    static String XPathCardConditionsMobile = "//div[@id='avisoConfirmar']";
    static String XPathSelectorBankIdealMobile = XPathCardConditionsMobile + "//div[@id='ideal-bank-selector']";
    static String XPathListBankIdealMobile = "//select[@class[contains(.,'bank-select')]]";
    
    /**
     * @return el xpath correspondiente al contenedor segun si es movil o desktop. 
     */
    
    public static String getXPath_section(Channel channel) {
    	if (channel==Channel.mobile) {
    		return XPathCardConditionsMobile;
    	}
    	return XPathCardConditions;
    }

    

    /**
     * @return el xpath correspondiente al elemento que puede recibir el click para el check del banco
     */
        
    public static boolean isVisibleUntil(Channel channel, int maxSeconds, WebDriver driver) {
    	String xpath = getXPath_section(channel);
    	return (state(Visible, By.xpath(xpath), driver).wait(maxSeconds).check());
    }
    
    public static boolean isVisibleSelectorOfBank(Channel channel, int maxSeconds, WebDriver driver) {
    	if (channel==Channel.mobile) {
    		return (state(Visible, By.xpath(XPathSelectorBankIdealMobile), driver)
    				.wait(maxSeconds).check());
    	}
    	return (state(Present, By.xpath(XPathSelectorBankIdeal), driver)
    			.wait(maxSeconds).check());
    }
    
    /**
     * @return si una determinado banco está disponible
     */
    
    public static boolean isBancoDisponible(WebDriver driver, Channel channel, BancoSeleccionado bancoSeleccionado) {
    	String xpath = getXPathBankOptionByValue(channel, bancoSeleccionado);
    	return (state(Present, By.xpath(xpath), driver).check());
    }
    
    /**
     * @return el xpath que engloba la capa con la sección
     */
    
    public static String getXPathBankOptionByValue(Channel channel, BancoSeleccionado bancoSeleccionado) {
    	if (channel==Channel.mobile) { 		
    		return XPathListBankIdealMobile + "//option[@value='" + bancoSeleccionado.valueOption + "']";
    	}
    	return XPathListBankIdeal + "//option[@value='" + bancoSeleccionado.valueOption + "']";
    }
    
    public static String getXPathBankOptionByName(Channel channel, BancoSeleccionado bancoSeleccionado) {
    	if (channel==Channel.mobile) {
    		return XPathListBankIdealMobile + "//option[@text='" + bancoSeleccionado.nombre + "']";
    	}
    	return XPathListBankIdeal + "//option[@text='" + bancoSeleccionado.nombre + "']";
    }
    
    /**
     * Selecciona un banco especifico por su literal o valor
     */
    
    public static void clickBancoByValue(WebDriver driver, Channel channel, BancoSeleccionado bancoSeleccionado) {
    	driver.findElement(By.xpath(getXPathBankOptionByValue(channel, bancoSeleccionado))).click();
    }
    
    public static void clickBancoByName(WebDriver driver, Channel channel, BancoSeleccionado bancoSeleccionado) {
    	driver.findElement(By.xpath(getXPathBankOptionByName(channel, bancoSeleccionado))).click();
    }
}
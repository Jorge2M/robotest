package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.ideal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecIdeal extends PageObjTM {
	
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
    	}
    };

    private final Channel channel;
    	
    private final static String XPathCardConditions = "//div[@id='textoCondicionesTarjeta']";
    private final static String XPathSelectorBankIdeal = XPathCardConditions + "//div[@id='ideal-bank-selector']";
    private final static String XPathListBankIdeal = XPathSelectorBankIdeal + "//select[@name[contains(.,'panelTarjetasForm')] and @class='bank-select']";
    
    private final static String XPathCardConditionsMobile = "//div[@id='avisoConfirmar']";
    private final static String XPathSelectorBankIdealMobile = XPathCardConditionsMobile + "//div[@id='ideal-bank-selector']";
    private final static String XPathListBankIdealMobile = "//select[@class[contains(.,'bank-select')]]";
    
    public SecIdeal(Channel channel, WebDriver driver) {
    	super(driver);
    	this.channel = channel;
    }

    public String getXPath_section() {
    	if (channel.isDevice()) {
    		return XPathCardConditionsMobile;
    	}
    	return XPathCardConditions;
    }

    public boolean isVisibleUntil(int maxSeconds) {
    	String xpath = getXPath_section();
    	return (state(Visible, By.xpath(xpath)).wait(maxSeconds).check());
    }
    
    public boolean isVisibleSelectorOfBank(int maxSeconds) {
    	if (channel.isDevice()) {
    		return (state(Visible, By.xpath(XPathSelectorBankIdealMobile)).wait(maxSeconds).check());
    	}
    	return (state(Present, By.xpath(XPathSelectorBankIdeal)).wait(maxSeconds).check());
    }
    
    public boolean isBancoDisponible(BancoSeleccionado bancoSeleccionado) {
    	String xpath = getXPathBankOptionByValue(bancoSeleccionado);
    	return (state(Present, By.xpath(xpath)).check());
    }
    
    public String getXPathBankOptionByValue(BancoSeleccionado bancoSeleccionado) {
    	if (channel.isDevice()) { 		
    		return XPathListBankIdealMobile + "//option[@value='" + bancoSeleccionado.valueOption + "']";
    	}
    	return XPathListBankIdeal + "//option[@value='" + bancoSeleccionado.valueOption + "']";
    }
    
    public String getXPathBankOptionByName(BancoSeleccionado bancoSeleccionado) {
    	if (channel.isDevice()) {
    		return XPathListBankIdealMobile + "//option[@text='" + bancoSeleccionado.nombre + "']";
    	}
    	return XPathListBankIdeal + "//option[@text='" + bancoSeleccionado.nombre + "']";
    }
    
    public void clickBancoByValue(BancoSeleccionado bancoSeleccionado) {
    	driver.findElement(By.xpath(getXPathBankOptionByValue(bancoSeleccionado))).click();
    }
    
    public void clickBancoByName(BancoSeleccionado bancoSeleccionado) {
    	driver.findElement(By.xpath(getXPathBankOptionByName(bancoSeleccionado))).click();
    }
}
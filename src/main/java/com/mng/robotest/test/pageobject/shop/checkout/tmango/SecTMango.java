package com.mng.robotest.test.pageobject.shop.checkout.tmango;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecTMango extends PageObjTM {

	public enum TipoPago { PAGO_HABITUAL, TRES_MESES, SEIS_MESES, PAGO_UNICO }
	
	private final Channel channel;
	
	private static final String XPATH_SECTION_MOBIL = "//div[@data-id='mango_card']"; 
	private static final String XPATH_SECTION_DESKTOP = "//div[@id='mangoCardContent']"; 
	
	public SecTMango(Channel channel, WebDriver driver) {
		super(driver);
		this.channel = channel;
	}
	
	public String getDescripcionTipoPago(TipoPago tipoPago) {
		switch (tipoPago) {
		case PAGO_HABITUAL:
			return "La modalidad de pago habitual que tengas elegida para tu Tarjeta MANGO";
		case TRES_MESES:
			return "3 meses sin intereses";
		case SEIS_MESES:
			return "6 meses sin intereses";
		case PAGO_UNICO:
			return "Pago único a final de mes";
		default:
			return "";
		}
	}
	
	private String getXPathSection() {
		if (channel==Channel.mobile) {
			return XPATH_SECTION_MOBIL;
		}
		return XPATH_SECTION_DESKTOP;
	}
	
	private String getXPathLabelsCheckModalidad() {
		String xpathSection = getXPathSection(); 
		if (channel==Channel.mobile) {
			return (xpathSection + "//p[@class='method-name']");
		}
		return (xpathSection + "//input/../label/span");
	}
	
	public String getXPathLabelModalidad(TipoPago tipoPago) {
		String litModalidad = getDescripcionTipoPago(tipoPago);
		String xpathLabelsMod = getXPathLabelsCheckModalidad();
		return (xpathLabelsMod + "//self::*[text()[contains(.,'" + litModalidad + "')]]");
	}
	
	public String getXPathClickModalidad(TipoPago tipoPago) {
		String xpathLabelMod = getXPathLabelModalidad(tipoPago);
		if (channel==Channel.mobile) {
			return (xpathLabelMod);
		}
		return (xpathLabelMod + "/../../input");
	}
	
	public boolean isVisibleUntil(int maxSeconds) {
		String xpath = getXPathSection();
		return (state(Visible, By.xpath(xpath)).wait(maxSeconds).check());
	}
	
	public boolean isModalidadDisponible(TipoPago tipoPago) {
		String xpath = getXPathLabelModalidad(tipoPago);
		return (state(Present, By.xpath(xpath)).check());
	}
	
	public void clickModalidad(TipoPago tipoPago) {
		driver.findElement(By.xpath(getXPathClickModalidad(tipoPago))).click();
	}
}

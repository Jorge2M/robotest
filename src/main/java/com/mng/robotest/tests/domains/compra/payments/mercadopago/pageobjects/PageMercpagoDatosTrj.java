package com.mng.robotest.tests.domains.compra.payments.mercadopago.pageobjects;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public abstract class PageMercpagoDatosTrj extends PageBase {
	
	static final String XP_INPUT_NUM_TARJ = "//input[@name='cardNumber']";
	static final String XP_INPUT_FEC_CADUCIDAD = "//input[@name='cardExpiration']";
	static final String XP_INPUT_CVC = "//input[@id='securityCode']";
	static final String XP_BOTON_PAGAR = "//input[@type='submit']";
	
	public enum TypePant {INPUT_DATA_TRJ_NEW, INPUT_CVC_TRJ_SAVED} 
	
	public abstract boolean isPage(int seconds);
	public abstract void sendCvc(String cvc);
	public abstract void sendCaducidadTarj(String fechaVencimiento);
	
	public static PageMercpagoDatosTrj newInstance(Channel channel) {
		switch (channel) {
		case desktop:
			return new PageMercpagoDatosTrjDesktop();
		case mobile:
		default:
			return new PageMercpagoDatosTrjMobil();
		}
	}
	
	public void sendNumTarj(String numTarjeta) {
		getElement(XP_INPUT_NUM_TARJ).sendKeys(numTarjeta);
	}
	
	public TypePant getTypeInput() {
		if (state(VISIBLE, XP_INPUT_NUM_TARJ).check()) {
			return TypePant.INPUT_DATA_TRJ_NEW;
		}
		return TypePant.INPUT_CVC_TRJ_SAVED;
	}
}

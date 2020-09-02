package com.mng.robotest.test80.mango.test.pageobject.shop.miscompras;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;


public abstract class ModalDetalleArticulo extends PageObjTM {

    public abstract boolean isVisible();
    public abstract void clickAspaForClose();
    public abstract String getReferencia();
    public abstract String getNombre();
    public abstract String getPrecio();
	public abstract boolean isReferenciaValidaModal(String idArticulo);
	
	public static ModalDetalleArticulo make(Channel channel, WebDriver driver) {
		switch (channel) {
		case desktop:
			return new ModalDetalleArticuloDesktop(driver);
		case mobile:
			return new ModalDetalleArticuloMobile(driver);
		}
		return null;
	}
    ModalDetalleArticulo(WebDriver driver) {
    	super(driver);
    }
    
    public ModalDetalleArticuloDesktop getDesktopVersion() {
		return (ModalDetalleArticuloDesktop)this;
	}
}

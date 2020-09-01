package com.mng.robotest.test80.mango.test.pageobject.shop.miscompras;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

public abstract class ModalDetalleCompra extends PageObjTM {

	private final ModalDetalleArticulo modalDetalleArticulo;
	
	public ModalDetalleCompra(WebDriver driver) {
		super(driver);
    	modalDetalleArticulo = ModalDetalleArticulo.getNew(driver);
	}
	
    public ModalDetalleArticulo getModalDetalleArticulo() {
    	return modalDetalleArticulo;
    }
}

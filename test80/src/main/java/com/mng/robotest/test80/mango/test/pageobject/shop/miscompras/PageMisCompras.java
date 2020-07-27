package com.mng.robotest.test80.mango.test.pageobject.shop.miscompras;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

public abstract class PageMisCompras extends PageObjTM {

	public abstract boolean isPageUntil(int maxSeconds);
	
	public enum TypeCompra {Tienda, Online}
	
	private final SecDetalleCompraTiendaDesktop secDetalleCompraTienda;
	private final SecQuickViewArticuloDesktop secQuickViewArticulo;
	private final ModalDetalleMisComprasDesktop modalDetalleMisCompras;
	
	public static PageMisCompras make(Channel channel, WebDriver driver) {
		switch (channel) {
		case desktop:
			return new PageMisComprasMobil(driver);
		case mobile:
			return new PageMisComprasDesktop(driver);
		default:
			return null;
		}
	}
	
	protected PageMisCompras(Channel channel, WebDriver driver) {
		super(driver);
		this.secDetalleCompraTienda = SecDetalleCompraTiendaDesktop.getNew(driver);
		this.secQuickViewArticulo = SecQuickViewArticuloDesktop.getNew(driver);
		this.modalDetalleMisCompras = ModalDetalleMisComprasDesktop.getNew(channel, driver);
	}
	
	public SecDetalleCompraTiendaDesktop getSecDetalleCompraTienda() {
		return this.secDetalleCompraTienda;
	}
	public SecQuickViewArticuloDesktop getSecQuickViewArticulo() {
		return this.secQuickViewArticulo;
	}
	public ModalDetalleMisComprasDesktop getModalDetalleMisCompras() {
		return this.modalDetalleMisCompras;
	}
	
}

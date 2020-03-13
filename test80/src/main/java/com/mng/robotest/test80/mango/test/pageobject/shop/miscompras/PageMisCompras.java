package com.mng.robotest.test80.mango.test.pageobject.shop.miscompras;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

public abstract class PageMisCompras extends WebdrvWrapp {

	public abstract boolean isPageUntil(int maxSeconds);
	
	private final SecDetalleCompraTienda secDetalleCompraTienda;
	private final SecQuickViewArticulo secQuickViewArticulo;
	private final ModalDetalleMisCompras modalDetalleMisCompras;

	public final WebDriver driver;
	public final Channel channel;

	public enum TypeCompra {Tienda, Online}

	public PageMisCompras(Channel channel, WebDriver driver) {
		this.driver = driver;
		this.channel = channel;
		this.secDetalleCompraTienda = SecDetalleCompraTienda.getNew(driver);
		this.secQuickViewArticulo = SecQuickViewArticulo.getNew(driver);
		this.modalDetalleMisCompras = ModalDetalleMisCompras.getNew(channel, driver);
	}
	
	public SecDetalleCompraTienda getSecDetalleCompraTienda() {
		return this.secDetalleCompraTienda;
	}
	public SecQuickViewArticulo getSecQuickViewArticulo() {
		return this.secQuickViewArticulo;
	}
	public ModalDetalleMisCompras getModalDetalleMisCompras() {
		return this.modalDetalleMisCompras;
	}
	
}

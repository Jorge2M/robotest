package com.mng.robotest.test80.mango.test.pageobject.shop.miscompras;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;

public interface PageDetallePedido {
	
	public enum DetallePedido {
		OldOld, 
		Old,
		New;
		
		public PageDetallePedido getPageObject(Channel channel, WebDriver driver) {
			switch (this) {
			case OldOld:
				return (new PageDetalleCompraOldOld(driver));
			case Old:
				return (new PageDetalleCompraOld(driver));
			case New:
			default:
				return PageDetalleCompra.make(channel, driver);
			}	
		} 
	};
	
	public DetallePedido getTypeDetalle();
	
	public boolean isPage();
	
	public boolean isPresentImporteTotal(String importeTotal, String codPais);
	
	public boolean isVisiblePrendaUntil(int maxSecondsToWait);
	
	public int getNumPrendas();
	
	public void clickBackButton(Channel channel);
}

package com.mng.robotest.domains.micuenta.pageobjects;

import com.github.jorge2m.testmaker.conf.Channel;


public interface PageDetallePedido {
	
	public enum DetallePedido {
		OLD_OLD, 
		OLD,
		NEW;
		
		public PageDetallePedido getPageObject(Channel channel) {
			switch (this) {
			case OLD_OLD:
				return (new PageDetalleCompraOldOld());
			case OLD:
				return (new PageDetalleCompraOld());
			case NEW:
			default:
				return PageDetalleCompra.make(channel);
			}	
		} 
	}
	
	public DetallePedido getTypeDetalle();
	
	public boolean isPage();
	
	public boolean isPresentImporteTotal(String importeTotal, String codPais);
	
	public boolean isVisiblePrendaUntil(int seconds);
	
	public int getNumPrendas();
	
	public void clickBackButton(Channel channel);
}

package com.mng.robotest.domains.micuenta.pageobjects;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.conftestmaker.AppEcom;

public interface PageDetallePedido {
	
	public enum DetallePedido {
		OldOld, 
		Old,
		New;
		
		public PageDetallePedido getPageObject(Channel channel, AppEcom app) {
			switch (this) {
			case OldOld:
				return (new PageDetalleCompraOldOld());
			case Old:
				return (new PageDetalleCompraOld());
			case New:
			default:
				return PageDetalleCompra.make(channel);
			}	
		} 
	};
	
	public DetallePedido getTypeDetalle();
	
	public boolean isPage();
	
	public boolean isPresentImporteTotal(String importeTotal, String codPais);
	
	public boolean isVisiblePrendaUntil(int secondsToWait);
	
	public int getNumPrendas();
	
	public void clickBackButton(Channel channel);
}

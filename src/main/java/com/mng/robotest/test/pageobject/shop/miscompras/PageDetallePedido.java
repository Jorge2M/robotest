package com.mng.robotest.test.pageobject.shop.miscompras;

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
				return PageDetalleCompra.make(channel, app);
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

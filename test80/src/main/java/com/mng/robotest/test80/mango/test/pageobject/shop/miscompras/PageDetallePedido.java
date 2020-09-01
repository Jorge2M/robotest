package com.mng.robotest.test80.mango.test.pageobject.shop.miscompras;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;

public interface PageDetallePedido {
	
    public enum DetallePedido {
    	Old, 
    	New;
    	
        public PageDetallePedido getPageObject(Channel channel, WebDriver driver) {
        	switch (this) {
        	case Old:
        		return (new PageDetalleCompraOld(driver));
        	case New:
        	default:
        		if (channel==Channel.desktop) {
        			return (new ModalDetalleCompraDesktop(driver));
        		}
        		return (new ModalDetalleCompraMobil(driver));
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

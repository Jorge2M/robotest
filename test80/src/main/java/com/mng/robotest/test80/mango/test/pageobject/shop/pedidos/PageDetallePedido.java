package com.mng.robotest.test80.mango.test.pageobject.shop.pedidos;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.conf.Channel;

public interface PageDetallePedido {
	
    public enum DetallePedido {
    	Old, 
    	New;
    	
        public PageDetallePedido getPageObject(WebDriver driver) {
        	switch (this) {
        	case Old:
        		return (new PageDetallePedidoOld(driver));
        	case New:
        	default:
        		return (new PageDetallePedidoNew(driver));	
        	}	
        } 
    };
    
    public DetallePedido getTypeDetalle();
	
    public boolean isPage();
    
    public boolean isPresentImporteTotal(String importeTotal, String codPais);
    
    public boolean isVisiblePrendaUntil(int maxSecondsToWait) throws Exception;
    
    public int getNumPrendas();
    
    public void clickBackButton(Channel channel) throws Exception;
}

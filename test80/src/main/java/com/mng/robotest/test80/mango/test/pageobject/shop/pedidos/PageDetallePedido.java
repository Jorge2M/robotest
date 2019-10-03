package com.mng.robotest.test80.mango.test.pageobject.shop.pedidos;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.utils.otras.Channel;

public interface PageDetallePedido {
	
    public enum DetallePedido {
    	Old, 
    	New;
    	
        public PageDetallePedido getPageObject() {
        	switch (this) {
        	case Old:
        		return (new PageDetallePedidoOld());
        	case New:
        	default:
        		return (new PageDetallePedidoNew());	
        	}	
        } 
    };
    
    public DetallePedido getTypeDetalle();
	
    public boolean isPage(WebDriver driver);
    
    public boolean isPresentImporteTotal(String importeTotal, String codPais, WebDriver driver);
    
    public boolean isVisiblePrendaUntil(int maxSecondsToWait, WebDriver driver) throws Exception;
    
    public int getNumPrendas(WebDriver driver);
    
    public void clickBackButton(Channel channel, WebDriver driver) throws Exception;
}

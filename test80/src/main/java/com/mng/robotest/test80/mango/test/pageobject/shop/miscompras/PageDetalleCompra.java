package com.mng.robotest.test80.mango.test.pageobject.shop.miscompras;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.pageobject.shop.miscompras.PageMisCompras.TypeTicket;

public abstract class PageDetalleCompra extends PageObjTM implements PageDetallePedido {

	private final ModalDetalleArticulo modalDetalleArticulo;
	
	@Override
	public abstract boolean isPage();
    @Override
    public abstract boolean isPresentImporteTotal(String importeTotal, String codPais);
    @Override
    public abstract boolean isVisiblePrendaUntil(int maxSeconds);
    @Override
    public abstract void clickBackButton(Channel channel);
    @Override
    public abstract int getNumPrendas();
    
    public abstract boolean isVisibleDataTicket(int maxSeconds);
    public abstract boolean isVisibleIdTicket(int maxSeconds);
    public abstract String getIdTicket(TypeTicket typeTicket);
    public abstract String getImporte();
    public abstract String getReferenciaArticulo(int posArticulo);
    public abstract String getNombreArticulo(int posArticulo);
    public abstract String getPrecioArticulo(int posArticulo);
    public abstract void selectArticulo(int posArticulo);
    public abstract void gotoListaMisCompras();
	
    public static PageDetalleCompra make(Channel channel, WebDriver driver) {
    	switch (channel) {
    	case desktop:
    		return new PageDetalleCompraDesktop(channel, driver);
    	case mobile:
    	case tablet:
    		return new PageDetalleCompraMobil(channel, driver);
    	}
    	return null;
    }
    
	public PageDetalleCompra(Channel channel, WebDriver driver) {
		super(driver);
    	modalDetalleArticulo = ModalDetalleArticulo.make(channel, driver);
	}
	
    public ModalDetalleArticulo getModalDetalleArticulo() {
    	return modalDetalleArticulo;
    }
    
    @Override
    public DetallePedido getTypeDetalle() {
    	return DetallePedido.New;
    }

    public ArticuloScreen getDataArticulo(int posArticulo) {
        //Sólo informamos algunos datos relevantes del artículo
        ArticuloScreen articulo = new ArticuloScreen();
        articulo.setReferencia(getReferenciaArticulo(posArticulo));
        articulo.setNombre(getNombreArticulo(posArticulo));
        articulo.setPrecio(getPrecioArticulo(posArticulo));
        return articulo;
    }
    
    protected String getDataRightFrom(String stringFrom, String data) {
    	if (data.indexOf(stringFrom)>=0) { 
	        int beginIndex = data.indexOf(stringFrom) + stringFrom.length();
	        return (data.substring(beginIndex));
    	}
    	return data;
    }
}

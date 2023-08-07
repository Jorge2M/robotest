package com.mng.robotest.domains.micuenta.pageobjects;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.domains.micuenta.pageobjects.PageMisCompras.TypeTicket;
import com.mng.robotest.test.generic.beans.ArticuloScreen;


public abstract class PageDetalleCompra extends PageBase {

	public abstract boolean isPage(int seconds);
	public abstract boolean isVisiblePrendaUntil(int seconds);
	public abstract boolean isPresentImporteTotal(String importeTotal, String codPais);
	public abstract int getNumPrendas();
	public abstract boolean isVisibleDataTicket(int seconds);
	public abstract boolean isVisibleIdTicket(int seconds);
	public abstract String getIdTicket(TypeTicket typeTicket);
	public abstract String getImporte();
	public abstract String getDireccionEnvioOnline();
	public abstract String getReferenciaArticulo(int posArticulo);
	public abstract String getNombreArticulo(int posArticulo);
	public abstract String getPrecioArticulo(int posArticulo);
	public abstract void selectArticulo(int posArticulo);
	
	public static PageDetalleCompra make(Channel channel) {
		if (channel==Channel.desktop) {
			return new PageDetalleCompraDesktop();
		}
		return new PageDetalleCompraMobil();
	}
	
	public ArticuloScreen getDataArticulo(int posArticulo) {
		//Sólo informamos algunos datos relevantes del artículo
		var articulo = new ArticuloScreen();
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
	
	public boolean isVisibleDireccionEnvio(String address) {
		waitMillis(5000);
		return getDireccionEnvioOnline().contains(address);
	}
}

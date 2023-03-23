package com.mng.robotest.domains.micuenta.pageobjects;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.domains.micuenta.pageobjects.PageMisCompras.TypeTicket;
import com.mng.robotest.test.generic.beans.ArticuloScreen;


public abstract class PageDetalleCompra extends PageBase implements PageDetallePedido {

	private final ModalDetalleArticulo modalDetalleArticulo;
	
	public abstract boolean isVisibleDataTicket(int seconds);
	public abstract boolean isVisibleIdTicket(int seconds);
	public abstract String getIdTicket(TypeTicket typeTicket);
	public abstract String getImporte();
	public abstract String getDireccionEnvioOnline();
	public abstract String getReferenciaArticulo(int posArticulo);
	public abstract String getNombreArticulo(int posArticulo);
	public abstract String getPrecioArticulo(int posArticulo);
	public abstract void selectArticulo(int posArticulo);
	public abstract void gotoListaMisCompras();
	
	public static PageDetalleCompra make(Channel channel) {
		if (channel==Channel.desktop) {
			return new PageDetalleCompraDesktop();
		}
		return new PageDetalleCompraMobil();
	}
	
	protected PageDetalleCompra() {
		modalDetalleArticulo = ModalDetalleArticulo.make(channel);
	}
	
	public ModalDetalleArticulo getModalDetalleArticulo() {
		return modalDetalleArticulo;
	}
	
	@Override
	public DetallePedido getTypeDetalle() {
		return DetallePedido.NEW;
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

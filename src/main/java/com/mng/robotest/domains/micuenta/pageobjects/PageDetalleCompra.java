package com.mng.robotest.domains.micuenta.pageobjects;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.domains.micuenta.pageobjects.PageMisCompras.TypeTicket;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.generic.beans.ArticuloScreen;


public abstract class PageDetalleCompra extends PageBase implements PageDetallePedido {

	private final ModalDetalleArticulo modalDetalleArticulo;
	
	@Override
	public abstract boolean isPage();
	@Override
	public abstract boolean isPresentImporteTotal(String importeTotal, String codPais);
	@Override
	public abstract boolean isVisiblePrendaUntil(int seconds);
	@Override
	public abstract void clickBackButton(Channel channel);
	@Override
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
	public abstract void gotoListaMisCompras();
	
	public static PageDetalleCompra make(Channel channel) {
		switch (channel) {
		case desktop:
			return new PageDetalleCompraDesktop();
		case mobile:
		case tablet:
			return new PageDetalleCompraMobil();
		}
		return null;
	}
	
	public PageDetalleCompra() {
		modalDetalleArticulo = ModalDetalleArticulo.make(channel);
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
	
	public boolean isVisibleDireccionEnvio(String address) {
		return getDireccionEnvioOnline().contains(address);
	}
}

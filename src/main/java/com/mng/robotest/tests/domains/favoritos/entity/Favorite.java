package com.mng.robotest.tests.domains.favoritos.entity;

import com.mng.robotest.testslegacy.generic.beans.ArticuloScreen;

public class Favorite {

	String referencia = "";
	String codigoColor = "";
	
	public static Favorite from(ArticuloScreen articulo) {
		var favorite = new Favorite();
		favorite.setReferencia(articulo.getReferencia());
		favorite.setCodigoColor(articulo.getCodigoColor());
		return favorite;
	}
	
	public static Favorite from(String referencia, String codigoColor) {
		var favorite = new Favorite();
		favorite.setReferencia(referencia);
		favorite.setCodigoColor(codigoColor);
		return favorite;
	}
	
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public String getCodigoColor() {
		return codigoColor;
	}
	public void setCodigoColor(String codigoColor) {
		this.codigoColor = codigoColor;
	}

}

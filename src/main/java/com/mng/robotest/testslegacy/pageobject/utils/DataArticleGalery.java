package com.mng.robotest.testslegacy.pageobject.utils;


public class DataArticleGalery {

	private String nombre;
	private String referencia;
	private String imagen;
	
	public DataArticleGalery() {}
	
	public DataArticleGalery(String nombre, String referencia, String imagen) {
		this.nombre = nombre;
		this.referencia = referencia;
		this.imagen = imagen;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getReferencia() {
		return this.referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	
	public String getImagen() {
		return this.imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (!(o instanceof DataArticleGalery)) {
			return false;
		}
		
		var otherObject = (DataArticleGalery)o;
		return (
			(this.nombre==otherObject.nombre || this.nombre.compareTo(otherObject.getNombre())==0) &&
			(this.referencia==otherObject.referencia || this.referencia.compareTo(otherObject.getReferencia())==0) &&
			(this.imagen!=null && (this.imagen==otherObject.imagen || this.imagen.compareTo(otherObject.imagen)==0)));   
	}
	
	@Override
	public int hashCode() {
		assert false : "hashCode not designed";
		return 42; // any arbitrary constant will do
	}

	@Override
	public String toString() {
		return (this.nombre + " (" + this.referencia + ")");
	}
}

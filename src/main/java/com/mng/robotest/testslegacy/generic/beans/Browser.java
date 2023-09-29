package com.mng.robotest.testslegacy.generic.beans;


public class Browser {
	String codigo;
	String descripcion;

	public Browser() {}
		
	public Browser (String codigo, String descripcion) {
		this.codigo = codigo;
		this.descripcion = descripcion;
	}
		
	public String getCodigo() {
		return this.codigo;
	}
		
	public String getDescripcion() {
		return this.descripcion;
	}
		
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}	   
		
	@Override
	public String toString() {
		return "Browser [codigo=" + this.codigo + ",descripcion=" + this.descripcion +
			   ", toString()=" + super.toString() + "]";
	}
}

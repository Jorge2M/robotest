package com.mng.robotest.tests.domains.bolsa.pageobjects;

import com.mng.robotest.testslegacy.data.Talla;

public class ArticuloDataBolsaScreen {
	private String referencia;
	private String nombre;
	private String color;
	private String codColor;
	private Talla talla;
	private String cantidad;
	private float precio;
	
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getColor() {
		return color;
	}
	public String getCodColor() {
		return codColor;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public void setCodColor(String codColor) {
		this.codColor = codColor;
	}
	public Talla getTalla() {
		return talla;
	}
	public void setTalla(Talla talla) {
		this.talla = talla;
	}
	public String getCantidad() {
		return cantidad;
	}
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	public float getPrecio() {
		return precio;
	}
	public void setPrecio(float precio) {
		this.precio = precio;
	}
}

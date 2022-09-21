package com.mng.robotest.domains.compra.pageobject;

public class DirectionData {

	private String nombre;
	private String apellidos;
	private String direccion;
	private String codPostal;
	private String mobil;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getCodPostal() {
		return codPostal;
	}
	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}
	public String getMobil() {
		return mobil;
	}
	public void setMobil(String mobil) {
		this.mobil = mobil;
	}
	
	public String getFormattedHTMLData() {
		String dataHTML = String.format("Nombre: <b>%s</b><br>", nombre);
		dataHTML+=String.format("Apellidos: <b>%s</b><br>", apellidos);
		dataHTML+=String.format("Direccion: <b>%s</b><br>", direccion);
		dataHTML+=String.format("Código Postal: <b>%s</b><br>", codPostal);
		dataHTML+=String.format("Móbil: <b>%s</b>", mobil);
		return dataHTML;
	}	
	

}

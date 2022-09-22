package com.mng.robotest.domains.compra.pageobject;

public class DirectionData2 {

	private String nombre;
	private String apellidos;

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

	public String getFormattedHTMLData() {
		String dataHTML = String.format("Nombre: <b>%s</b><br>", nombre);
		dataHTML+=String.format("Apellidos: <b>%s</b><br>", apellidos);
		return dataHTML;
	}	
	

}

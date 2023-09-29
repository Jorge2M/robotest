package com.mng.robotest.tests.domains.registro.beans;


public class DataNino {
	public enum sexoType {nina, nino}
	
	private sexoType sexo;
	private String nombre;
	private String fechaNacimiento;
	
	public DataNino(sexoType sexo, String nombre, String fechaNacimiento) {
		this.sexo = sexo;
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
	}
	
	public sexoType getSexoType() {
		return this.sexo;
	}

	public String getNombre() {
		return this.nombre;
	}
	
	public String getFechaNacimiento() {
		return this.fechaNacimiento;
	}
	
	public String getFormattedHTMLData() {
		return ("Sexo: <b>" + this.sexo + "</b>, Nombre: <b>" + this.nombre + "</b>, Fecha Nacimiento: <b>" + this.fechaNacimiento + "</b>");  
	}
}

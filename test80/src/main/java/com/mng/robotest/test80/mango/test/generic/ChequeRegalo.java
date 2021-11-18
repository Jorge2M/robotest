package com.mng.robotest.test80.mango.test.generic;

import com.mng.robotest.test80.mango.test.pageobject.chequeregalo.PageChequeRegaloInputData.Importe;


public class ChequeRegalo { 

	Importe importe;
	String nombre;
	String apellidos;
	String email;
	String mensaje;
	
	public Importe getImporte() {
		return this.importe;
	}
	
	public void setImporte(Importe importe) {
		this.importe = importe;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getApellidos() {
		return this.apellidos;
	}
	
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getMensaje() {
		return this.mensaje;
	}
	
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}

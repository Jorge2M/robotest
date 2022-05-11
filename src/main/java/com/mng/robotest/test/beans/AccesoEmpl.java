package com.mng.robotest.test.beans;

import com.mng.robotest.test.utils.awssecrets.GetterSecrets;
import com.mng.robotest.test.utils.awssecrets.Secret;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets.SecretType;


public class AccesoEmpl { 
	
	private final String tarjeta;
	private final String nif;
	private final String nombre;

	private AccesoEmpl(String tarjeta, String nif, String nombre) {
		this.tarjeta = tarjeta;
		this.nif = nif;
		this.nombre = nombre;
	}
	
	public static AccesoEmpl forSpain() {
	   	Secret secret = GetterSecrets.factory().getCredentials(SecretType.EMPLOYEE_DATA);
	   	return new AccesoEmpl(secret.getUser(), secret.getNif(), secret.getNombre());
	}

	public String getTarjeta() {
		return this.tarjeta;
	}
	
	public String getNif() {
		return this.nif;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	@Override
	public String toString() {
			return "AccesoEmpl [tarjeta="+ this.tarjeta + ", nif=" + this.nif + ", nombre=" + this.nombre + 
				 ", toString()=" + super.toString() + "]";
	}

}
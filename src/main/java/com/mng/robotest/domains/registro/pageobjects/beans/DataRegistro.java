package com.mng.robotest.domains.registro.pageobjects.beans;

import com.mng.robotest.domains.registro.pageobjects.beans.ListDataRegistro.DataRegType;


public class DataRegistro {
	public DataRegType dataRegType;
	public String data;
	public boolean validPrevRegistro; //Si el dato es válido previamente a la selección del botón de REGISTRO
	
	public DataRegistro(DataRegType dataRegType, String data, boolean validPrevRegistro) {
		this.dataRegType = dataRegType;
		this.data = data;
		this.validPrevRegistro = validPrevRegistro;
	}
	
	public DataRegType getDataRegType() {
		return this.dataRegType;
	}
	
	public String getData() {
		return this.data;
	}
	
	public boolean isValidPrevRegistro() {
		return this.validPrevRegistro;
	}
	
	public String getFormattedHTMLData() {
		return ("Type Data: <b>" + this.dataRegType + "</b>, Datos: <b>" + this.data + "</b>, Válidos: <b>" + this.validPrevRegistro + "</b>");  
	}
}

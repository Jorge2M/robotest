package com.mng.robotest.test.beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Modopago {
	
	String nombrepago;
	String importepago;
	
	public String getNombrepago() {
		return this.nombrepago;
	}
	
	@XmlElement(name="nombrepago")
	public void setModopago(String nombrepago) {
		this.nombrepago = nombrepago;
	}
	
	public String getImportepago() {
		return this.importepago;
	}
	
	@XmlElement(name="importepago")
	public void setImportepago(String importepago) {
		this.importepago = importepago;
	}
	
	@Override
	public String toString() {
		return "Metodopago [modopago="+ this.nombrepago + ", importepago=" + this.importepago + 
				", toString()=" + super.toString() + "]";
	}
}

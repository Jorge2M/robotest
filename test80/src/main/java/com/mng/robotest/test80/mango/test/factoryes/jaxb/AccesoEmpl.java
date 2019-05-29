package com.mng.robotest.test80.mango.test.factoryes.jaxb;

import javax.xml.bind.annotation.*;

public class AccesoEmpl { 

    String tarjeta;
    String nif;
    String nombre;
    String fecnac;


    public String getTarjeta() {
        return this.tarjeta;
    }
	
	@XmlAttribute(name="tarjeta")
	public void setTarjeta(String tarjeta) {
	    this.tarjeta = tarjeta;
	}
	
	public String getNif() {
	    return this.nif;
	}
	
	@XmlAttribute(name="nif")
	public void setNif(String nif) {
	    this.nif = nif;
	}
	
	public String getNombre() {
	    return this.nombre;
	}
	
	@XmlAttribute(name="nombre")
    public void setNombre(String nombre) {
	    this.nombre = nombre;
	}
	
	public String getFecnac() {
	    return this.fecnac;
	}
	
	@XmlAttribute(name="fecnac")
	public void setFecnac(String fecnac) {
	    this.fecnac = fecnac;
	}
	
	@Override
	public String toString() {
	        return "AccesoEmpl [tarjeta="+ this.tarjeta + ", nif=" + this.nif + ", nombre=" + this.nombre + ", fecnac=" + this.fecnac +
	             ", toString()=" + super.toString() + "]";
	}

}
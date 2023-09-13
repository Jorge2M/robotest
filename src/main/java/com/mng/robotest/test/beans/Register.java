package com.mng.robotest.test.beans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;

public class Register implements Serializable {

	private static final long serialVersionUID = 1L;
	
	String nuevo; 
	String cpost;
	String publi;
	
	public String getNuevo() {
		return this.nuevo;
	}
	
	@XmlAttribute(name="nuevo")
	public void setNuevo(String nuevo) {
		this.nuevo = nuevo;
	}

	public boolean isNewRegister() {
		return this.nuevo.compareTo("S")==0;
	}
	
	public String getCpost() {
		return this.cpost;
	}
	
	@XmlAttribute(name="cpost")
	public void setCpostal(String cpost) {
		this.cpost = cpost;
	}
	
	public boolean isCpost() {
		return this.cpost.compareTo("S")==0;
	}
	
	public String getpubli() {
		return this.publi;
	}
	
	@XmlAttribute(name="publi")
	public void setpubli(String publi) {
		this.publi = publi;
	}
	
	public boolean ispubli() {
		return this.publi.compareTo("S")==0;
	}	
}

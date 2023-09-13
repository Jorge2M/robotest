package com.mng.robotest.test.beans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;

public class Register implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@XmlAttribute(name="nuevo", required=true)
	public String nuevo; 
	@XmlAttribute(name="cpost", required=true)
	public String cpost;
	@XmlAttribute(name="publi", required=true)
	public String publi;
	
	public String getNuevo() {
		return this.nuevo;
	}
	
//	@XmlAttribute(name="nuevo", required=true)
//	public void setNuevo(String nuevo) {
//		this.nuevo = nuevo;
//	}
//
	public boolean isNewRegister() {
		return this.nuevo.compareTo("S")==0;
	}
	
	public String getCpost() {
		return this.cpost;
	}
	
//	@XmlAttribute(name="cpost", required=true)
//	public void setCpost(String cpost) {
//		this.cpost = cpost;
//	}
//	
	public boolean isCpost() {
		return this.cpost.compareTo("S")==0;
	}
	
	public String getPubli() {
		return this.publi;
	}
	
//	@XmlAttribute(name="publi", required=true)
//	public void setPubli(String publi) {
//		this.publi = publi;
//	}
//	
	public boolean isPubli() {
		return this.publi.compareTo("S")==0;
	}	
}

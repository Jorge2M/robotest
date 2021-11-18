package com.mng.robotest.test80.mango.test.beans;

import java.io.Serializable;

import javax.xml.bind.annotation.*;

/**
 * @author jorge.munoz
 *
 */

public class Tienda implements Serializable {

	private static final long serialVersionUID = 7895392091251192587L;
	
	String nuevo;
	String rebajas;
	String she;
	String he;
	String kids;
	String baby;
	String teen;
	String edits;
	
	public String getNuevo() {
		return this.nuevo;
	}
		
	@XmlAttribute(name="nuevo")
	public void setNuevo(String nuevo) {
		this.nuevo = nuevo;
	}	
	
	public String getRebajas() {
		return this.rebajas;
	}
	
	@XmlAttribute(name="rebajas")
	public void setRebajas(String rebajas) {
		this.rebajas = rebajas;
	}		
	
	public String getShe() {
		return this.she;
	}
	
	@XmlAttribute(name="she")
	public void setShe(String she) {
		this.she = she;
	}
	
	public String getHe() {
		return this.he;
	}
	
	@XmlAttribute(name="he")
	public void setHe(String he) {
		this.he = he;
	}
	
	public String getKids() {
		return this.kids;
	}
	
	@XmlAttribute(name="kids")
	public void setKids(String kids) {
		this.kids = kids;
	}
	
	public String getBaby() {
		return this.baby;
	}
	
	@XmlAttribute(name="baby")
	public void setBaby(String baby) {
		this.baby = baby;
	}
	
	public String getTeen() {
		return this.teen;
	}
	
	@XmlAttribute(name="teen")
	public void setTeen(String teen) {
		this.teen = teen;
	}	
	
	public String getEdits() {
		return this.edits;
	}
		
	@XmlAttribute(name="edits")
	public void setEdits(String edits) {
		this.edits = edits;
	}	
	
	@Override
	public String toString() {
		return "Tienda [nuevo=" + this.nuevo + ", rebajas=" + this.rebajas + ", she="+ this.she + ", he=" + this.he + ", kids=" + this.kids + ", baby=" + this.baby + ", teen =" + this.teen + ", edits =" + this.edits +
				", toString()=" + super.toString() + "]";
	}
}
package com.mng.robotest.test80.mango.test.factoryes.jaxb;

import javax.xml.bind.annotation.*;

/**
 * @author jorge.munoz
 *
 */

public class Tienda {

    String nuevo;
    String rebajas;
    String she;
    String he;
    String kids;
    String baby;
    String violeta;
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
	
    public String getVioleta() {
        return this.violeta;
    }
	
    @XmlAttribute(name="violeta")
    public void setVioleta(String violeta) {
        this.violeta = violeta;
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
        return "Tienda [nuevo=" + this.nuevo + ", rebajas=" + this.rebajas + ", she="+ this.she + ", he=" + this.he + ", kids=" + this.kids + ", baby=" + this.baby + ", violeta =" + this.violeta + ", edits =" + this.edits +
                ", toString()=" + super.toString() + "]";
    }
}
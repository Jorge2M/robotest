package com.mng.robotest.test80.mango.test.factoryes.jaxb;

import java.io.Serializable;

import javax.xml.bind.annotation.*;


@XmlRootElement
public class Tpv implements Serializable {

	private static final long serialVersionUID = -11661968618489661L;
	
	String id;
	String clase;
    String estado;
    String notificaciones;

    public String getId() {
        return this.id;
    }
    
    @XmlAttribute(name="id")
    public void setId(String id) {
        this.id = id;
    }
    
    public String getClase() {
    	return this.clase;
    }

    @XmlAttribute(name="clase")
    public void setTipo(String clase) {
        this.clase = clase;
    }        
	
    public String getEstado() {
        return this.estado;
    }

    @XmlAttribute(name="estado")
    public void setEstado(String estado) {
        this.estado = estado;
    }
	
    public String getNotificaciones() {
        return this.notificaciones;
    }

    @XmlAttribute(name="notificaciones")
    public void setNotificaciones(String notificaciones) {
        this.notificaciones = notificaciones;
    }
    
    @Override
    public String toString() {
        return "Pais [id="+ this.id + ", clase=" + this.clase + ", estado=" + this.estado + ", notificaciones=" + this.notificaciones + 
                ", toString()=" + super.toString() + "]";
    }
}

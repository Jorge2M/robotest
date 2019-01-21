package com.mng.robotest.test80.mango.test.factoryes.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("javadoc")
@XmlRootElement
public class Continente {
    String nombre_continente;
    List<Pais> paises;

    public String getNombre_continente() {
        return this.nombre_continente;
    }
    
    @XmlAttribute(name="nombre_continente")
    public void setNombre_continente(String nombre_continente) {
        this.nombre_continente = nombre_continente;
    }       
        
    public List<Pais> getPaises() {
        return this.paises;
    }

    @XmlElement(name="pais")
    public void setPaises(List<Pais> paises) {
        this.paises = paises;
    }

    @Override
    public String toString() {
        return "Continente [nombre_continente=" + this.nombre_continente + ",paises=" + this.paises + ", toString()="
                + super.toString() + "]";
    }
}
package com.mng.robotest.test80.mango.test.factoryes.jaxb;

import java.net.URI;

import javax.xml.bind.annotation.*;

import com.mng.robotest.test80.mango.test.data.CodigoIdioma;


public class IdiomaPais {

    String acceso;
    CodigoIdioma codigo;
	
    public String getAcceso() {
        return this.acceso;
    }
	
    @XmlAttribute(name="acceso")
    public void setAcceso(String acceso) {
        this.acceso = acceso;
    }
	
    public CodigoIdioma getCodigo() {
        return this.codigo;
    }
    
    @XmlValue
    public void setCodigoIdioma(String codigo) {
        this.codigo = CodigoIdioma.valueOf(codigo);
    }
        
    //Función que obtiene la URL de acceso al país/idioma
    public String getUrlIdioma(String urlBase) throws Exception {
        URI uri = new URI(urlBase);
        return (urlBase.replace(uri.getPath(), "") + "/" + getAcceso());            
    }
    
    public String getLiteral() {
    	return (getCodigo().getLiteral());
    }
        
    @Override
    public String toString() {
        return "IdiomaPais [acceso="+ this.acceso + ", codigo=" + this.codigo + 
                ", toString()=" + super.toString() + "]";
    }
}
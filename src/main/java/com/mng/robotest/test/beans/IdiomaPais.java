package com.mng.robotest.test.beans;

import java.io.Serializable;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import javax.xml.bind.annotation.*;

import com.mng.robotest.conf.AppEcom;
import com.mng.robotest.test.data.CodIdioma;

public class IdiomaPais implements Serializable {

	private static final long serialVersionUID = 8191808970314215622L;
	
	String acceso;
	String tiendas;
	CodIdioma codigo;
	
	public String getAcceso() {
		return this.acceso;
	}
	
	@XmlAttribute(name="acceso")
	public void setAcceso(String acceso) {
		this.acceso = acceso;
	}
	
	@XmlAttribute(name="tiendas", required=false)
	public void setTiendas(String tiendas) {
		this.tiendas = tiendas;
	}		
	
	public String getTiendas() {
		return this.tiendas;
	}
	
	List<AppEcom> getTiendasList() {
		if (tiendas==null) {
			return Arrays.asList(AppEcom.values());
		}
		if ("".compareTo(tiendas)==0) {
			return Arrays.asList();
		}
		return Stream.of(tiendas.split(","))
				.map(a -> AppEcom.valueOf(a)).toList();
	}

	
	public CodIdioma getCodigo() {
		return this.codigo;
	}

	@XmlValue
	public void setCodigoIdioma(String codigo) {
		this.codigo = CodIdioma.valueOf(codigo);
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
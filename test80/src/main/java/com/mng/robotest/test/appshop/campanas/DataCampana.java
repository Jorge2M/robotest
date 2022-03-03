package com.mng.robotest.test.appshop.campanas;

public class DataCampana {
	public enum AtributoCampana {Codigo_Pais, Nombre_Pais, Codigo_Idioma, Linea, Posicion, URL, SRC, Destino, All_Text}
	public String codPais;
	public String namePais;
	public String codIdioma;
	public String linea;
	public String posicion;
	public String urlBanner;
	public String srcBanner;
	public String destBanner;
	public String textoBanner;
	
	public String getAtributo(AtributoCampana atributo) {
		switch (atributo) {
		case Codigo_Pais:
			return codPais;
		case Nombre_Pais:
			return namePais;
		case Codigo_Idioma:
			return codIdioma;
		case Linea:
			return linea;
		case Posicion:
			return posicion;
		case URL:
			return urlBanner;
		case SRC:
			return srcBanner;
		case Destino:
			return destBanner;
		case All_Text:
			return textoBanner;
		default:
			return "";
		}
	}
}

package com.mng.robotest.tests.domains.ficha.beans;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mng.robotest.tests.domains.ficha.pageobjects.nogenesis.TipoImagenProducto;


public class DataFoto {
	private static final String SRC_REGEX_GENERAL = ".*?rcs/pics/static/T(.\\d?)/fotos(|/pasarela|/outfit)/S(.\\d?)/(|A\\d/)(.\\d+)_(\\w{2})_*(.*|.?).(jpg|png).*";
	private static final String SRC_REGEX_PROPIOS = ".*?rcs/pics/static/T(.\\d?)/colv3/(.\\d+)_(.\\d+)_*(.*|.?).(jpg|png).*";
	private static final String SRC_REGEX_VIDEOS = ".*?rcs/pics/static/T(.\\d?)/videos/(.*)/(.\\d+)_(.\\d+).(mp4|avi).*";
	private static final String SRC_REGEX_BASE = ".*?/col_nrf_v3/(.\\d+)_(.\\d+).(jpg|png).*";
	
	private String srcFoto = "";
	private String temporada = ""; 
	private String size = "";
	private String referencia = ""; 
	private String color = "";
	private TipoImagenProducto typeImage;  
	
	public DataFoto(String srcFoto) {
		this.srcFoto = srcFoto;
		if (getDataFotoBase()) {
			return;
		}
		if (getDataFotoPropios()) {
			return;
		}
		if (getDataFotoVideos()) {
			return;		
		}
		if (getDataFotoGeneral()) {
			return;
		}
	}
	
	private boolean getDataFotoGeneral() {
		Pattern pattern = Pattern.compile(SRC_REGEX_GENERAL);
		Matcher matcher = pattern.matcher(this.srcFoto);
		if (!matcher.matches()) {
			return false;
		}
		
		this.temporada = matcher.group(1);  
		this.size = matcher.group(3);
		this.referencia = matcher.group(5);
		this.color = matcher.group(6);
		if ("".compareTo(matcher.group(7))==0) {
			this.typeImage = getTipoForBlankSufix();
		} else {
			this.typeImage = TipoImagenProducto.getTipoImagenProductoFromSufijo(matcher.group(7));
		}
		
		return true;
	}	
	
	private boolean getDataFotoPropios() {
		Pattern pattern = Pattern.compile(SRC_REGEX_PROPIOS);
		Matcher matcher = pattern.matcher(this.srcFoto);
		if (!matcher.matches()) {
			return false;
		}
		
		this.temporada = matcher.group(1);  
		this.referencia = matcher.group(2);
		this.color = matcher.group(3);
		if ("".compareTo(matcher.group(4))==0) {
			this.typeImage = getTipoForBlankSufix();
		} else {
			this.typeImage = TipoImagenProducto.getTipoImagenProductoFromSufijo(matcher.group(4));
		}
		
		return true;		
	}
	
	private boolean getDataFotoVideos() {
		Pattern pattern = Pattern.compile(SRC_REGEX_VIDEOS);
		Matcher matcher = pattern.matcher(this.srcFoto);
		if (!matcher.matches()) {
			return false;
		}
		
		this.temporada = matcher.group(1);  
		this.referencia = matcher.group(3);
		this.color = matcher.group(4);
		this.typeImage = TipoImagenProducto.VIDEO;
		return true;		
	}
	
	private boolean getDataFotoBase() {
		Pattern pattern = Pattern.compile(SRC_REGEX_BASE);
		Matcher matcher = pattern.matcher(this.srcFoto);
		if (!matcher.matches()) {
			return false;
		}
		
		this.referencia = matcher.group(1);
		this.color = matcher.group(2);
		this.typeImage = TipoImagenProducto.BASE;
		return true;		
	}
	
	private TipoImagenProducto getTipoForBlankSufix() {
		if (this.srcFoto.contains("/pasarela/")) {
			return TipoImagenProducto.PASARELA;
		}
		if (this.srcFoto.contains("/videos/")) {
			return TipoImagenProducto.VIDEO;
		}
		if (this.srcFoto.contains("col_nrf_v3")) {
			return TipoImagenProducto.BASE;
		}
			
		return TipoImagenProducto.DETALLES;		
	}

	public String getSrcFoto() {
		return srcFoto;
	}

	public String getTemporada() {
		return temporada;
	}

	public String getSize() {
		return size;
	}

	public String getReferencia() {
		return referencia;
	}

	public String getColor() {
		return color;
	}

	public TipoImagenProducto getTypeImage() {
		return typeImage;
	}
}

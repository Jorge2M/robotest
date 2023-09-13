package com.mng.robotest.test.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.*;

import com.mng.robotest.conf.AppEcom;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.test.data.Constantes.ThreeState;


public class Shoponline implements Serializable {
	
	private static final long serialVersionUID = -8214276147980655399L;

	//Líneas correspondientes a las 5 básicas ("kids" sigue apareciendo en algunos países sin venta como Bolivia) 
	public enum LineaBasica { she, he, nina, nino, kids, teen }	

	@XmlElement(name="linea") 
	List<Linea> Lineas = new LinkedList<>();
	
	public List<Linea> getLineas() {
		return this.Lineas;
	}
	
	public List<Linea> getLineasToTest(AppEcom app) {
		List<Linea> lineasToTest = new ArrayList<>();
		for (Linea linea : getLineas()) {	
			if (stateLinea(linea, app)==ThreeState.TRUE) {
				lineasToTest.add(linea);
			}
		}
		return lineasToTest;
	}
	
	/**
	 * @param linea
	 * @return si existe la línea: TRUE, FALSE, UNKNOW 
	 */
	public ThreeState stateLinea(Linea linea, AppEcom app) {
		ThreeState stateLinea = ThreeState.UNKNOWN;
		String stateLineaStr = "";
		if (linea!=null) {
			if (app==AppEcom.outlet) {
				stateLineaStr = linea.getOutlet();
			}
			else {
				stateLineaStr = linea.getShop();
			}
		}
		
		switch (stateLineaStr) {
		case "s":
			stateLinea = ThreeState.TRUE;
			break;
		case "n":
			stateLinea = ThreeState.FALSE;
			break;
		default:
			break;
		}
		
		return stateLinea;
	}
	
	public ThreeState stateLinea(LineaType lineaType, AppEcom app) {
		Linea linea = this.getLinea(lineaType);
		return (stateLinea(linea, app));
	}
	
	//Identifica si la línea pertenece a una de las 5 líneas de tipo 'tienda' (she, he, nino, nina, teen)
	public boolean isLineaTienda(Linea linea) {
		for (LineaBasica lineaTda : LineaBasica.values()) {
			if (lineaTda.name().compareTo(linea.getType().name())==0) {
				return true;
			}
		}
		
		return false;
	}
	
	public Linea getLinea(LineaType lineaId) {
		Linea lineaRet = null;
		boolean encontrada = false;
		Iterator<Linea> it = this.Lineas.iterator();
		while (!encontrada && it.hasNext()) {
			Linea lineaTmp = it.next();
			if (lineaTmp.getType() == lineaId) {
				lineaRet = lineaTmp;
				encontrada = true;
			}
		}
		
		return lineaRet;			
	}
	
	public int getNumLineas(AppEcom app) {
		int numLineas = 0;
		for (Linea linea : this.Lineas) {
			if (this.stateLinea(linea, app)==ThreeState.TRUE) {
				numLineas+=1;
			}
		}
		
		return numLineas;
	}
	
	/**
	 * Identifica el número de líneas con tienda asociada (she, he, kids, teen)
	 */
	public List<Linea> getListLineasTiendas(AppEcom app) {
		List<Linea> listLineas = new ArrayList<>();
		for (Linea linea : this.Lineas) {
			if (this.stateLinea(linea, app)==ThreeState.TRUE && this.isLineaTienda(linea)) {
				listLineas.add(linea);
			}
		}
		
		return listLineas;
	}		
	
	public int getNumLineasTiendas(AppEcom app) {
		return (getListLineasTiendas(app).size());
	}
}
package com.mng.robotest.tests.domains.compra.pageobjects.beans;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


public class DataDireccion {
	
	public enum DataDirType {
		NIF, NAME, APELLIDOS, DIRECCION, CODPOSTAL, CODIGOPAIS, 
		POBLACION, PROVINCIA, EMAIL, TELEFONO
	}
	
	private LinkedHashMap<DataDirType, String> dataDireccion = new LinkedHashMap<>();
		
	public void put(DataDirType typeData, String data) {
		this.dataDireccion.put(typeData, data);
	}
	
	public String getValue(DataDirType typeData) {
		return (this.dataDireccion.get(typeData));
	}
	
	public Map<DataDirType, String> getDataDireccion() {
		return this.dataDireccion;
	}
	
	public String getFormattedHTMLData() {
		String dataHTML = "";
		Iterator<Map.Entry<DataDirType,String>> it = getDataDireccion().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<DataDirType,String> pair = it.next();
			dataHTML+="Type Data: <b>" + pair.getKey() + "</b>, Datos: <b>" + pair.getValue() + "</b><br>";
		}
		
		dataHTML = replaceLast(dataHTML, "<br>", "");
		return dataHTML;
	}
	
	private static String replaceLast(String text, String regex, String replacement) {
		return text.replaceFirst("(?s)"+regex+"(?!.*?"+regex+")", replacement);
	}
}

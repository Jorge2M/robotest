package com.mng.robotest.test80.mango.test.appshop.campanas;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class CampanasData {
	ArrayList<DataCampana> listCampanas;
	
	abstract public void loadCampanas(String source) throws Exception;
	
	public ArrayList<DataCampana> getListCampanas() {
		return listCampanas;
	}
	
	public void setListCampanas(ArrayList<DataCampana> listCampanas) {
		this.listCampanas = listCampanas;
	}
	
	public ArrayList<DataCampana> getListCampanas(String codPais, String codIdioma, String linea) {
		ArrayList<DataCampana> listCampanasResult = new ArrayList<>();
		for (DataCampana dataCampana : listCampanas) {
			if (dataCampana.codPais.compareTo(codPais)==0 &&
				dataCampana.codIdioma.compareTo(codIdioma)==0 &&
				dataCampana.linea.compareTo(linea)==0)
				listCampanasResult.add(dataCampana);
		}
		
		return listCampanasResult;		
	}
	
	public HashSet<String> getListCodPais() {
		HashSet<String> listCountrys = new HashSet<>();
		for (DataCampana dataCampana : listCampanas)
			listCountrys.add(dataCampana.codPais);
		
		return listCountrys;
	}
	
	public HashSet<String> getListLines() {
		HashSet<String> listLines = new HashSet<>();
		for (DataCampana dataCampana : listCampanas)
			listLines.add(dataCampana.linea);
		
		return listLines;		
	}
	
	public HashSet<String> getListIdiomas(String codPais) {
		HashSet<String> listIdiomas = new HashSet<>();
		for (DataCampana dataCampana : listCampanas) {
			if (dataCampana.codPais.compareTo(codPais)==0) {
				listIdiomas.add(dataCampana.codIdioma);
			}
		}
		
		return listIdiomas;		
	}
	
	public HashSet<String> getListLines(String codPais) {
		HashSet<String> listLines = new HashSet<>();
		for (DataCampana dataCampana : listCampanas) {
			if (dataCampana.codPais.compareTo(codPais)==0) {
				listLines.add(dataCampana.linea);
			}
		}
		
		return listLines;		
	}
	
	public HashSet<String> getListLines(String codPais, String codIdioma) {
		HashSet<String> listLines = new HashSet<>();
		for (DataCampana dataCampana : listCampanas) {
			if (dataCampana.codPais.compareTo(codPais)==0 &&
				dataCampana.codIdioma.compareTo(codIdioma)==0)
				listLines.add(dataCampana.linea);
		}
		
		return listLines;		
	}
}

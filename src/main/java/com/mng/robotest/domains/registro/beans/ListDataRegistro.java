package com.mng.robotest.domains.registro.beans;

import java.util.ArrayList;
import java.util.List;


public class ListDataRegistro {
	
	public enum DataRegType {NAME, APELLIDOS, EMAIL, PASSWORD, TELEFONO, CODPOSTAL, CODPAIS, NUMLINEAS, LINEASCOMASEPARATED, CLICKLINEAS, DIRECCION, POBLACION, PROVINCIA}
	public enum DataPageIni {NAME, APELLIDOS, EMAIL, PASSWORD, TELEFONO, CODPOSTAL, CODPAIS}
	public enum DataPageDirec {PAIS, DIRECCION, CODPOSTAL, POBLACION, PROVINCIA}
	public enum PageData {PAGEINICIAL, PAGEDIRECCION}
	
	private List<DataRegistro> dataRegistro = new ArrayList<>();
	
	public void add(DataRegType inputType, String data, boolean valid) {
		add(new DataRegistro(inputType, data, valid));
	}
	
	public void add(DataRegistro dataInput) {
		this.dataRegistro.add(dataInput);
	}
	
	public List<DataRegistro> getDataRegistro() {
		return this.dataRegistro;
	}
	
	/**
	 * @return los datos pertenecientes a la página inicial del registro
	 */
	public List<DataRegistro> getDataPageInicial() {
		List<DataRegistro> listDataPageIni = new ArrayList<>();
		for (DataRegistro dataReg : this.dataRegistro) {
			if (isDataPageIni(dataReg.getDataRegType())) {
				listDataPageIni.add(dataReg);
			}
		}
		
		return listDataPageIni;
	} 
	
	/**
	 * @return los datos pertenecientes a la página de introducción de los datos de la dirección
	 */
	public List<DataRegistro> getDataPageDirec() {
		List<DataRegistro> listDataPageDirec = new ArrayList<>();
		for (DataRegistro dataReg : this.dataRegistro) {
			if (isDataPageDirec(dataReg.getDataRegType())) {
				listDataPageDirec.add(dataReg);
			}
		}
		return listDataPageDirec;
	}	
	
	/**
	 * @return si un dato del registro se corresponde con la página inicial
	 */
	private boolean isDataPageIni(DataRegType dataReg) {
		for (DataPageIni dataPageIni : DataPageIni.values()) {
			if (dataReg.name().compareTo(dataPageIni.name())==0) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * @return si un dato del registro se corresponde con la página inicial
	 */
	private boolean isDataPageDirec(DataRegType dataReg) {
		for (DataPageDirec dataPageDirec : DataPageDirec.values()) {
			if (dataReg.name().compareTo(dataPageDirec.name())==0) {
				return true;
			}
		}
		
		return false;
	}	
	
	public DataRegistro getData(DataRegType typeData) {
		DataRegistro dataToReturn = null;
		for (DataRegistro data : this.dataRegistro) {
			if (data.getDataRegType() == typeData) {
				dataToReturn = data;
				break;
			}
		}
		
		return dataToReturn;
	}
	
	public String getFormattedHTMLData(PageData pageData) {
		StringBuilder dataHtml = new StringBuilder();
		List<DataRegistro> dataToPrint = new ArrayList<>();
		
		switch (pageData) {
		case PAGEINICIAL:
			dataToPrint = getDataPageInicial();
			break;
		case PAGEDIRECCION:
			dataToPrint = getDataPageDirec();
			break;
		default:
			break;
		}
		
		for (DataRegistro dataInput : dataToPrint) {
			dataHtml.append(dataInput.getFormattedHTMLData() + "<br>");
		}
		
		return (replaceLast(dataHtml.toString(), "<br>", ""));
	}
	
	private static String replaceLast(String text, String regex, String replacement) {
		return text.replaceFirst("(?s)"+regex+"(?!.*?"+regex+")", replacement);
	}
}

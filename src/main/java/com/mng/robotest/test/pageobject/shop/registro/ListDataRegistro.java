package com.mng.robotest.test.pageobject.shop.registro;

import java.util.ArrayList;


public class ListDataRegistro {
	
	public enum DataRegType {name, apellidos, email, password, telefono, codpostal, codpais, numlineas, lineascomaseparated, clicklineas, direccion, poblacion, provincia}
	public enum DataPageIni {name, apellidos, email, password, telefono, codpostal, codpais}
	public enum DataPageDirec {pais, direccion, codpostal, poblacion, provincia}
	public enum PageData {pageInicial, pageDireccion}
	
	private ArrayList<DataRegistro> dataRegistro = new ArrayList<>();
	
	public void add(DataRegType inputType, String data, boolean valid) {
		add(new DataRegistro(inputType, data, valid));
	}
	
	public void add(DataRegistro dataInput) {
		this.dataRegistro.add(dataInput);
	}
	
	public ArrayList<DataRegistro> getDataRegistro() {
		return this.dataRegistro;
	}
	
	/**
	 * @return los datos pertenecientes a la página inicial del registro
	 */
	public ArrayList<DataRegistro> getDataPageInicial() {
		ArrayList<DataRegistro> listDataPageIni = new ArrayList<>();
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
	public ArrayList<DataRegistro> getDataPageDirec() {
		ArrayList<DataRegistro> listDataPageDirec = new ArrayList<>();
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
		String dataHTML = "";
		ArrayList<DataRegistro> dataToPrint = new ArrayList<>();
		
		switch (pageData) {
		case pageInicial:
			dataToPrint = getDataPageInicial();
			break;
		case pageDireccion:
			dataToPrint = getDataPageDirec();
			break;
		default:
			break;
		}
		
		for (DataRegistro dataInput : dataToPrint) 
			dataHTML+=dataInput.getFormattedHTMLData() + "<br>";
		
		dataHTML = replaceLast(dataHTML, "<br>", "");
		return dataHTML;
	}
	
	private static String replaceLast(String text, String regex, String replacement) {
		return text.replaceFirst("(?s)"+regex+"(?!.*?"+regex+")", replacement);
	}
}

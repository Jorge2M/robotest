package com.mng.robotest.test80.mango.test.appshop.campanas;

import java.io.File;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Row;

public class CampanasExcel extends CampanasData {
	public enum TypeCell {
		CodPais("Código país"),
		LitPais("País"),
		CodIdioma("Idioma"),
		Linea("Línea"),
		PosicBanner("Posición"),
		UrlBanner("URL"),
		SrcBanner("SRC"),
		DestBanner("Destino"),
		TextoBanner("Texto");
		
		String encabezado;
		private TypeCell(String encabezado) {
			this.encabezado = encabezado;
		}
		
		public String getEncabezado() {
			return this.encabezado;
		}
		
		public static TypeCell getTypeCell(String encabezado) {
			for (TypeCell typeCell : TypeCell.values()) {
				if (encabezado.compareTo(typeCell.getEncabezado())==0)
					return typeCell;
			}
			
			return null;
		}
	}
	
	@Override
	public void loadCampanas(String filePathExcel) throws Exception {
		ArrayList<DataCampana> listCampanas = getListCamapansFromExcel(filePathExcel);
		super.setListCampanas(listCampanas);
	}
	
	private ArrayList<DataCampana> getListCamapansFromExcel(String filePathExcel) throws Exception {
		ArrayList<DataCampana> listCampanas = new ArrayList<DataCampana>(); 
    	File excelFile = new File(filePathExcel);
        Workbook workbook = WorkbookFactory.create(excelFile);
        Sheet sheet = workbook.getSheetAt(0);

        //Navigate throughout the row/cells
        for (int i=0; i<sheet.getPhysicalNumberOfRows(); i++) {
        	if (i>0) {
        		Row row = sheet.getRow(i);
	        	DataCampana dataCampana = new DataCampana();
	            for(int j=0; j<row.getPhysicalNumberOfCells(); j++) {
	            	Cell cell = row.getCell(j);
            		mapCellIntoDataCampana(cell, dataCampana);
	            }
	            
	            listCampanas.add(dataCampana);
        	}
        }
        
        workbook.close();		
        return listCampanas;
	}
	
	private void mapCellIntoDataCampana(Cell cell, DataCampana dataCampana) {
		DataFormatter dataFormatter = new DataFormatter();
		TypeCell typeCell = getTypeCell(cell);
		String cellValue = dataFormatter.formatCellValue(cell);
		switch (typeCell) {
		case CodPais:
			dataCampana.codPais = cellValue;
			break;
		case LitPais:
			dataCampana.namePais = cellValue;
			break;
		case CodIdioma:
			dataCampana.codIdioma = cellValue;
			break;
		case Linea:
			dataCampana.linea = cellValue;
			break;
		case PosicBanner:
			dataCampana.posicion = cellValue;
			break;
		case UrlBanner:
			dataCampana.urlBanner = cellValue;
			break;
		case SrcBanner:
			dataCampana.srcBanner = cellValue;
			break;
		case DestBanner:
			dataCampana.destBanner = cellValue;
			break;
		case TextoBanner:
			dataCampana.textoBanner = cellValue;
			break;
		}
	}
	
	private TypeCell getTypeCell(Cell cell) {
		DataFormatter dataFormatter = new DataFormatter();
		Row headerRow = cell.getSheet().getRow(0);
		int columnCell = cell.getColumnIndex();
		String titleColumn = dataFormatter.formatCellValue(headerRow.getCell(columnCell));
		return (TypeCell.getTypeCell(titleColumn));		
	}
}

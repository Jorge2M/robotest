//package com.mng.robotest.test80.mango.test.appshop.campanas;
//
//import org.junit.Test;
//import static org.junit.Assert.*;
//import org.junit.runner.RunWith;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PowerMockIgnore;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//
//import com.mng.robotest.test80.mango.test.appshop.campanas.CampanasExcel.TypeCell;
//
//import static org.mockito.Matchers.any;
//import java.io.File;
//import java.util.ArrayList;
//import java.util.Arrays;
//
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellType;
//import org.apache.poi.ss.usermodel.RichTextString;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.ss.usermodel.WorkbookFactory;
//import org.apache.poi.ss.usermodel.Row;
//
//@RunWith(PowerMockRunner.class)
//@PowerMockIgnore("javax.management.*")
//@PrepareForTest({WorkbookFactory.class, CampanasExcel.class})
//public class CampanasExcelTest {
//	final static String nameExcel = "PlantillaBanners.xls";
//	final static ArrayList<String> rowHeader = new ArrayList<>(Arrays.asList(
//			TypeCell.CodPais.getEncabezado(),
//			TypeCell.LitPais.getEncabezado(),
//			TypeCell.CodIdioma.getEncabezado(),
//			TypeCell.Linea.getEncabezado(),
//			TypeCell.PosicBanner.getEncabezado(),
//			TypeCell.UrlBanner.getEncabezado(),
//			TypeCell.SrcBanner.getEncabezado(),
//			TypeCell.DestBanner.getEncabezado(),
//			TypeCell.TextoBanner.getEncabezado()
//	));
//			
//	final static ArrayList<String> row1Data = new ArrayList<>(Arrays.asList(
//			"001", 
//			"España", 
//			"ES", 
//			"she", 
//			"1", 
//			"http://shop.mango.com/redirect.faces?op=conta&externa=lookbook2v1", 
//			"http://staticpages.mngbcn.com/homes/images/home/assets/she/ss15/mayo/video/MangoLookbookSS15_2.mp4",
//			"http://shop.mango.com/iframe.faces?state=she_001_ES",
//			"Lookbook verano"));
//	
//	@Test
//	public void testExcelHeaderAnd1DataLine() throws Exception {
//		ArrayList<ArrayList<String>> listOfRowsExcel = new ArrayList<>();
//		listOfRowsExcel.add(rowHeader);
//		listOfRowsExcel.add(row1Data);
//		mockExcel(listOfRowsExcel);
//		
//		//Code to Test
//    	CampanasData dataCamp = new CampanasExcel();
//    	dataCamp.loadCampanas(nameExcel);
//    	
//    	//Validations
//    	ArrayList<DataCampana> listCampanas = dataCamp.getListCampanas();
//    	assertTrue(listCampanas.size()==1);
//    	
//    	DataCampana dataCampana1 = listCampanas.get(0);
//    	assertTrue(dataCampana1.codPais.compareTo(row1Data.get(0))==0);
//    	assertTrue(dataCampana1.namePais.compareTo(row1Data.get(1))==0);
//    	assertTrue(dataCampana1.codIdioma.compareTo(row1Data.get(2))==0);
//    	assertTrue(dataCampana1.linea.compareTo(row1Data.get(3))==0);
//    	assertTrue(dataCampana1.posicion.compareTo(row1Data.get(4))==0);
//    	assertTrue(dataCampana1.urlBanner.compareTo(row1Data.get(5))==0);
//    	assertTrue(dataCampana1.srcBanner.compareTo(row1Data.get(6))==0);
//    	assertTrue(dataCampana1.destBanner.compareTo(row1Data.get(7))==0);
//    	assertTrue(dataCampana1.textoBanner.compareTo(row1Data.get(8))==0);
//	}
//	
//	private static void mockExcel(ArrayList<ArrayList<String>> listOfRowsExcel) throws Exception {
//		//Mock Workbook and Sheet
//		PowerMockito.mockStatic(WorkbookFactory.class);
//		Workbook mockWorkbook = PowerMockito.mock(Workbook.class);
//		PowerMockito.when(WorkbookFactory.create(any(File.class))).thenReturn(mockWorkbook);
//		Sheet mockSheet = PowerMockito.mock(Sheet.class);
//		PowerMockito.when(mockWorkbook.getSheetAt(0)).thenReturn(mockSheet);
//		
//		//Mock Rows
//		Row mockRowHeader = mockRow(rowHeader, mockSheet);
//		PowerMockito.when(mockSheet.getRow(0)).thenReturn(mockRowHeader);
//		Row mockRow1Data = mockRow(row1Data, mockSheet);
//		PowerMockito.when(mockSheet.getRow(1)).thenReturn(mockRow1Data);
//		PowerMockito.when(mockSheet.getPhysicalNumberOfRows()).thenReturn(2);
//		
//	}
//	
//	private static Row mockRow(ArrayList<String> listOfCellValues, Sheet mockSheet) throws Exception {
//		Row mockRow = PowerMockito.mock(Row.class);
//		int numberCells = listOfCellValues.size();
//		PowerMockito.when(mockRow.getPhysicalNumberOfCells()).thenReturn(numberCells);
//		for (int i=0; i<listOfCellValues.size(); i++) {
//			String cellValue = listOfCellValues.get(i);
//			Cell mockCell = mockCell(cellValue, i, mockSheet);
//			PowerMockito.when(mockRow.getCell(i)).thenReturn(mockCell);
//		}
//		
//		return mockRow;
//	}
//	
//	private static Cell mockCell(String cellValue, int cellColumn, Sheet mockSheet) {
//		Cell mockCell = PowerMockito.mock(Cell.class);
//		RichTextString mockRichText = PowerMockito.mock(RichTextString.class);
//		PowerMockito.when(mockCell.getCellTypeEnum()).thenReturn(CellType.STRING);
//		PowerMockito.when(mockCell.getStringCellValue()).thenReturn(cellValue);
//		PowerMockito.when(mockRichText.getString()).thenReturn(cellValue);
//		PowerMockito.when(mockCell.getColumnIndex()).thenReturn(cellColumn);
//		PowerMockito.when(mockCell.getRichStringCellValue()).thenReturn(mockRichText);
//		PowerMockito.when(mockCell.getSheet()).thenReturn(mockSheet);
//		return mockCell;
//	}
//}

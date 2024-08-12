package com.mng.robotest.testslegacy.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.compra.pageobjects.UtilsCheckout;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ImporteScreen {
	
	private ImporteScreen() {}
	
	public static String normalizeImportFromScreen(String screenImport) {
		if (containsArabicNumber(screenImport)) {
			return screenImport.replaceAll("[A-Za-z\\s]", "");
		}
		
		String impToReturn = screenImport.replaceAll("[^\\d.,]", "");
		
		//Remove first and last character if "." o ","
		if (impToReturn.length()>0) {
			String firstChar = impToReturn.substring(0,1);
			if ((".".compareTo(firstChar)==0 || ",".compareTo(firstChar)==0)) {
				impToReturn = impToReturn.substring(1); 
			}
		}
			
		if (impToReturn.length()>0) {
			String lastChar = impToReturn.substring(impToReturn.length()-1,impToReturn.length());
			if (".".compareTo(lastChar)==0 || ",".compareTo(lastChar)==0) {
				impToReturn = impToReturn.substring(0, impToReturn.length()-1);
			}
		}
		
		//Si hay más de un punto eliminamos el 1o
		if (impToReturn.indexOf(".")!=impToReturn.lastIndexOf(".")) {
			impToReturn = impToReturn.replaceFirst("\\.", "");
		}
		if (impToReturn.indexOf(".") < impToReturn.length() - 3) {
			impToReturn = impToReturn.replace(".", "");
		}
		if (impToReturn.indexOf(",") < impToReturn.length() - 3) {
			impToReturn = impToReturn.replace(",", "");
		}
		if (impToReturn.indexOf(".") == impToReturn.length()) {
			impToReturn = impToReturn.replace(".", "");
		}
		return impToReturn;
	}
	
	private static boolean containsArabicNumber(String screenImport) {
        String arabicNumberRegex = "[\\u0660-\\u0669]";
        Pattern pattern = Pattern.compile(arabicNumberRegex);
        Matcher matcher = pattern.matcher(screenImport);
        return matcher.find();
	}
	
	/**
	 * Adapta un importe a un float teniendo en cuenta los diferentes formatos de importe mostrados en MANGO
	 */
	public static float getFloatFromImporteMangoScreen(String screenImport) {
		String importeResult = normalizeImportFromScreen(screenImport);
		if (importeResult.compareTo("") == 0) {
			return (0);
		}
		if (containsArabicNumber(screenImport)) {
			return UtilsCheckout.getArabicNumber(screenImport);
		}
		return Float.parseFloat(importeResult.replace(',', '.'));
	}
	
	/**
	 * Valida si el importe está apareciendo en la pantalla actual
	 */
	public static boolean isPresentImporteInScreen(String importe, String codPais, WebDriver driver) {
		return (isPresentImporteInElements(importe, codPais, "//*", driver));
	}
	
	public static boolean isPresentImporteInElements(String importe, String codPais, String xpathElementsWhereSearch, WebDriver driver) {
		List<String> possibleImports = getPosibleLitImportInScreen(importe, codPais);
		return (isAnyImportInScreen(possibleImports, xpathElementsWhereSearch, driver));
	}
	
	private static boolean isAnyImportInScreen(List<String> possibleImports, String xpathElementsWhereSearch, WebDriver driver) {
		if (isAnyImportInScreenMethodXpath(possibleImports, xpathElementsWhereSearch, driver)) {
			return true;
		}
		int itemsToIterate = 10;
		return (isAnyImportInScreenMethodIterate(possibleImports, xpathElementsWhereSearch, itemsToIterate, driver));
	}
	
	/**
	 * Se buscan los importes en pantalla vía XPath directo
	 */
	private static boolean isAnyImportInScreenMethodXpath(List<String> possibleImports, String xpathElementsWhereSearch, WebDriver driver) {
		String xpathImports = xpathElementsWhereSearch + "//self::*[";
		String litOr = " or ";
		for (String possibleImport : possibleImports) {
			xpathImports+=("node()[contains(.,'" + possibleImport + "')]" + litOr);
		}
		
		xpathImports = replaceLast(xpathImports, litOr, "") + "]";
		return (state(PRESENT, By.xpath(xpathImports), driver).check());
	}
	
	/**
	 * Se buscan los importes en pantalla vía revisión de todos los WebElement
	 */
	private static boolean isAnyImportInScreenMethodIterate(List<String> possibleImports, String xpathElementsWhereSearch, 
															int itemsToIterate, WebDriver driver) {
		List<WebElement> listElementos = driver.findElements(By.xpath(xpathElementsWhereSearch));
		Iterator<WebElement> itElements = listElementos.iterator();
		int i = 0;
		while (itElements.hasNext() && i<itemsToIterate) {
			String textElelments = itElements.next().getText();
			for (String possibleImport : possibleImports) {
				if (textElelments.contains(possibleImport)) {
					return true;
				}
			}
			
			i+=1;
		}
			
		return false;
	}
	
	private static List<String> getPosibleLitImportInScreen(String importe, String codPais) {
		//Posibles importes a nivel General
		List<String> listOfImports = new ArrayList<>();
		listOfImports.add(importe);
		if (importe.contains(".")) {
			listOfImports.add(importe.replace(".", ","));
		}
		if (importe.contains(",")) {
			listOfImports.add(importe.replace(",", "."));
			int ultimaComa = importe.lastIndexOf(",");
			if (importe.length() - ultimaComa == 4) {
				listOfImports.add(importe.replace(",", "")); //Importe sin carácter de miles
			}
		}
		
		listOfImports.add(insertCharInMiles(importe, ' ')); //Importe con un espacio indicando los miles
		
		//Posibles importes específicos de países concretos
		switch (codPais) {
		case "728"/*Corea*/:
			listOfImports.add(importe.replace(",000", "000")); //En corea aparece un importe en la pasarela del tipo 88000 (88,000)
			break;
		case "480"/*Colombia*/:
			listOfImports.add(importe.replace(".", "")); // En Colombia aparece un importe del tipo 111800,00
			break;
		case "064"/*Hungria*/:
			listOfImports.add(insertCharInMiles(importe, ' ')); // En Hungría aparece un importe del tipo 8 785
			listOfImports.add(insertCharInMiles(importe, ',')); // En algunas pasarelas (como Paypal) es de tipo 8,785
			listOfImports.add(insertCharInMiles(importe, '.')); // En algunas pasarelas es de tipo 8.785			
			break;
		case "036":
			int importeSize = importe.length();
			String lastNum = importe.substring(importeSize-1, importeSize);
			if ("0".compareTo(lastNum)==0) {
				listOfImports.add(importe.substring(0, importeSize-1)); //En ocasiones, cuando el 2o es 0, aparece 1 solo decimal
			}
			break;
		case "061"/*Czech Repúblic*/, "720"/*China*/:
			listOfImports.add(insertCharInMiles(importe, ',') + ".00"); //En República Checa y China aparece un importe del tipo 1,598.00
			break;
		default:
		}
		
		return listOfImports;
	}
	
	/**
	 * Inserta un carácter determinado en los puntos de miles de un número
	 */
	private static String insertCharInMiles(String importeTotal, char charToInsert) {
		int k = 0;
		String importeTotalSpaces = "";
		for (int j = importeTotal.length() - 1; j >= 0; j--) {
			char caracter = importeTotal.charAt(j);
			importeTotalSpaces = caracter + importeTotalSpaces;
			if (Character.isDigit(caracter)) {
				k += 1;
				if (k == 3) {
					importeTotalSpaces = charToInsert + importeTotalSpaces;
					k = 0;
				}
			}
		}
		importeTotalSpaces = importeTotalSpaces.trim();
		if (importeTotalSpaces.charAt(0) == charToInsert) {
			importeTotalSpaces = importeTotalSpaces.substring(1);
		}
		return importeTotalSpaces;
	}
	
	private static String replaceLast(String text, String regex, String replacement) {
		return text.replaceFirst("(?s)"+regex+"(?!.*?"+regex+")", replacement);
	}
}

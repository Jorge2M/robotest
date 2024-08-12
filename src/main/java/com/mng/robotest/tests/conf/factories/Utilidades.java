package com.mng.robotest.tests.conf.factories;

import java.util.*;

import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.menus.entity.Linea;
import com.mng.robotest.testslegacy.beans.Pais;


public class Utilidades {

	private Utilidades() {}
	
	/**
	 * Get the lines to test
	 * @param lineasParam list of lines, comma separated
	 */
	public static List<Linea> getLinesToTest(Pais pais, AppEcom appE, String lineasParam) {
		List<Linea> lineasXML = pais.getShoponline().getLineasToTest(appE);
		if ("".compareTo(lineasParam)==0) {
			return lineasXML;
		}
		
		List<Linea> lineasToTest = new ArrayList<>();
		List<String> lineasArray = new ArrayList<>(Arrays.asList(lineasParam.split(",")));
		for (Linea linea : lineasXML) {
			 if (lineasArray.contains(linea.getId())) {
				 lineasToTest.add(linea);
			 }
		}
		
		return lineasToTest;
	}

	public static String rtrim(String s) {
		int i = s.length()-1;
		while (i >= 0 && Character.isWhitespace(s.charAt(i))) {
			i--;
		}
		return s.substring(0,i+1);
	}

	public static String ltrim(String s) {
		int i = 0;
		while (i < s.length() && Character.isWhitespace(s.charAt(i))) {
			System.out.println("s.charAt(i)  "+s.charAt(i));
			i++;
		}
		return s.substring(i);
	}		
	

	
	public static boolean lineaToTest(Linea linea, AppEcom appE) {
		if (appE==AppEcom.outlet && linea.getOutlet().compareTo("s")!=0) {
			return false;
		}
		if (appE!=AppEcom.outlet && linea.getShop().compareTo("s")!=0) {
			return false;
		}
		if (linea.getMenus().compareTo("s")!=0 && !linea.existsSublineas()) {
			return false;
		}
		return true;
	}	
}

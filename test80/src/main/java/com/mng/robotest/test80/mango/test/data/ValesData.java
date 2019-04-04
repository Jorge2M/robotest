package com.mng.robotest.test80.mango.test.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.generic.beans.FactoryVale;
import com.mng.robotest.test80.mango.test.generic.beans.ValePais;
import com.mng.robotest.test80.mango.test.data.PaisShop;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;

//TODO refactorizar para que cada campaña sea una clase
public class ValesData {
	
	public enum Campanya {
		GLAM19("01/04/2019 00:00"),
		VIP18("10/12/2018 00:00"),
		VIPMNG("10/12/2018 00:00"),
		Test("01/01/2000 00:00");
		
		Calendar fechaInit;
		private Campanya(String fechaInitStr) {
			try {
				fechaInit = FactoryVale.fechaToCalendar(fechaInitStr);
			}
			catch (Exception e) {}
		}
		
		public Calendar getFechaInit() {
			return fechaInit;
		}
	}
	
	public static List<ValePais> getListVales(Campanya campanya, boolean filterCal) throws Exception {
		switch (campanya) {
		case GLAM19:
			return getValesGLAM19(campanya, filterCal);
//		case VIP18:
//			return getValesVIP18(campanya, filterCal);
//		case VIPMNG:
//			return getValesVIPMNG(campanya, filterCal);
		default:
			return null;
		}
	}
	
	public static List<ValePais> getValesGLAM19(Campanya campanya, boolean filterCal) throws Exception {
		List<ValePais> listaPaisesVales = new ArrayList<>();
		FactoryVale factoryVale = new FactoryVale(UtilsMangoTest.listaPaisesXML(true, null));
		addValePais(factoryVale.makeWithArticles(campanya, PaisShop.Deutschland, 20, "06/04/2019 00:00", "14/06/2019 00:00", filterCal, Arrays.asList("43067768"), Arrays.asList("41093010")), listaPaisesVales);
		addValePais(factoryVale.makeWithArticles(campanya, PaisShop.Nederland,   20, "06/04/2019 00:00", "14/06/2019 00:00", filterCal, Arrays.asList("43067768"), Arrays.asList("41093010")), listaPaisesVales);
		addValePais(factoryVale.makeWithArticles(campanya, PaisShop.Hungary,     20, "11/04/2019 00:00", "14/06/2019 00:00", filterCal, Arrays.asList("43067768"), Arrays.asList("41093010")), listaPaisesVales);
		addValePais(factoryVale.makeWithArticles(campanya, PaisShop.Austria,     20, "04/04/2019 00:00", "14/06/2019 00:00", filterCal, Arrays.asList("43067768"), Arrays.asList("41093010")), listaPaisesVales);
	    addValePais(factoryVale.makeWithArticles(campanya, PaisShop.Russia,      20, "27/04/2019 00:00", "14/06/2019 00:00", filterCal, Arrays.asList("43085773"), Arrays.asList("41050718")), listaPaisesVales);
	    return listaPaisesVales;
	}
	
	private static String getTextCheckoutVale(ValePais vale) {
		Campanya campanya = vale.getCampanya();
		Pais pais = vale.getPais();
		switch (campanya) {
		case GLAM19:
			return getTextCheckoutGLAM19(pais);
		default:
			return "";
		}
	}
	
	private static String getTextCheckoutGLAM19(Pais pais) {
		switch (pais.getCodigo_pais()) {
		case "004":
			return "GLAMOUR SHOPPING WEEK | -20 % auf die NEUE KOLLEKTION! Sichern Sie sich die Glamour Shopping-Card oder den Online-Code in der Glamour April-Ausgabe. Verleihen Sie Ihrer Garderobe etwas Glamour!";
		case "038":
			return "WOMAN DAY | -20 % auf die NEUE KOLLEKTION! Legen Sie den Gutschein aus der Ausgabe vom 28.03. im Geschäft vor oder nutzen Sie den Online-Code GLAM19. Verpassen Sie diese glamouröse Chance nicht!";
		case "064":
			return "GLAMOUR DAYS | -20% AZ ÚJ KOLLEKCIÓRA! A kupont és az online kódot a Glamour magazin áprilisi számában találhatod.";
		case "003":
			return "NATIONAL GLAMOUR DAY | 20% korting op de NIEUWE COLLECTIE! Je vindt de online code in het Glamour nummer van april. Voeg een tikkeltje glamour aan je garderobe toe!";
		default:
			return ("NATIONAL GLAMOUR DAY | 20% off NEW COLLECTION! Find the online code in the Glamour April issue. Add some glamour to your wardrobe!");
		}
	}
	
	/**
	 * Se añade un ValePais a la lista pero manteniendo algunas una condición:
	 *  - Si existen 2 valesPais con la misma clave (código vale/país) mantendremos sólo el activado  
	 */
	private static void addValePais(ValePais valePais, List<ValePais> listaPaisesVales) {
		String textoCheckout = getTextCheckoutVale(valePais);
		valePais.setTextoCheckout(textoCheckout);
	    listaPaisesVales.add(valePais);
	}
}

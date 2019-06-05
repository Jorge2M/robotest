package com.mng.robotest.test80.mango.test.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.mng.robotest.test80.mango.test.generic.beans.FactoryVale;
import com.mng.robotest.test80.mango.test.generic.beans.ValePais;
import com.mng.robotest.test80.mango.test.data.PaisShop;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;

//TODO refactorizar para que cada campaña sea una clase
public class ValesData {
	
	public enum Campanya {
		MNGVIP("01/06/2019 00:00"),
		IMVIP("01/06/2019 00:00"),
		VIP19("01/06/2019 00:00"),
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
		case MNGVIP:
			return getMNGVIP2019(campanya, filterCal);
		case IMVIP:
			return getIMVIP2019(campanya, filterCal); 
		case VIP19:
			return getVIP19(campanya, filterCal);
//		case VIP18:
//			return getValesVIP18(campanya, filterCal);
//		case VIPMNG:
//			return getValesVIPMNG(campanya, filterCal);
		default:
			return null;
		}
	}
	
	public static List<ValePais> getMNGVIP2019(Campanya campanya, boolean filterCal) throws Exception {
		List<ValePais> listaPaisesVales = new ArrayList<>();
		addValePais(FactoryVale.makeWithArticles(campanya, PaisShop.CoreaDelSur, 30, "04/06/2019 00:00", "01/01/2020 00:00", filterCal, Arrays.asList("43029089"), Arrays.asList("53070658")), listaPaisesVales);
		addValePais(FactoryVale.makeWithArticles(campanya, PaisShop.CoreaDelSur, 40, "04/06/2019 00:00", "01/01/2020 00:00", filterCal, Arrays.asList("41077792"), Arrays.asList("53070658")), listaPaisesVales);
		addValePais(FactoryVale.makeWithArticles(campanya, PaisShop.Philippines, 30, "04/06/2019 00:00", "01/01/2020 00:00", filterCal, Arrays.asList("43029089"), Arrays.asList("53070658")), listaPaisesVales);
		addValePais(FactoryVale.makeWithArticles(campanya, PaisShop.Malaysia, 30, "04/06/2019 00:00", "01/01/2020 00:00", filterCal, Arrays.asList("43029089"), Arrays.asList("53070658")), listaPaisesVales);
		addValePais(FactoryVale.makeWithArticles(campanya, PaisShop.Singapore, 30, "04/06/2019 00:00", "01/01/2020 00:00", filterCal, Arrays.asList("43029089"), Arrays.asList("53070658")), listaPaisesVales);
		addValePais(FactoryVale.makeWithArticles(campanya, PaisShop.Russia, 30, "04/06/2019 00:00", "01/01/2020 00:00", filterCal, Arrays.asList("43029089"), Arrays.asList("53070658")), listaPaisesVales);
		addValePais(FactoryVale.makeWithArticles(campanya, PaisShop.Russia, 40, "04/06/2019 00:00", "01/01/2020 00:00", filterCal, Arrays.asList("41077792"), Arrays.asList("53070658")), listaPaisesVales);
	    return listaPaisesVales;
	}
	
	public static List<ValePais> getIMVIP2019(Campanya campanya, boolean filterCal) throws Exception {
		List<ValePais> listaPaisesVales = new ArrayList<>();
		addValePais(FactoryVale.makeWithArticles(campanya, PaisShop.Russia, 30, "04/06/2019 00:00", "01/01/2020 00:00", filterCal, Arrays.asList("43029089"), Arrays.asList("53070658")), listaPaisesVales);
		addValePais(FactoryVale.makeWithArticles(campanya, PaisShop.Russia, 40, "04/06/2019 00:00", "01/01/2020 00:00", filterCal, Arrays.asList("41077792"), Arrays.asList("53070658")), listaPaisesVales);
		addValePais(FactoryVale.makeWithArticles(campanya, PaisShop.Deutschland, 30, "04/06/2019 00:00", "01/01/2020 00:00", filterCal, Arrays.asList("43029089"), Arrays.asList("53070658")), listaPaisesVales);
		addValePais(FactoryVale.makeWithArticles(campanya, PaisShop.Nederland, 30, "04/06/2019 00:00", "01/01/2020 00:00", filterCal, Arrays.asList("43029089"), Arrays.asList("53070658")), listaPaisesVales);
	    return listaPaisesVales;
	}
	
	public static List<ValePais> getVIP19(Campanya campanya, boolean filterCal) throws Exception {
		List<ValePais> listaPaisesVales = new ArrayList<>();
		addValePais(FactoryVale.makeWithArticles(campanya, PaisShop.Denmark, 30, "04/06/2019 00:00", "01/01/2020 00:00", filterCal, Arrays.asList("43029089"), Arrays.asList("53070658")), listaPaisesVales);
	    return listaPaisesVales;
	}
	
	private static String getTextCheckoutVale(ValePais vale) {
		Campanya campanya = vale.getCampanya();
		Pais pais = vale.getPais();
		switch (campanya) {
//		case MNGVIP2019:
//			return getTextCheckoutVALEEjemplo(pais);
		default:
			return "";
		}
	}
	
	private static String getTextCheckoutVALEEjemplo(Pais pais) {
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

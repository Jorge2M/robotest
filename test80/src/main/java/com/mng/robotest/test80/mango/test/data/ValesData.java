package com.mng.robotest.test80.mango.test.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.mng.robotest.test80.mango.test.generic.beans.ValePais;

public class ValesData {
	
	public enum Campanya {
		VIP18("10/12/2018 00:00"),
		VIPMNG("10/12/2018 00:00"),
		IMPVIP("10/12/2018 00:00"),
		Test("01/01/2000 00:00");
		
		Calendar fechaInit;
		private Campanya(String fechaInitStr) {
			try {
				fechaInit = ValePais.fechaToCalendar(fechaInitStr);
			}
			catch (Exception e) {}
		}
		
		public Calendar getFechaInit() {
			return fechaInit;
		}
	}
	
	public static List<ValePais> getListVales(Campanya campanya, boolean filterCal) throws Exception {
		switch (campanya) {
		case VIP18:
			return getValesVIP18(campanya, filterCal);
		case VIPMNG:
			return getValesVIPMNG(campanya, filterCal);
//		case IMPVIP:
//			return getValesIMPVIP(campanya, filterCal);
		default:
			return null;
		}
	}
	
	public static List<ValePais> getValesVIP18(Campanya campanya, boolean filterCal) throws Exception {
		List<ValePais> listaPaisesVales = new ArrayList<>();
	    addValePais(ValePais.makeWithArticles(campanya, "004"/*ALEMANIA            */, "VIP18", 30, "13/12/2018 20:00", "17/12/2018 02:00", filterCal, Arrays.asList("34070589"), Arrays.asList("33089053")), listaPaisesVales);
	    addValePais(ValePais.makeWithArticles(campanya, "800"/*AUSTRALIA           */, "VIP18", 30, "13/12/2018 12:00", "19/12/2018 16:00", filterCal, Arrays.asList("34070589"), Arrays.asList("33089053")), listaPaisesVales);
	    addValePais(ValePais.makeWithArticles(campanya, "038"/*AUSTRIA             */, "VIP18", 30, "13/12/2018 16:00", "17/12/2018 02:00", filterCal, Arrays.asList("34070589"), Arrays.asList("33089053")), listaPaisesVales);
	    addValePais(ValePais.makeWithArticles(campanya, "017"/*BELGICA             */, "VIP18", 30, "20/12/2018 18:00", "25/12/2018 02:00", filterCal, Arrays.asList("34070589"), Arrays.asList("33089053")), listaPaisesVales);
	    addValePais(ValePais.makeWithArticles(campanya, "068"/*BULGARIA            */, "VIP18", 30, "15/12/2018 21:00", "20/12/2018 01:00", filterCal, Arrays.asList("34070589"), Arrays.asList("33089053")), listaPaisesVales);
	    addValePais(ValePais.makeWithArticles(campanya, "404"/*CANADA              */, "VIP18", 30, "15/12/2018 22:00", "20/12/2018 08:00", filterCal, Arrays.asList("33053821"), Arrays.asList("33089053")), listaPaisesVales);
	    addValePais(ValePais.makeWithArticles(campanya, "022"/*CEUTA               */, "VIP18", 30, "26/12/2018 20:00", "01/01/2019 02:00", filterCal, Arrays.asList("34070589"), Arrays.asList("33089053")), listaPaisesVales);
	    addValePais(ValePais.makeWithArticles(campanya, "720"/*CHINA               */, "VIP18", 30, "03/12/2018 15:00", "07/12/2018 19:00", filterCal, Arrays.asList("33055755"), Arrays.asList("33089053")), listaPaisesVales);
	    addValePais(ValePais.makeWithArticles(campanya, "480"/*COLOMBIA            */, "VIP18", 30, "28/12/2018 04:00", "01/01/2019 08:00", filterCal, Arrays.asList("31035745"), Arrays.asList("31019702")), listaPaisesVales);
	    addValePais(ValePais.makeWithArticles(campanya, "728"/*COREA               */, "VIP18", 30, "08/12/2018 12:00", "12/12/2018 18:00", filterCal, Arrays.asList("33085800"), Arrays.asList("41040718")), listaPaisesVales);
	    addValePais(ValePais.makeWithArticles(campanya, "092"/*CROACIA             */, "VIP18", 30, "13/12/2018 22:00", "20/12/2018 02:00", filterCal, Arrays.asList("34070589"), Arrays.asList("33089053")), listaPaisesVales);
	    addValePais(ValePais.makeWithArticles(campanya, "001"/*ESPAÑA              */, "VIP18", 30, "26/12/2018 20:00", "01/01/2019 02:00", filterCal, Arrays.asList("34070589"), Arrays.asList("33089053")), listaPaisesVales);
	    addValePais(ValePais.makeWithArticles(campanya, "708"/*FILIPINAS           */, "VIP18", 30, "07/12/2018 15:00", "12/12/2018 19:00", filterCal, Arrays.asList("34070589"), Arrays.asList("33089053")), listaPaisesVales);
	    addValePais(ValePais.makeWithArticles(campanya, "032"/*FINLANDIA           */, "VIP18", 30, "13/12/2018 21:00", "20/12/2018 01:00", filterCal, Arrays.asList("34070589"), Arrays.asList("31055727")), listaPaisesVales);
	    addValePais(ValePais.makeWithArticles(campanya, "011"/*FRANCIA             */, "VIP18", 30, "20/12/2018 19:00", "25/12/2018 02:00", filterCal, Arrays.asList("34070589"), Arrays.asList("43050587")), listaPaisesVales); //En Francia no hay artículos excluídos de T3
	    addValePais(ValePais.makeWithArticles(campanya, "064"/*HUNGRIA             */, "VIP18", 30, "15/12/2018 22:00", "20/12/2018 02:00", filterCal, Arrays.asList("34070589"), Arrays.asList("33089053")), listaPaisesVales);
	    addValePais(ValePais.makeWithArticles(campanya, "700"/*INDONESIA           */, "VIP18", 30, "07/12/2018 16:00", "12/12/2018 20:00", filterCal, Arrays.asList("34070589"), Arrays.asList("33089053")), listaPaisesVales);
	    addValePais(ValePais.makeWithArticles(campanya, "007"/*IRLANDA             */, "VIP18", 30, "13/12/2018 23:00", "20/12/2018 03:00", filterCal, Arrays.asList("34070589"), Arrays.asList("33089053")), listaPaisesVales);
	    addValePais(ValePais.makeWithArticles(campanya, "021"/*I.CANARIAS          */, "VIP18", 30, "26/12/2018 20:00", "01/01/2019 03:00", filterCal, Arrays.asList("33053821"), Arrays.asList("33089053")), listaPaisesVales);
	    addValePais(ValePais.makeWithArticles(campanya, "005"/*ITALIA              */, "VIP18", 30, "20/12/2018 20:00", "25/12/2018 02:00", filterCal, Arrays.asList("34070589"), Arrays.asList("33089053")), listaPaisesVales);
	    addValePais(ValePais.makeWithArticles(campanya, "701"/*MALASIA             */, "VIP18", 30, "07/12/2018 15:00", "12/12/2018 19:00", filterCal, Arrays.asList("34070589"), Arrays.asList("33089053")), listaPaisesVales);
	    addValePais(ValePais.makeWithArticles(campanya, "023"/*MELILLA             */, "VIP18", 30, "26/12/2018 20:00", "01/01/2019 02:00", filterCal, Arrays.asList("34070589"), Arrays.asList("33089053")), listaPaisesVales);
	    addValePais(ValePais.makeWithArticles(campanya, "412"/*MEXICO              */, "VIP18", 30, "21/12/2018 05:00", "25/12/2018 09:00", filterCal, Arrays.asList("34070589"), Arrays.asList("33089053")), listaPaisesVales);	    
	    addValePais(ValePais.makeWithArticles(campanya, "028"/*NORUEGA             */, "VIP18", 30, "13/12/2018 22:00", "20/12/2018 02:00", filterCal, Arrays.asList("34070589"), Arrays.asList("33089053")), listaPaisesVales);
	    addValePais(ValePais.makeWithArticles(campanya, "003"/*PAISES BAJOS        */, "VIP18", 30, "13/12/2018 19:00", "17/12/2018 02:00", filterCal, Arrays.asList("34070589"), Arrays.asList("33089053")), listaPaisesVales);
	    addValePais(ValePais.makeWithArticles(campanya, "060"/*POLONIA             */, "VIP18", 30, "14/12/2018 19:00", "20/12/2018 02:00", filterCal, Arrays.asList("34070589"), Arrays.asList("33089053")), listaPaisesVales);
	    addValePais(ValePais.makeWithArticles(campanya, "010"/*PORTUGAL            */, "VIP18", 30, "20/12/2018 23:00", "26/12/2018 03:00", filterCal, Arrays.asList("34070589"), Arrays.asList("33089053")), listaPaisesVales);
	    addValePais(ValePais.makeWithArticles(campanya, "006"/*REINO UNIDO         */, "VIP18", 30, "14/12/2018 18:00", "20/12/2018 03:00", filterCal, Arrays.asList("34070589"), Arrays.asList("33089053")), listaPaisesVales);
	    addValePais(ValePais.makeWithArticles(campanya, "061"/*REPUBLICA CHECA     */, "VIP18", 30, "15/12/2018 22:00", "20/12/2018 02:00", filterCal, Arrays.asList("34070589"), Arrays.asList("33089053")), listaPaisesVales);
	    addValePais(ValePais.makeWithArticles(campanya, "066"/*RUMANIA             */, "VIP18", 30, "13/12/2018 21:00", "20/12/2018 01:00", filterCal, Arrays.asList("34070589"), Arrays.asList("33089053")), listaPaisesVales);
	    addValePais(ValePais.makeWithArticles(campanya, "075"/*RUSIA               */, "VIP18", 30, "06/12/2018 20:00", "13/12/2018 00:00", filterCal, Arrays.asList("34070589"), Arrays.asList("33089053")), listaPaisesVales);
	    addValePais(ValePais.makeWithArticles(campanya, "706"/*SINGAPUR            */, "VIP18", 30, "06/12/2018 13:00", "12/12/2018 19:00", filterCal, Arrays.asList("34070589"), Arrays.asList("33089053")), listaPaisesVales);
	    addValePais(ValePais.makeWithArticles(campanya, "030"/*SUECIA              */, "VIP18", 30, "13/12/2018 18:00", "20/12/2018 02:00", filterCal, Arrays.asList("34070589"), Arrays.asList("33089053")), listaPaisesVales);
	    addValePais(ValePais.makeWithArticles(campanya, "036"/*SUIZA               */, "VIP18", 30, "13/12/2018 19:00", "17/12/2018 02:00", filterCal, Arrays.asList("34070589"), Arrays.asList("33089053")), listaPaisesVales);
	    addValePais(ValePais.makeWithArticles(campanya, "400"/*U.S.A.              */, "VIP18", 30, "15/12/2018 00:00", "20/12/2018 07:00", filterCal, Arrays.asList("33053821"), Arrays.asList("31019702")), listaPaisesVales);
	    addValePais(ValePais.makeWithArticles(campanya, "720"/*CHINA               */, "VIP18", 40, "03/12/2018 15:00", "07/12/2018 19:00", filterCal, Arrays.asList("33055755,31087008"), Arrays.asList("33089053")), listaPaisesVales); //2 ó más artículos 40% descuento
	    addValePais(ValePais.makeWithArticles(campanya, "005"/*ITALIA              */, "VIP18", 40, "20/12/2018 20:00", "25/12/2018 02:00", filterCal, Arrays.asList("33097016"), Arrays.asList("33089053")), listaPaisesVales); //+120 euros 40% descuento
	
	    return listaPaisesVales;
	}
	
	public static List<ValePais> getValesVIPMNG(Campanya campanya, boolean filterCal) throws Exception {
		List<ValePais> listaPaisesVales = new ArrayList<>();
		addValePais(ValePais.makeWithArticles(campanya, "022"/*CEUTA*/	    ,"VIPMNG", 30, "23/12/2018 20:00", "02/01/2019 02:00", filterCal, Arrays.asList("33085800"), Arrays.asList("41040718")), listaPaisesVales);
		addValePais(ValePais.makeWithArticles(campanya, "728"/*COREA*/	    ,"VIPMNG", 30, "08/12/2018 12:00", "12/12/2018 18:00", filterCal, Arrays.asList("33085800"), Arrays.asList("41040718")), listaPaisesVales);
		addValePais(ValePais.makeWithArticles(campanya, "001"/*ESPAÑA*/	    ,"VIPMNG", 30, "23/12/2018 20:00", "02/01/2019 02:00", filterCal, Arrays.asList("33085800"), Arrays.asList("41040718")), listaPaisesVales);
		addValePais(ValePais.makeWithArticles(campanya, "708"/*FILIPINAS*/	,"VIPMNG", 30, "06/12/2018 15:00", "12/12/2018 19:00", filterCal, Arrays.asList("31083742"), Arrays.asList("33099701")), listaPaisesVales);
	    addValePais(ValePais.makeWithArticles(campanya, "021"/*CANARIAS*/   ,"VIPMNG", 30, "23/12/2018 20:00", "02/01/2019 03:00", filterCal, Arrays.asList("33085800"), Arrays.asList("41040718")), listaPaisesVales);
	    addValePais(ValePais.makeWithArticles(campanya, "701"/*MALASIA*/	,"VIPMNG", 30, "06/12/2018 15:00", "12/12/2018 19:00", filterCal, Arrays.asList("31083742"), Arrays.asList("33099701")), listaPaisesVales);
	    addValePais(ValePais.makeWithArticles(campanya, "023"/*MELILLA*/    ,"VIPMNG", 30, "23/12/2018 20:00", "02/01/2019 02:00", filterCal, Arrays.asList("33085800"), Arrays.asList("41040718")), listaPaisesVales);
	    addValePais(ValePais.makeWithArticles(campanya, "075"/*RUSIA*/	    ,"VIPMNG", 30, "04/12/2018 20:00", "13/12/2018 00:00", filterCal, Arrays.asList("31083742"), Arrays.asList("33099701")), listaPaisesVales);
		addValePais(ValePais.makeWithArticles(campanya, "706"/*SINGAPUR*/	,"VIPMNG", 30, "04/12/2018 13:00", "12/12/2018 19:00", filterCal, Arrays.asList("31083742"), Arrays.asList("33099701")), listaPaisesVales);
		
	    return listaPaisesVales;
	}
	
//	public static List<ValePais> getValesIMPVIP(Campanya campanya, boolean filterCal) throws Exception {
//		List<ValePais> listaPaisesVales = new ArrayList<>();
//		addValePais(ValePais.makeWithArticles(campanya, "004"/*ALEMANIA*/		,"VIPMNG", 30, "08/12/2018 12:00", "12/12/2018 18:00", filterCal, Arrays.asList("31083742"), Arrays.asList("33079048")), listaPaisesVales);
//		addValePais(ValePais.makeWithArticles(campanya, "001"/*ESPAÑA*/			,"VIPMNG", 30, "06/12/2018 15:00", "12/12/2018 19:00", filterCal, Arrays.asList("31083742"), Arrays.asList("33079048")), listaPaisesVales);
//	    addValePais(ValePais.makeWithArticles(campanya, "011"/*FRANCIA*/		,"VIPMNG", 30, "06/12/2018 16:00", "12/12/2018 20:00", filterCal, Arrays.asList("31083742"), Arrays.asList("43050587")), listaPaisesVales); //En Francia no hay artículos excluídos de T3
//	    addValePais(ValePais.makeWithArticles(campanya, "021"/*CANARIAS*/		,"VIPMNG", 30, "06/12/2018 15:00", "12/12/2018 19:00", filterCal, Arrays.asList("31083742"), Arrays.asList("33079048")), listaPaisesVales);
//	    addValePais(ValePais.makeWithArticles(campanya, "005"/*ITALIA*/	    	,"VIPMNG", 30, "04/12/2018 20:00", "13/12/2018 00:00", filterCal, Arrays.asList("31083742"), Arrays.asList("33079048")), listaPaisesVales);
//		addValePais(ValePais.makeWithArticles(campanya, "003"/*HOLANDA*/		,"VIPMNG", 30, "04/12/2018 13:00", "12/12/2018 19:00", filterCal, Arrays.asList("31083742"), Arrays.asList("33069053")), listaPaisesVales);
//		addValePais(ValePais.makeWithArticles(campanya, "060"/*POLONIA*/		,"VIPMNG", 30, "04/12/2018 13:00", "12/12/2018 19:00", filterCal, Arrays.asList("31083742"), Arrays.asList("33079048")), listaPaisesVales);
//		addValePais(ValePais.makeWithArticles(campanya, "005"/*PORTUGAL*/		,"VIPMNG", 30, "04/12/2018 13:00", "12/12/2018 19:00", filterCal, Arrays.asList("31083742"), Arrays.asList("33079048")), listaPaisesVales);
//		addValePais(ValePais.makeWithArticles(campanya, "010"/*REINO UNIDO*/	,"VIPMNG", 30, "04/12/2018 13:00", "12/12/2018 19:00", filterCal, Arrays.asList("31083742"), Arrays.asList("33079048")), listaPaisesVales);
//		addValePais(ValePais.makeWithArticles(campanya, "006"/*USA*/			,"VIPMNG", 30, "04/12/2018 13:00", "12/12/2018 19:00", filterCal, Arrays.asList("31083742"), Arrays.asList("31039056")), listaPaisesVales);
//		addValePais(ValePais.makeWithArticles(campanya, "400"/*ITALIA*/			,"VIPMNG", 40, "04/12/2018 13:00", "12/12/2018 19:00", filterCal, Arrays.asList("33097016"), Arrays.asList("33079048")), listaPaisesVales); //+120 euros 40% descuento
//		
//		
//	    return listaPaisesVales;
//	}
	
	/**
	 * Se añade un ValePais a la lista pero manteniendo algunas una condición:
	 *  - Si existen 2 valesPais con la misma clave (código vale/país) mantendremos sólo el activado  
	 */
	private static void addValePais(ValePais valePais, List<ValePais> listaPaisesVales) {
	    listaPaisesVales.add(valePais);
	}

    
}

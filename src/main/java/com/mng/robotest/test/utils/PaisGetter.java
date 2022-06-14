package com.mng.robotest.test.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.test.beans.Continente;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.beans.Response;
import com.mng.robotest.test.data.PaisShop;

public class PaisGetter {
	
	private static final String NameFileCountiesXml = "ListCountries.xml";
	private static final List<Pais> listAllCountries = getListAllCountries();
	private static final List<String> charactersAllCountries = Arrays.asList("*", "X", "");

	public static Pais get(PaisShop paisShop) throws NoSuchElementException {
		return (get(paisShop.getCodigoPais()));
	}
	
	public static Pais get(String codPais) throws NoSuchElementException {
		for (Pais pais : listAllCountries) {
			if (codPais.compareTo(pais.getCodigo_pais())==0) {
				return pais;
			}
		}
		throw new NoSuchElementException(codPais + " is not in the XML of countries");
	}
	
	public static List<Pais> get(List<String> listCodCountries) {
		List<Pais> listToReturn = new ArrayList<>();
		for (Pais pais : listAllCountries) {
			if (listCodCountries.contains(pais.getCodigo_pais())) {
				listToReturn.add(pais);
			}
		}
		return listToReturn;
	}
	
	
	/**
	 * @param countries: countries comma separated. Also accepts "*", "X" and "" for all countries
	 */
	public static List<Pais> getFromCommaSeparatedCountries(String countries) {
		if (charactersAllCountries.contains(countries)) {
			return getAllCountries();
		}
		List<String> listCountries = Arrays.asList(countries.split(","));
		return (get(listCountries));
	}
	
	public static List<Pais> getAllCountries() {
		return listAllCountries;
	}

	private static List<Pais> getListAllCountries() {
		List<Pais> listCountrysToReturn = new ArrayList<>();
		Response xmlFiltered;
		try {
			xmlFiltered = getXmlFilteredByCountries();
		}
		catch (JAXBException e) {
			Log4jTM.getLogger().error(e);
			return null;
		}
		Iterator<Continente> itContinentes = xmlFiltered.getResponse().iterator();
		while (itContinentes.hasNext()) {
			Continente continente = itContinentes.next();
			Iterator<Pais> itPaises = continente.getPaises().iterator();
			while (itPaises.hasNext()) {
				listCountrysToReturn.add(itPaises.next());
			}
		}

		return listCountrysToReturn;
	}

	private static Response getXmlFilteredByCountries() throws JAXBException {
		Response response;
		JAXBContext jaxbContext = JAXBContext.newInstance(Response.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		InputStream countriesXmlStream = PaisGetter.class.getResourceAsStream("/" + NameFileCountiesXml);
		response = (Response)jaxbUnmarshaller.unmarshal(countriesXmlStream);
		return response;
	}

//	public static boolean isTop(String codigoPais, int numTops) {
//		ArrayList<String> listTop10 = new ArrayList<>();
//		listTop10.add("001"); //España (Península y Baleares)
//		listTop10.add("004"); //Deutschland
//		listTop10.add("006"); //United Kingdom
//		listTop10.add("011"); //France métropolitaine
//		listTop10.add("052"); //Türkiye
//		listTop10.add("400"); //USA
//		listTop10.add("003"); //Nederland
//		listTop10.add("060"); //Poland
//		listTop10.add("005"); //Italia
//		listTop10.add("075"); //Россия (Российская Федерация)
//		boolean encontrado = false;
//		for (int i=0; i<numTops && i<10; i++) {
//			if (listTop10.get(i).compareTo(codigoPais)==0) {
//				encontrado = true;
//			}
//		}
//
//		return encontrado;
//	}
//
//	public static boolean isTop10(int codigo_pais) {	
//		ArrayList<Integer> listTop = new ArrayList<>();
//		listTop.add(Integer.valueOf(004)); //Deutschland");
//		listTop.add(Integer.valueOf(001)); //España (Península y Baleares)");
//		listTop.add(Integer.valueOf(011)); //France métropolitaine");
//		listTop.add(Integer.valueOf(003)); //Nederland");
//		listTop.add(Integer.valueOf(060)); //Poland ");
//		listTop.add(Integer.valueOf(052)); //Türkiye");
//		listTop.add(Integer.valueOf(006)); //United Kingdom");
//		listTop.add(Integer.valueOf(400)); //USA ");
//		listTop.add(Integer.valueOf(075)); //Россия (Российская Федерация)");
//		listTop.add(Integer.valueOf(720)); //中国");
//		boolean encontrado = false;
//		if (listTop.contains(Integer.valueOf(codigo_pais))) {
//			encontrado = true;
//		}
//		return encontrado;
//	}
	
}

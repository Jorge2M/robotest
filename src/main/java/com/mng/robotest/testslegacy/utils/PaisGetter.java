package com.mng.robotest.testslegacy.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.beans.Response;
import com.mng.robotest.testslegacy.data.PaisShop;

public class PaisGetter {
	
	private PaisGetter() {}
	
	private static final String NAME_FILE_COUNTRIES_XML = "ListCountries.xml";
	private static final List<Pais> listAllCountries = getListAllCountries();
	private static final List<String> charactersAllCountries = Arrays.asList("*", "X", "");

	public static Pais from(PaisShop paisShop) throws NoSuchElementException {
		return (from(paisShop.getCodigoPais()));
	}
	
	public static Pais from(String codPais) throws NoSuchElementException {
		for (var pais : listAllCountries) {
			if (codPais.compareTo(pais.getCodigoPais())==0) {
				return pais;
			}
		}
		throw new NoSuchElementException(codPais + " is not in the XML of countries");
	}
	
	public static Pais fromCodAlf(String codPaisAlf) throws NoSuchElementException {
		for (var pais : listAllCountries) {
			if (codPaisAlf.compareTo(pais.getCodigoAlf())==0) {
				return pais;
			}
		}
		throw new NoSuchElementException(codPaisAlf + " is not in the XML of countries");
	}	
	
	public static List<Pais> from(List<String> listCodCountries) {
		List<Pais> listToReturn = new ArrayList<>();
		for (var pais : listAllCountries) {
			if (listCodCountries.contains(pais.getCodigoPais())) {
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
		var listCountries = Arrays.asList(countries.split(","));
		return (from(listCountries));
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
			return new ArrayList<>();
		}
		var itContinentes = xmlFiltered.getResponse().iterator();
		while (itContinentes.hasNext()) {
			var continente = itContinentes.next();
			var itPaises = continente.getPaises().iterator();
			while (itPaises.hasNext()) {
				listCountrysToReturn.add(itPaises.next());
			}
		}
		return listCountrysToReturn;
	}

	private static Response getXmlFilteredByCountries() throws JAXBException {
		var jaxbContext = JAXBContext.newInstance(Response.class);
		var jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		var countriesXmlStream = PaisGetter.class.getResourceAsStream("/" + NAME_FILE_COUNTRIES_XML);
		return (Response)jaxbUnmarshaller.unmarshal(countriesXmlStream);
	}

}

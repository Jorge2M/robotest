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
	
	private PaisGetter() {}
	
	private static final String NAME_FILE_COUNTRIES_XML = "ListCountries.xml";
	private static final List<Pais> listAllCountries = getListAllCountries();
	private static final List<String> charactersAllCountries = Arrays.asList("*", "X", "");

	public static Pais from(PaisShop paisShop) throws NoSuchElementException {
		return (from(paisShop.getCodigoPais()));
	}
	
	public static Pais from(String codPais) throws NoSuchElementException {
		for (Pais pais : listAllCountries) {
			if (codPais.compareTo(pais.getCodigo_pais())==0) {
				return pais;
			}
		}
		throw new NoSuchElementException(codPais + " is not in the XML of countries");
	}
	
	public static Pais fromCodAlf(String codPaisAlf) throws NoSuchElementException {
		for (Pais pais : listAllCountries) {
			if (codPaisAlf.compareTo(pais.getCodigo_alf())==0) {
				return pais;
			}
		}
		throw new NoSuchElementException(codPaisAlf + " is not in the XML of countries");
	}	
	
	public static List<Pais> from(List<String> listCodCountries) {
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
		JAXBContext jaxbContext = JAXBContext.newInstance(Response.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		InputStream countriesXmlStream = PaisGetter.class.getResourceAsStream("/" + NAME_FILE_COUNTRIES_XML);
		return (Response)jaxbUnmarshaller.unmarshal(countriesXmlStream);
	}

}

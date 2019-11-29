package com.mng.robotest.test80.mango.test.utils;

import static org.junit.Assert.*;
import static org.testng.Assert.assertThrows;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;

import com.mng.robotest.test80.mango.test.data.PaisShop;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;

public class PaisExtractorTest {

	@Test
	public void testGetPais() throws Exception {
		//When
		Pais pais = PaisGetter.get(PaisShop.España);
		
		//Then
		assertTrue(pais.getCodigo_pais().compareTo(PaisShop.España.getCodigoPais())==0);
	}

	@Test
	public void testException_WhenGetPaisThatNotExists() throws Exception {
		//Then <- When
		assertThrows(NoSuchElementException.class, () -> {
			PaisGetter.get(PaisShop.FakeCountry);
		});
	}
	
	@Test
	public void testGetFromCommaSeparatedCountries() {
		//Given
		String countries = "001,011";
		
		//When
		List<Pais> listCountries = PaisGetter.getFromCommaSeparatedCountries(countries);
		
		//Then
		assertTrue(checkListContainsCodPais(listCountries, "001"));
		assertTrue(checkListContainsCodPais(listCountries, "011"));
		assertTrue(listCountries.size()==2);
	}
	
	@Test
	public void testGetFromCommaSeparatedCountriesWildcard() {
		//Given
		String countries = "X"; //All countries
		
		//When
		List<Pais> listCountries = PaisGetter.getFromCommaSeparatedCountries(countries);
		
		//Then
		assertTrue(checkListContainsCodPais(listCountries, "032"));
		assertTrue(listCountries.size()>30);
	}
	
	private boolean checkListContainsCodPais(List<Pais> listCountries, String codPais) {
		for (Pais pais : listCountries) {
			if (pais.getCodigo_pais().compareTo(codPais)==0) {
				return true;
			}
		}
		return false;
	}
}

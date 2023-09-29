package com.mng.robotest.testslegacy.utils;

import static org.junit.Assert.*;
import static org.testng.Assert.assertThrows;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;

import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.data.PaisShop;

public class PaisGetterTest {

	@Test
	public void testGetPais() throws Exception {
		//When
		Pais pais = PaisGetter.from(PaisShop.ESPANA);
		
		//Then
		assertEquals(PaisShop.ESPANA.getCodigoPais(), pais.getCodigo_pais());
	}

	@Test
	public void testException_WhenGetPaisThatNotExists() throws Exception {
		//Then <- When
		assertThrows(NoSuchElementException.class, () -> {
			PaisGetter.from(PaisShop.FAKE_COUNTRY);
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
		assertEquals(2, listCountries.size());
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

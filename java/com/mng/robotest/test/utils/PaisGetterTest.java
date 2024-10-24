package com.mng.robotest.test.utils;

import static org.junit.Assert.*;
import static org.testng.Assert.assertThrows;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;

import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.PaisShop;


public class PaisGetterTest {

	@Test
	public void testGetPais() throws Exception {
		//When
		Pais pais = PaisGetter.get(PaisShop.ESPANA);
		
		//Then
		assertTrue(pais.getCodigo_pais().compareTo(PaisShop.ESPANA.getCodigoPais())==0);
	}

	@Test
	public void testException_WhenGetPaisThatNotExists() throws Exception {
		//Then <- When
		assertThrows(NoSuchElementException.class, () -> {
			PaisGetter.get(PaisShop.FAKE_COUNTRY);
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

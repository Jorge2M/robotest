package com.mng.robotest.test80.mango.test.utils;

import static com.mng.robotest.test80.mango.test.data.PaisShop.ChannelIslands;
import static com.mng.robotest.test80.mango.test.data.PaisShop.Hungary;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.github.jorge2m.testmaker.conf.Channel;

import static com.mng.robotest.test80.mango.test.data.PaisShop.*; 

public class PagoGetterTest {

	@Test
	public void testGetListLabelsPaymentsEstandar() {
		//Given
		List<String> listCodCountries = Arrays.asList(
				España.getCodigoPais(), 
				Sweden.getCodigoPais());
		
		//When
		List<String> listPaymentsResult = PagoGetter.getLabelsPaymentsAlphabetically(
				listCodCountries, Channel.desktop, AppEcom.shop, false);
		
		//Then
		//TODO creo que el flag de empleado lo estoy gestionando mal
		assertTrue(listPaymentsResult.contains("AMERICAN EXPRESS"));
		assertTrue(listPaymentsResult.contains("KLARNA FAKTURA"));
		assertTrue(listPaymentsResult.contains("Klarna konto"));
		assertTrue(listPaymentsResult.contains("MASTERCARD"));
		assertTrue(listPaymentsResult.contains("PAYPAL"));
		assertTrue(listPaymentsResult.contains("TARJETA MANGO"));
		assertTrue(listPaymentsResult.contains("VISA"));
		assertTrue(listPaymentsResult.contains("VISA ELECTRON"));
		assertTrue(listPaymentsResult.size()==8);
	}
	
	@Test
	public void testGetListLabelsPaymentsSpecialConditions() {
		//Given
		List<String> listCodCountries = Arrays.asList(
			ChannelIslands.getCodigoPais(),
			Hungary.getCodigoPais()); 

		//When
		List<String> listPaymentsResult = PagoGetter.getLabelsPaymentsAlphabetically(
				listCodCountries, Channel.mobile, AppEcom.outlet, false);

		//Then
		assertTrue(listPaymentsResult.contains("VISA"));
		assertTrue(listPaymentsResult.contains("MASTERCARD"));
		assertTrue(listPaymentsResult.contains("VISA ELECTRON"));
		assertTrue(listPaymentsResult.contains("PAYPAL"));
		assertTrue(listPaymentsResult.contains("Utánvételes Fizetés"));
		assertTrue(listPaymentsResult.contains("AMERICAN EXPRESS"));
		assertTrue(listPaymentsResult.size()==6);
	}
}

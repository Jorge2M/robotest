package com.mng.robotest.testslegacy.factoryes.jaxb;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.Test;

import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.testslegacy.beans.Pago;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.data.PaisShop;
import com.mng.robotest.testslegacy.utils.PaisGetter;

public class PaisTest {

	@Test
	public void testGetListPagosForTest() {
		//Given
		Pais espana = PaisGetter.from(PaisShop.ESPANA);
		
		//When
		List<Pago> pagosForTest = espana.getListPagosForTest(AppEcom.shop, false);
		
		//Then
		assertEquals("VISA", pagosForTest.get(0).getNombre());
		assertEquals("VISA ELECTRON", pagosForTest.get(5).getNombre());
		assertEquals(5, pagosForTest.size());
	}

}

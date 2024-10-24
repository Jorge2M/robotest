package com.mng.robotest.test.factoryes.jaxb;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.Test;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.utils.PaisGetter;

public class PaisTest {

	@Test
	public void testGetListPagosForTest() {
		//Given
		Pais espana = PaisGetter.get(PaisShop.ESPANA);
		
		//When
		List<Pago> pagosForTest = espana.getListPagosForTest(AppEcom.shop, false);
		
		//Then
		assertTrue(pagosForTest.get(0).getNombre().compareTo("VISA")==0);
		assertTrue(pagosForTest.get(5).getNombre().compareTo("VISA ELECTRON")==0);
		assertTrue(pagosForTest.size()==6);
	}

}

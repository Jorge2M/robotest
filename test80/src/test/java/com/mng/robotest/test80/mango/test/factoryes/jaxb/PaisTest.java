package com.mng.robotest.test80.mango.test.factoryes.jaxb;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.Test;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.PaisShop;

import com.mng.robotest.test80.mango.test.utils.PaisGetter;

public class PaisTest {

	@Test
	public void testGetListPagosForTest() {
		//Given
		Pais españa = PaisGetter.get(PaisShop.España);
		
		//When
		List<Pago> pagosForTest = españa.getListPagosForTest(AppEcom.shop, false);
		
		//Then
		assertTrue(pagosForTest.get(0).getNombre().compareTo("VISA")==0);
		assertTrue(pagosForTest.get(5).getNombre().compareTo("VISA ELECTRON")==0);
		assertTrue(pagosForTest.size()==6);
	}

}

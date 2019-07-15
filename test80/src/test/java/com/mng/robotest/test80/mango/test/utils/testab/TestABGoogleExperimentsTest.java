package com.mng.robotest.test80.mango.test.utils.testab;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;

public class TestABGoogleExperimentsTest {
	
	@Test
	public void testGetVariantExistent() throws Exception {
		//Mock
		TestABGoogleExperiments testAB = Mockito.spy(new TestABGoogleExperiments(TestABid.GaleriaDesktopReact, Channel.desktop, AppEcom.shop, null));
		int variantExpected = 1;
		String valueInCookie = testAB.getValueExpectedInCookie(TestABid.GaleriaDesktopReact, variantExpected);
		Mockito.doReturn(valueInCookie).when(testAB).getValueCookieGoogleExperiments(Mockito.any());
		
		//Code to Test
		int variantObtained = testAB.getVariantFromCookie(null);
		
		//Validations
		assertEquals(variantObtained, variantExpected);
	}
	
	@Test
	public void testGetVariantNotExistent() {
		//Mock
		TestABGoogleExperiments testAB = Mockito.spy(new TestABGoogleExperiments(TestABid.GaleriaDesktopReact, Channel.desktop, AppEcom.shop, null));
		String valueInCookie = testAB.getValueExpectedInCookie(TestABid.GaleriaDesktopReactPRESemanal, 1);
		Mockito.doReturn(valueInCookie).when(testAB).getValueCookieGoogleExperiments(Mockito.any());
		
		//Code to Test
		int variantObtained = testAB.getVariantFromCookie(null);
		
		//Validations
		assertEquals(variantObtained, -1);
	}
	
	@Test
	public void testGetValueCookieResetingAllVariants() {
		//Mock
		String remainderCookie = "Remainder";
		TestABid testABtoInsert = TestABid.GaleriaDesktopReact;
		TestABGoogleExperiments testAB = Mockito.spy(new TestABGoogleExperiments(testABtoInsert, Channel.desktop, AppEcom.shop, null));
		String valueInCookie = testAB.getValueExpectedInCookie(testABtoInsert, 1) + remainderCookie;
		Mockito.doReturn(valueInCookie).when(testAB).getValueCookieGoogleExperiments(Mockito.any());
		
		//Code to Test
		String valueCookie = testAB.getValueCookieResetingAllTestABvariants(null);
		
		//Validations
		assertEquals(valueCookie, remainderCookie);
	}
}

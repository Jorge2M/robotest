package com.mng.robotest.test80.mango.test.utils.testab;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.utils.testab.manager.TestABGoogleExpManager;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;

public class TestABGoogleExperimentsTest {
	
	@Test
	public void testGetVariantExistent() throws Exception {
		//Mock
		TestABGoogleExpManager testAB = Mockito.spy(new TestABGoogleExpManager(TestABGoogleExpImpl.GaleriaDesktopReact, Channel.desktop, AppEcom.shop, null));
		int variantExpected = 1;
		String valueInCookie = testAB.getValueExpectedInCookie(TestABGoogleExpImpl.GaleriaDesktopReact, variantExpected);
		Mockito.doReturn(valueInCookie).when(testAB).getValueCookieGoogleExperiments(Mockito.any());
		
		//Code to Test
		int variantObtained = testAB.getVariantFromCookie(null);
		
		//Validations
		assertEquals(variantObtained, variantExpected);
	}
	
	@Test
	public void testGetVariantNotExistent() {
		//Mock
		TestABGoogleExpManager testAB = Mockito.spy(new TestABGoogleExpManager(TestABGoogleExpImpl.GaleriaDesktopReact, Channel.desktop, AppEcom.shop, null));
		String valueInCookie = testAB.getValueExpectedInCookie(TestABGoogleExpImpl.GaleriaDesktopReactPRESemanal, 1);
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
		TestABGoogleExpImpl testABtoInsert = TestABGoogleExpImpl.GaleriaDesktopReact;
		TestABGoogleExpManager testAB = Mockito.spy(new TestABGoogleExpManager(testABtoInsert, Channel.desktop, AppEcom.shop, null));
		String valueInCookie = testAB.getValueExpectedInCookie(testABtoInsert, 1) + remainderCookie;
		Mockito.doReturn(valueInCookie).when(testAB).getValueCookieGoogleExperiments(Mockito.any());
		
		//Code to Test
		String valueCookie = testAB.getValueCookieResetingAllTestABvariants(null);
		
		//Validations
		assertEquals(valueCookie, remainderCookie);
	}
}

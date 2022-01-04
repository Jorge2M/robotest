package com.mng.robotest.test80.mango.test.pageobject.shop.ficha.tallas;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.Talla;

class SSecSelTallasFichaOldDeviceTest {

	@Test
	void testGetXPathOptionTalla() {
		SSecSelTallasFichaOldDevice selTallas = new SSecSelTallasFichaOldDevice(Channel.desktop, AppEcom.shop, null);
		String xpath = selTallas.getXPathOptionTalla(Talla.T32);
		
		String xpathExpected = 
				"//div[@id='sizesContainerId']" + 
				"//span[@class='size-text']" +
				"//self::*[" +
				"text()='32' or starts-with(text(),'32 ') or " +
				"text()='1' or starts-with(text(),'1 ')]";
				
		assertTrue(xpath.compareTo(xpathExpected)==0);
	}

}

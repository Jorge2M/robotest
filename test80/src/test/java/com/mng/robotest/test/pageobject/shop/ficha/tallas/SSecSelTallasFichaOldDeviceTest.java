package com.mng.robotest.test.pageobject.shop.ficha.tallas;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.data.Talla;
import com.mng.robotest.test.pageobject.shop.ficha.tallas.SSecSelTallasFichaOldDevice;

public class SSecSelTallasFichaOldDeviceTest {

	@Test
	public void testGetXPathOptionTalla() {
		SSecSelTallasFichaOldDevice selTallas = new SSecSelTallasFichaOldDevice(Channel.desktop, AppEcom.shop, null);
		String xpath = selTallas.getXPathOptionTalla(Talla.T32);
		
		String xpathExpected = 
				"//div[@id='sizesContainerId']" + 
				"//span[@class='size-text']" +
				"//self::*[" +
				"text()='32' or starts-with(text(),'32 ') or " +
				"text()='4' or starts-with(text(),'4 ') or " + 
				"text()='0' or starts-with(text(),'0 ')]";
				
		assertTrue(xpath.compareTo(xpathExpected)==0);
	}

}
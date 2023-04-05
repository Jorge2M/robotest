package com.mng.robotest.domains.ficha.pageobjects.tallas;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.data.Talla;

public class SSecSelTallasFichaOldDeviceTest {

	@Test
	public void testGetXPathOptionTalla() {
		SSecSelTallasFichaDevice selTallas = new SSecSelTallasFichaDevice(Channel.desktop, AppEcom.shop);
		String xpath = selTallas.getXPathOptionTalla(Talla.T32);

		String xpathExpected =
				"//div[@id='sizesContainerId']" +
						"//div[@data-testid[contains(.,'sizeSelector.size')]]" +
						"//self::*[" +
						"text()='32' or starts-with(text(),'32 ') or starts-with(text(),'32cm') or " +
						"text()='4' or starts-with(text(),'4 ') or starts-with(text(),'4cm') or " +
						"text()='0' or starts-with(text(),'0 ') or starts-with(text(),'0cm')]";

		assertEquals(0, xpath.compareTo(xpathExpected));
	}

}

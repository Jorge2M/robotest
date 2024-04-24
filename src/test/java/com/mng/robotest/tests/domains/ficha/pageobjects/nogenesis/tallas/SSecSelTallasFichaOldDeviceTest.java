package com.mng.robotest.tests.domains.ficha.pageobjects.nogenesis.tallas;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.testslegacy.data.PaisShop;
import com.mng.robotest.testslegacy.data.Talla;

public class SSecSelTallasFichaOldDeviceTest {

	@Test
	public void testGetXPathOptionTalla() {
		var selTallas = new SSecSelTallasFichaDevice(Channel.desktop, AppEcom.shop);
		String xpath = selTallas.getXPathOptionTalla(Talla.T32, PaisShop.ESPANA.getCodigoPais());

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

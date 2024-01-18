package com.mng.robotest.testslegacy.data;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

public class TallaTest {

	@Test
	public void testFromLabel() {
		Talla talla = Talla.fromLabel("13", PaisShop.ESPANA);
		assertEquals(Talla.T46, talla);
	}
	
	@Test
	public void testFromLabelVoid() {
		Talla talla = Talla.fromLabel("92", PaisShop.ESPANA);
		assertEquals(Talla.T00, talla);
	}
	
	@Test
	public void testFromValue() {
		Talla talla = Talla.fromValue("10");
		assertEquals(Talla.T10, talla);
	}
}

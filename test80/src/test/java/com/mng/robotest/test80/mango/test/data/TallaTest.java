package com.mng.robotest.test80.mango.test.data;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TallaTest {

	@Test
	void testFromLabel() {
		Talla talla = Talla.fromLabel("14");
		assertTrue(talla==Talla.T46);
	}
	
	@Test
	void testFromLabelVoid() {
		Talla talla = Talla.fromLabel("92");
		assertTrue(talla==Talla.T00);
	}
	
	@Test
	void testFromValue() {
		Talla talla = Talla.fromValue("10");
		assertTrue(talla==Talla.T10);
	}

}

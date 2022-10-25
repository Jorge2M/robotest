package com.mng.robotest.test.data;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;


public class TallaTest {

	@Test
	public void testFromLabel() {
		Talla talla = Talla.fromLabel("13");
		assertEquals(Talla.T46, talla);
	}
	
	@Test
	public void testFromLabelVoid() {
		Talla talla = Talla.fromLabel("92");
		assertEquals(Talla.T00, talla);
	}
	
	@Test
	public void testFromValue() {
		Talla talla = Talla.fromValue("10");
		assertEquals(Talla.T10, talla);
	}
}

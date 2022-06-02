package com.mng.robotest.test.data;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;


public class TallaTest {

	@Test
	public void testFromLabel() {
		Talla talla = Talla.fromLabel("13");
		assertTrue(talla==Talla.T46);
	}
	
	@Test
	public void testFromLabelVoid() {
		Talla talla = Talla.fromLabel("92");
		assertTrue(talla==Talla.T00);
	}
	
	@Test
	public void testFromValue() {
		Talla talla = Talla.fromValue("10");
		assertTrue(talla==Talla.T10);
	}

}

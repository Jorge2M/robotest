package com.mng.robotest.tests.conf;

import com.github.jorge2m.testmaker.conf.SuiteTest;

public enum Suites implements SuiteTest {
	SmokeTest(8),
	SmokeManto(8),
	PagosPaises(10), 
	PaisIdiomaBanner(10), 
	Campanas(10),
	MenusPais(10), 
	ConsolaVotf(10), 
	ListFavoritos(10),
	AvailabilityShop(10),
	CheckoutMultiAddress(10),
	ListMiCuenta(10),
	ModalPortada(10),
	RegistrosPaises(10),
	RegistrosNewPaises(10),
	TextosLegales(8),
	RebajasPaises(10);
	
	private final int maxSecondsToWaitStart;
	
	Suites(int maxSecondsToWaitStart) {
		this.maxSecondsToWaitStart = maxSecondsToWaitStart;
	}
	
	@Override
	public int getMaxSecondsToWaitStart() {
		return this.maxSecondsToWaitStart;
	}
	
	@Override
	public Suites getValueOf(String suiteName) {
		return valueOf(suiteName);
	}
}

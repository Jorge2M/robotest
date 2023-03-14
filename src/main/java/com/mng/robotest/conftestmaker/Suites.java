package com.mng.robotest.conftestmaker;

import com.github.jorge2m.testmaker.conf.SuiteTest;

public enum Suites implements SuiteTest {
	SmokeTest(8),
	SmokeManto(8),
	PagosPaises(10), 
	PaisIdiomaBanner(10), 
	Campanas(10),
	MenusPais(10), 
	MenusManto(50),
	ConsolaVotf(10), 
	ListFavoritos(10),
	CheckoutMultiAddress(10),
	ListMiCuenta(10),
	RegistrosPaises(10),
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

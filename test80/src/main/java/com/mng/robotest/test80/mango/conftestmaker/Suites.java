package com.mng.robotest.test80.mango.conftestmaker;

import com.mng.robotest.test80.arq.utils.conf.SuiteTest;

public enum Suites implements SuiteTest {
    SmokeTest(8),
    SmokeManto(8),
    PagosPaises(10), 
    ValesPaises(10), 
    PaisIdiomaBanner(10), 
    Campanas(10),
    MenusPais(10), 
    MenusManto(50),
    Nodos(80), //In this case the @Factory is very Slow because opens a Browser and iterates until 100 times searching new nodes  
    ConsolaVotf(10), 
    ListFavoritos(10),
    ListMiCuenta(10),
    RegistrosPaises(10),
    LoyaltyMasivos(10),
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
    	return this.valueOf(suiteName);
    }
}

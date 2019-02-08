package com.mng.robotest.test80.mango.test.data;

@SuppressWarnings("javadoc")
public enum Suites {
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
    RebajasPaises(10);
    
    private final int maxSecondsToWaitStart;
    
    Suites(int maxSecondsToWaitStart) {
        this.maxSecondsToWaitStart = maxSecondsToWaitStart;
    }
    
    public int getMaxSecondsToWaitStart() {
        return this.maxSecondsToWaitStart;
    }
}

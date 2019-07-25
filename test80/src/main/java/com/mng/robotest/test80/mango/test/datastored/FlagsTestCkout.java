package com.mng.robotest.test80.mango.test.datastored;

import com.mng.robotest.test80.mango.test.xmlprogram.PagosPaisesSuite.VersionPagosSuite;

public class FlagsTestCkout {
    public boolean validaPasarelas = false;
    public boolean validaPagos = false;
    public boolean validaPedidosEnManto = false;
    public boolean forceTestMisCompras = false;
    public boolean emailExist = false; 
    public boolean trjGuardada = false;
    public boolean isEmpl = false;
    public boolean testCodPromocional = false;
    public boolean isChequeRegalo = false;
    public boolean isStoreCredit = false;
    public boolean loyaltyPoints = false;
    
    public FlagsTestCkout() {}
    
    public static FlagsTestCkout getNew(VersionPagosSuite version) {
    	FlagsTestCkout flags = new FlagsTestCkout();
		flags.validaPasarelas = version.validaPasarelas();
		flags.validaPagos = version.validaPagos();
		flags.validaPedidosEnManto = version.validaPedidosEnManto();
		flags.isEmpl = version.isEmpl();
		flags.forceTestMisCompras = version.forceTestMisCompras();
		return flags;
    }
}

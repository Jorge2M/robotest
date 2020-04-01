package com.mng.robotest.test80.mango.test.datastored;

import java.io.Serializable;

import com.mng.robotest.test80.mango.test.suites.PagosPaisesSuite.VersionPagosSuite;
import com.mng.robotest.test80.mango.test.suites.ValesPaisesSuite.VersionValesSuite;

public class FlagsTestCkout implements Cloneable, Serializable {
	
	private static final long serialVersionUID = 914173092359264587L;
	
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
    
    public static FlagsTestCkout getNew(VersionValesSuite version) {
    	FlagsTestCkout flags = new FlagsTestCkout();
		flags.validaPasarelas = version.validaPasarelas();
		flags.validaPagos = version.validaPagos();
		return flags;
    }
    
    public Object clone() {
        Object obj=null;
        try{
            obj=super.clone();
        } 
        catch(CloneNotSupportedException ex){
            System.out.println(" no se puede duplicar");
        }
        return obj;
    }
}

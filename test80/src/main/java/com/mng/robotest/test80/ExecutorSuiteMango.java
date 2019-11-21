package com.mng.robotest.test80;

import java.util.Arrays;

import com.mng.robotest.test80.mango.conftestmaker.Suites;
import com.mng.robotest.test80.mango.test.suites.ConsolaVotfSuite;
import com.mng.robotest.test80.mango.test.suites.GenericFactorySuite;
import com.mng.robotest.test80.mango.test.suites.MenusMantoSuite;
import com.mng.robotest.test80.mango.test.suites.MenusPaisSuite;
import com.mng.robotest.test80.mango.test.suites.NodosSuite;
import com.mng.robotest.test80.mango.test.suites.PagosPaisesSuite;
import com.mng.robotest.test80.mango.test.suites.PaisIdiomaSuite;
import com.mng.robotest.test80.mango.test.suites.RebajasSuite;
import com.mng.robotest.test80.mango.test.suites.RegistrosSuite;
import com.mng.robotest.test80.mango.test.suites.SmokeMantoSuite;
import com.mng.robotest.test80.mango.test.suites.SmokeTestSuite;
import com.mng.robotest.test80.mango.test.suites.ValesPaisesSuite;
import com.mng.testmaker.domain.ExecutorSuite;
import com.mng.testmaker.domain.SuiteTM;

public class ExecutorSuiteMango extends ExecutorSuite {
	
	private ExecutorSuiteMango(InputParamsMango inputParams) throws Exception {
		super(inputParams);
	}
	public static ExecutorSuiteMango getNew(InputParamsMango inputParams) throws Exception {
		return new ExecutorSuiteMango(inputParams);
	}
	
    @Override
    public SuiteTM makeSuite() throws Exception {
    	InputParamsMango inputParamsMango = (InputParamsMango)inputParams;
        try {
            switch ((Suites)inputParams.getSuite()) {
            case SmokeTest:
                return (new SmokeTestSuite(inputParamsMango)).getSuite();
            case SmokeManto:
                return (new SmokeMantoSuite(inputParamsMango)).getSuite();
            case PagosPaises:
                return (new PagosPaisesSuite(inputParamsMango)).getSuite();
            case ValesPaises:
                return (new ValesPaisesSuite(inputParamsMango)).getSuite();
            case PaisIdiomaBanner:
                return (new PaisIdiomaSuite(inputParamsMango)).getSuite();
            case MenusPais:
                return (new MenusPaisSuite(inputParamsMango)).getSuite();
            case MenusManto:
                return (new MenusMantoSuite(inputParamsMango)).getSuite();
            case Nodos:
                return (new NodosSuite(inputParamsMango)).getSuite();
            case ConsolaVotf:
                return (new ConsolaVotfSuite(inputParamsMango)).getSuite();
            case ListFavoritos:
            case ListMiCuenta:
                return (new GenericFactorySuite(inputParamsMango)).getSuite();
            case RegistrosPaises:
                return (new RegistrosSuite(inputParamsMango)).getSuite();
            case RebajasPaises:
                return (new RebajasSuite(inputParamsMango)).getSuite();
            default:
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println("Suite Name not valid. Posible values: " + Arrays.asList(Suites.values()));
        }
        
        return null;
    }
	
}

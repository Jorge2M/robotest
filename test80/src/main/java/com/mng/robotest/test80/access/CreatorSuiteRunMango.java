package com.mng.robotest.test80.access;

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
import com.github.jorge2m.testmaker.domain.CreatorSuiteRun;
import com.github.jorge2m.testmaker.domain.SuiteMaker;


public class CreatorSuiteRunMango extends CreatorSuiteRun {
	
	private final String ChromeDriverVersionDefault = "80.0.3987.106";
	private final String GeckoDriverVersionDefault = "0.26.0";
	
	private CreatorSuiteRunMango() throws Exception {
		super();
	}
	private CreatorSuiteRunMango(InputParamsMango inputParams) throws Exception {
		super(inputParams);
	}
	public static CreatorSuiteRunMango getNew() throws Exception {
		return new CreatorSuiteRunMango();
	}
	public static CreatorSuiteRunMango getNew(InputParamsMango inputParams) throws Exception {
		return new CreatorSuiteRunMango(inputParams);
	}
	
	@Override
	public SuiteMaker getSuiteMaker() throws Exception {
		setWebDriverVersion();
		InputParamsMango inputParamsMango = (InputParamsMango)inputParams;
		try {
			switch ((Suites)inputParams.getSuite()) {
			case SmokeTest:
				return (new SmokeTestSuite(inputParamsMango));
			case SmokeManto:
				return (new SmokeMantoSuite(inputParamsMango));
			case PagosPaises:
				return (new PagosPaisesSuite(inputParamsMango));
			case ValesPaises:
				return (new ValesPaisesSuite(inputParamsMango));
			case PaisIdiomaBanner:
				return (new PaisIdiomaSuite(inputParamsMango));
			case MenusPais:
				return (new MenusPaisSuite(inputParamsMango));
			case MenusManto:
				return (new MenusMantoSuite(inputParamsMango));
			case Nodos:
				return (new NodosSuite(inputParamsMango));
			case ConsolaVotf:
				return (new ConsolaVotfSuite(inputParamsMango));
			case ListFavoritos:
			case ListMiCuenta:
				return (new GenericFactorySuite(inputParamsMango));
			case RegistrosPaises:
				return (new RegistrosSuite(inputParamsMango));
			case RebajasPaises:
				return (new RebajasSuite(inputParamsMango));
			default:
			}
		}
		catch (IllegalArgumentException e) {
			System.out.println("Suite Name not valid. Posible values: " + Arrays.asList(Suites.values()));
		}

		return null;
	}
	
	private void setWebDriverVersion() {
		if (inputParams.getChromeDriverVersion()==null) {
			inputParams.setChromeDriverVersion(ChromeDriverVersionDefault);
		}
		if (inputParams.getGeckoDriverVersion()==null) {
			inputParams.setGeckoDriverVersion(GeckoDriverVersionDefault);
		}
	}
}

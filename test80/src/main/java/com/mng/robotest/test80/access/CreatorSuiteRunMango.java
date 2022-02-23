package com.mng.robotest.test80.access;

import java.util.Arrays;

import com.mng.robotest.test80.mango.conftestmaker.Suites;
import com.mng.robotest.test80.mango.test.suites.CompraLuqueSuite;
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
//import com.github.jorge2m.testmaker.service.webdriver.maker.FactoryWebdriverMaker.EmbeddedDriver;


public class CreatorSuiteRunMango extends CreatorSuiteRun {
	
//	private final String ChromeDriverVersionDefault = "90.0.4430.24";
//	private final String GeckoDriverVersionDefault = "0.28.0";
	
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
//		//Realmente no es necesario porque robotest carga automáticamente el driver asociado al navegador que arranca
//		//pero puede ser necesario si en algún momento puntual se produce alguna incompatibilidad entre el navegador/driver
		//setWebDriverVersion();
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
			case CompraLuque:
				return (new CompraLuqueSuite(inputParamsMango));
			default:
			}
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
			System.out.println("Suite Name not valid. Posible values: " + Arrays.asList(Suites.values()));
		}

		return null;
	}
	
//	private void setWebDriverVersion() {
//		if (inputParams.getDriverVersion()==null) {
//			if (isEmbeddedDriver(inputParams.getDriver())) {
//				EmbeddedDriver driverType = EmbeddedDriver.valueOf(inputParams.getDriver());
//				switch (driverType) {
//				case firefox:
//				case firefoxhless:
//					inputParams.setDriverVersion(GeckoDriverVersionDefault);
//					break;
//				case chrome:
//				case chromehless:
//					inputParams.setDriverVersion(ChromeDriverVersionDefault);
//					break;
//				default:
//					break;
//				}
//			}
//		}
//	}
//	
//	private boolean isEmbeddedDriver(String driver) {
//		for (EmbeddedDriver embDriver : EmbeddedDriver.values()) {
//			if (driver.compareTo(embDriver.name())==0) {
//				return true;
//			}
//		}
//		return false;
//	}
}

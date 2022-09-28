package com.mng.robotest.access;

import java.util.Arrays;

import com.mng.robotest.conftestmaker.Suites;
import com.mng.robotest.domains.votfconsole.suites.ConsolaVotfSuite;
import com.mng.robotest.test.suites.CompraLuqueSuite;
import com.mng.robotest.test.suites.EgyptOrdersSuite;
import com.mng.robotest.test.suites.GenericFactorySuite;
import com.mng.robotest.test.suites.MenusMantoSuite;
import com.mng.robotest.test.suites.MenusPaisSuite;
import com.mng.robotest.test.suites.NodosSuite;
import com.mng.robotest.test.suites.PagosPaisesSuite;
import com.mng.robotest.test.suites.PaisIdiomaSuite;
import com.mng.robotest.test.suites.RegistrosSuite;
import com.mng.robotest.test.suites.SmokeMantoSuite;
import com.mng.robotest.test.suites.SmokeTestSuite;
import com.github.jorge2m.testmaker.domain.CreatorSuiteRun;
import com.github.jorge2m.testmaker.domain.SuiteMaker;
//import com.github.jorge2m.testmaker.service.webdriver.maker.FactoryWebdriverMaker.EmbeddedDriver;


public class CreatorSuiteRunMango extends CreatorSuiteRun {
	
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
			case ListFavoritos, ListMiCuenta, CheckoutMultiAddress:
				return (new GenericFactorySuite(inputParamsMango));
			case RegistrosPaises:
				return (new RegistrosSuite(inputParamsMango));
			case CompraLuque:
				return (new CompraLuqueSuite(inputParamsMango));
			case EgyptOrders:
				return (new EgyptOrdersSuite(inputParamsMango));				
			default:
			}
		}
		catch (IllegalArgumentException e) {
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

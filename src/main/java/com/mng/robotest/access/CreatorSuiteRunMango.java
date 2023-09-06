package com.mng.robotest.access;

import java.util.Arrays;

import com.mng.robotest.conf.Suites;
import com.mng.robotest.conf.suites.GenericFactorySuite;
import com.mng.robotest.conf.suites.MenusPaisSuite;
import com.mng.robotest.conf.suites.PagosPaisesSuite;
import com.mng.robotest.conf.suites.PaisIdiomaSuite;
import com.mng.robotest.conf.suites.RegistrosNewSuite;
import com.mng.robotest.conf.suites.RegistrosSuite;
import com.mng.robotest.conf.suites.SmokeMantoSuite;
import com.mng.robotest.conf.suites.SmokeTestSuite;
import com.mng.robotest.conf.suites.TextosLegalesSuite;
import com.mng.robotest.domains.votfconsole.suites.ConsolaVotfSuite;
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
				return new SmokeTestSuite(inputParamsMango);
			case SmokeManto:
				return new SmokeMantoSuite(inputParamsMango);
			case PagosPaises:
				return new PagosPaisesSuite(inputParamsMango);
			case PaisIdiomaBanner:
				return new PaisIdiomaSuite(inputParamsMango);
			case MenusPais:
				return new MenusPaisSuite(inputParamsMango);
			case ConsolaVotf:
				return new ConsolaVotfSuite(inputParamsMango);
			case ListFavoritos, ListMiCuenta, CheckoutMultiAddress, ModalPortada:
				return new GenericFactorySuite(inputParamsMango);
			case RegistrosPaises:
				return new RegistrosSuite(inputParamsMango);
			case RegistrosNewPaises:
				return new RegistrosNewSuite(inputParamsMango);				
			case TextosLegales:
				return new TextosLegalesSuite(inputParamsMango);
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

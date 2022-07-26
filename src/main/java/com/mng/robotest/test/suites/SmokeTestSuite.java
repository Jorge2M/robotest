package com.mng.robotest.test.suites;

import static com.mng.robotest.test.suites.SuiteMakerResources.getParametersSuiteShop;
import static com.mng.robotest.test.suites.SuiteMakerResources.isBrowserStack;

import java.util.Arrays;
import java.util.List;

import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.ErrorStorer;
import com.mng.robotest.domains.ayuda.tests.Ayuda;
import com.mng.robotest.domains.ficha.tests.FichaProducto;
import com.mng.robotest.domains.loyalty.tests.Loyalty;
import com.mng.robotest.test.appshop.Bolsa;
import com.mng.robotest.test.appshop.Buscador;
import com.mng.robotest.test.appshop.Compra;
import com.mng.robotest.test.appshop.Favoritos;
import com.mng.robotest.test.appshop.Footer;
import com.mng.robotest.test.appshop.GaleriaProducto;
import com.mng.robotest.test.appshop.IniciarSesion;
import com.mng.robotest.test.appshop.MiCuenta;
import com.mng.robotest.test.appshop.Otras;
import com.mng.robotest.test.appshop.PaisIdioma;
import com.mng.robotest.test.appshop.Personalizacion;
import com.mng.robotest.test.appshop.Reembolsos;
import com.mng.robotest.test.appshop.Registro;
import com.mng.robotest.test.appshop.SEO;
import com.mng.robotest.test.data.Constantes;
import com.mng.robotest.test.factoryes.ListPagosEspana;
import com.github.jorge2m.testmaker.domain.SuiteMaker;
import com.github.jorge2m.testmaker.domain.TestRunMaker;

public class SmokeTestSuite extends SuiteMaker {

	public SmokeTestSuite(InputParamsMango inputParams) {
		super(inputParams);
		setParameters(getParametersSuiteShop(inputParams));
		TestRunMaker testRun = TestRunMaker.from(
				inputParams.getSuiteName(), 
				getClasses());
		testRun.setStorerErrorStep(new ErrorStorer());
		//testRun.setDriverMaker(new MyDriverMaker());
		addTestRun(testRun);
		
		setParallelMode(ParallelMode.METHODS);
		if (!isBrowserStack(inputParams.getDriver())) {
			setThreadCount(3); 
		} else {
			setThreadCount(Constantes.BSTACK_PARALLEL);
		}
	}

	private static List<Class<?>> getClasses() {
		return Arrays.asList(
			Otras.class,
			SEO.class,
			IniciarSesion.class,
			Bolsa.class,
			FichaProducto.class,
			Ayuda.class,
			Buscador.class,
			Footer.class,
			Registro.class,
			PaisIdioma.class,
			GaleriaProducto.class,
			Compra.class,
			ListPagosEspana.class,
			MiCuenta.class,
			Favoritos.class,
			Reembolsos.class,
			Loyalty.class,
			Personalizacion.class);
	}
	
//	public static class MyDriverMaker extends DriverMaker {
//		
//		private String driverVersion = "88.0.4324.96";
//		
//		@Override
//		public String getTypeDriver() {
//			return "mychrome";
//		}
//		
//		@Override
//		public void setupDriverVersion(String driverVersion) {
//			//this.driverVersion = driverVersion;
//		}
//		
//		@Override
//		public WebDriver build() {
//			ChromeDriverManager.chromedriver().version(driverVersion).setup();
//			ChromeOptions options = new ChromeOptions();
//			
//			Map<String, Object> mobileEmulation = new HashMap<>();
//			mobileEmulation.put("deviceName", "Nexus 10");
//			options.setExperimentalOption("mobileEmulation", mobileEmulation);
//			
//			//options.setBinary("C:\\Program Files\\Google\\Chrome Beta\\Application\\chrome.exe");
//			WebDriver driver =  new ChromeDriver(options);
//			return driver;
//		}
//	}
}

package com.mng.robotest.test.suites;

import static com.mng.robotest.test.suites.SuiteMakerResources.getParametersSuiteShop;
import static com.mng.robotest.test.suites.SuiteMakerResources.isBrowserStack;

import java.util.Arrays;
import java.util.List;

import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.ErrorStorer;
import com.mng.robotest.domains.ayuda.tests.Ayuda;
import com.mng.robotest.domains.bolsa.tests.Bolsa;
import com.mng.robotest.domains.buscador.tests.Buscador;
import com.mng.robotest.domains.compra.tests.Compra;
import com.mng.robotest.domains.compra.tests.CompraMultiAddress;
import com.mng.robotest.domains.favoritos.tests.Favoritos;
import com.mng.robotest.domains.ficha.tests.FichaProducto;
import com.mng.robotest.domains.footer.tests.Footer;
import com.mng.robotest.domains.galeria.tests.GaleriaProducto;
import com.mng.robotest.domains.identification.tests.Sesion;
import com.mng.robotest.domains.loyalty.tests.Loyalty;
import com.mng.robotest.domains.micuenta.tests.MiCuenta;
import com.mng.robotest.domains.otros.tests.Otras;
import com.mng.robotest.domains.personalizacion.tests.Personalizacion;
import com.mng.robotest.domains.registro.tests.Registro;
import com.mng.robotest.domains.seo.tests.Seo;
import com.mng.robotest.test.appshop.paisidioma.PaisIdioma;
import com.mng.robotest.test.appshop.reembolsos.Reembolsos;
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
			Seo.class,
			Sesion.class,
			Bolsa.class,
			FichaProducto.class,
			Ayuda.class,
			Buscador.class,
			Footer.class,
			Registro.class,
			PaisIdioma.class,
			GaleriaProducto.class,
			Compra.class,
			CompraMultiAddress.class,
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

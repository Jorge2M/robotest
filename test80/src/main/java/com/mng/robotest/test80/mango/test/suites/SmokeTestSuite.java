package com.mng.robotest.test80.mango.test.suites;

import static com.mng.robotest.test80.mango.test.suites.SuiteMakerResources.getParametersSuiteShop;
import static com.mng.robotest.test80.mango.test.suites.SuiteMakerResources.isBrowserStack;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.robotest.test80.access.InputParamsMango;
import com.mng.robotest.test80.mango.conftestmaker.ErrorStorer;
import com.mng.robotest.test80.mango.test.appshop.Ayuda;
import com.mng.robotest.test80.mango.test.appshop.Bolsa;
import com.mng.robotest.test80.mango.test.appshop.Buscador;
import com.mng.robotest.test80.mango.test.appshop.Compra;
import com.mng.robotest.test80.mango.test.appshop.Favoritos;
import com.mng.robotest.test80.mango.test.appshop.FichaProducto;
import com.mng.robotest.test80.mango.test.appshop.Footer;
import com.mng.robotest.test80.mango.test.appshop.GaleriaProducto;
import com.mng.robotest.test80.mango.test.appshop.IniciarSesion;
import com.mng.robotest.test80.mango.test.appshop.Loyalty;
import com.mng.robotest.test80.mango.test.appshop.MiCuenta;
import com.mng.robotest.test80.mango.test.appshop.Otras;
import com.mng.robotest.test80.mango.test.appshop.PaisIdioma;
import com.mng.robotest.test80.mango.test.appshop.Reembolsos;
import com.mng.robotest.test80.mango.test.appshop.Registro;
import com.mng.robotest.test80.mango.test.appshop.SEO;
import com.mng.robotest.test80.mango.test.data.Constantes;
import com.mng.robotest.test80.mango.test.factoryes.ListPagosEspana;

import io.github.bonigarcia.wdm.ChromeDriverManager;

import com.github.jorge2m.testmaker.domain.SuiteMaker;
import com.github.jorge2m.testmaker.domain.TestRunMaker;
import com.github.jorge2m.testmaker.service.webdriver.maker.DriverMaker;

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
			Loyalty.class);
	}
	
	public static class MyDriverMaker extends DriverMaker {
		
		private String driverVersion = "87.0.4280.88";
		
		@Override
		public String getTypeDriver() {
			return "mychrome";
		}
		
		@Override
		public void setupDriverVersion(String driverVersion) {
			//this.driverVersion = driverVersion;
		}
		
		@Override
		public WebDriver build() {
			ChromeDriverManager.chromedriver().version(driverVersion).setup();
			ChromeOptions options = new ChromeOptions();
			options.setBinary("C:\\Program Files\\Google\\Chrome Beta\\Application\\chrome.exe");
			WebDriver driver =  new ChromeDriver(options);
			return driver;
		}
	}
}

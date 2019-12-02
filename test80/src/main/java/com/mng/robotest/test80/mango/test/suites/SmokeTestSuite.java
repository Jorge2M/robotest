package com.mng.robotest.test80.mango.test.suites;

import static com.mng.robotest.test80.mango.test.suites.SuiteMakerResources.getParametersSuiteShop;
import static com.mng.robotest.test80.mango.test.suites.SuiteMakerResources.getTestRunsForBrowserStack;
import static com.mng.robotest.test80.mango.test.suites.SuiteMakerResources.isBrowserStack;

import java.util.Arrays;
import java.util.List;
import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.robotest.test80.access.InputParamsMango;
import com.mng.robotest.test80.mango.conftestmaker.StorerErrorDataStepValidationMango;
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
import com.mng.testmaker.domain.SuiteMaker;
import com.mng.testmaker.domain.TestRunMaker;

public class SmokeTestSuite extends SuiteMaker {

    public SmokeTestSuite(InputParamsMango inputParams) {
    	super(inputParams);
    	setParameters(getParametersSuiteShop(inputParams));
    	if (!isBrowserStack(inputParams.getWebDriverType())) {
	    	TestRunMaker testRun = TestRunMaker.from(inputParams.getSuiteName(), getClasses());
	    	testRun.setStorerErrorStep(new StorerErrorDataStepValidationMango());
	    	addTestRun(testRun);
	    	setParallelMode(ParallelMode.METHODS);
	    	setThreadCount(3); 
    	} else {
    		addTestRuns(getTestRunsForBrowserStack(inputParams.getSuiteName(), inputParams.getChannel(), getClasses()));
	    	setParallelMode(ParallelMode.TESTS);
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
}

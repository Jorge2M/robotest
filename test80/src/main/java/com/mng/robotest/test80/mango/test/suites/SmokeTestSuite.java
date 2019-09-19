package com.mng.robotest.test80.mango.test.suites;

import static com.mng.robotest.test80.mango.test.suites.SuiteMakerResources.getParametersSuiteShop;
import static com.mng.robotest.test80.mango.test.suites.SuiteMakerResources.getTestRunsForBrowserStack;
import static com.mng.robotest.test80.mango.test.suites.SuiteMakerResources.isBrowserStack;

import java.util.Arrays;
import java.util.List;
import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.robotest.test80.InputParams;
import com.mng.robotest.test80.arq.xmlprogram.SuiteMaker;
import com.mng.robotest.test80.arq.xmlprogram.TestRunMaker;
import com.mng.robotest.test80.mango.test.data.Constantes;

public class SmokeTestSuite extends SuiteMaker {

    public SmokeTestSuite(InputParams inputParams) {
    	super(inputParams);
    	setParameters(getParametersSuiteShop(inputParams));
    	if (!isBrowserStack(inputParams.getTypeWebDriver())) {
	    	TestRunMaker testRun = TestRunMaker.getNew(inputParams.getSuiteName(), getClasses());
	    	addTestRun(testRun);
	    	setParallelMode(ParallelMode.METHODS);
	    	setThreadCount(3);
    	} else {
    		addTestRuns(getTestRunsForBrowserStack(inputParams.getSuiteName(), inputParams.getChannel(), getClasses()));
	    	setParallelMode(ParallelMode.TESTS);
	    	setThreadCount(Constantes.BSTACK_PARALLEL);
    	}
    }

    private static List<String> getClasses() {
    	return Arrays.asList(
	        "com.mng.robotest.test80.mango.test.appshop.Otras",
	        "com.mng.robotest.test80.mango.test.appshop.SEO",
	        "com.mng.robotest.test80.mango.test.appshop.IniciarSesion",
	        "com.mng.robotest.test80.mango.test.appshop.Bolsa",
	        "com.mng.robotest.test80.mango.test.appshop.FichaProducto",
	        "com.mng.robotest.test80.mango.test.appshop.Ayuda",
	        "com.mng.robotest.test80.mango.test.appshop.Buscador",
	        "com.mng.robotest.test80.mango.test.appshop.Footer",
	        "com.mng.robotest.test80.mango.test.appshop.Registro",
	        "com.mng.robotest.test80.mango.test.appshop.PaisIdioma",
	        "com.mng.robotest.test80.mango.test.appshop.GaleriaProducto",
	        "com.mng.robotest.test80.mango.test.appshop.Compra",
	        "com.mng.robotest.test80.mango.test.factoryes.ListPagosEspana",
	        "com.mng.robotest.test80.mango.test.appshop.MiCuenta",
	        "com.mng.robotest.test80.mango.test.appshop.Favoritos",
	        "com.mng.robotest.test80.mango.test.appshop.Reembolsos",
	        "com.mng.robotest.test80.mango.test.appshop.Loyalty");
    }
}

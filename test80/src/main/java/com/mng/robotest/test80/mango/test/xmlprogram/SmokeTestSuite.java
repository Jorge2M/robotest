package com.mng.robotest.test80.mango.test.xmlprogram;

import java.util.Arrays;
import java.util.List;
import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.robotest.test80.arq.xmlprogram.ParamsBean;
import com.mng.robotest.test80.arq.xmlprogram.SuiteMaker;
import com.mng.robotest.test80.arq.xmlprogram.TestRunMaker;
import com.mng.robotest.test80.mango.test.data.Constantes;
import static com.mng.robotest.test80.mango.test.xmlprogram.SuiteMakerResources.getTestRunsForBrowserStack;
import static com.mng.robotest.test80.mango.test.xmlprogram.SuiteMakerResources.getParametersSuiteShop;
import static com.mng.robotest.test80.mango.test.xmlprogram.SuiteMakerResources.isBrowserStack;

public class SmokeTestSuite extends SuiteMaker {

    public SmokeTestSuite(ParamsBean params) {
    	super(params.getInputDataTestMaker());
    	setParameters(getParametersSuiteShop(params));
    	if (!isBrowserStack(params.getBrowser())) {
	    	TestRunMaker testRun = TestRunMaker.getNew(params.getSuiteName(), getClasses());
	    	addTestRun(testRun);
	    	setParallelMode(ParallelMode.METHODS);
	    	setThreadCount(3);
    	} else {
    		addTestRuns(getTestRunsForBrowserStack(params.getSuiteName(), params.getChannel(), getClasses()));
	    	setParallelMode(ParallelMode.TESTS);
	    	setThreadCount(Constantes.BSTACK_PARALLEL);
    	}
    }

    private static List<String> getClasses() {
    	return Arrays.asList(
//	        "com.mng.robotest.test80.mango.test.appshop.Otras",
//	        "com.mng.robotest.test80.mango.test.appshop.SEO",
//	        "com.mng.robotest.test80.mango.test.appshop.IniciarSesion",
//	        "com.mng.robotest.test80.mango.test.appshop.Bolsa",
//	        "com.mng.robotest.test80.mango.test.appshop.FichaProducto",
//	        "com.mng.robotest.test80.mango.test.appshop.Ayuda",
//	        "com.mng.robotest.test80.mango.test.appshop.Buscador",
//	        "com.mng.robotest.test80.mango.test.appshop.Footer",
//	        "com.mng.robotest.test80.mango.test.appshop.Registro",
//	        "com.mng.robotest.test80.mango.test.appshop.PaisIdioma",
//	        "com.mng.robotest.test80.mango.test.appshop.GaleriaProducto",
//	        "com.mng.robotest.test80.mango.test.appshop.Compra",
	        "com.mng.robotest.test80.mango.test.factoryes.ListPagosEspana");
//	        "com.mng.robotest.test80.mango.test.appshop.MiCuenta",
//	        "com.mng.robotest.test80.mango.test.appshop.Favoritos",
//	        "com.mng.robotest.test80.mango.test.appshop.Reembolsos",
//	        "com.mng.robotest.test80.mango.test.appshop.Loyalty");
    }
}

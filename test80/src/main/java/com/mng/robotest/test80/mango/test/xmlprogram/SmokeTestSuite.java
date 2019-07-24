package com.mng.robotest.test80.mango.test.xmlprogram;

import java.util.ArrayList;
import java.util.List;
import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.robotest.test80.arq.utils.webdriver.maker.FactoryWebdriverMaker.TypeWebDriver;
import com.mng.robotest.test80.arq.xmlprogram.ParamsBean;
import com.mng.robotest.test80.arq.xmlprogram.SuiteMaker;
import com.mng.robotest.test80.arq.xmlprogram.TestRunMaker;
import com.mng.robotest.test80.mango.test.data.Constantes;
import static com.mng.robotest.test80.mango.test.xmlprogram.SuiteMakerResources.getTestRunsForBrowserStack;

public class SmokeTestSuite extends SuiteMaker {

    public SmokeTestSuite(ParamsBean params) {
    	super(params.getInputDataTestMaker());
    	setParameters(SuiteMakerResources.getParametersSuiteShop(params));
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
        List<String> listClasses = new ArrayList<>();
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.Otras");
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.SEO");
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.IniciarSesion");
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.Bolsa");
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.FichaProducto");
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.Ayuda");
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.Buscador");
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.Footer");
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.Registro");
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.PaisIdioma");
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.GaleriaProducto");
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.Compra");
        listClasses.add("com.mng.robotest.test80.mango.test.factoryes.ListPagosEspana");
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.MiCuenta");
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.Favoritos");
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.Reembolsos");
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.Loyalty");
        return listClasses;
    }
    
    private boolean isBrowserStack(String browser) {
    	return (TypeWebDriver.valueOf(browser)==TypeWebDriver.browserstack);
    }
}

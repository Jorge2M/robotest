package com.mng.robotest.test80.mango.test.xmlprogram;

import java.util.ArrayList;
import java.util.List;
import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.utils.webdriver.maker.FactoryWebdriverMaker.TypeWebDriver;
import com.mng.robotest.test80.arq.xmlprogram.ParamsBean;
import com.mng.robotest.test80.arq.xmlprogram.SuiteMaker;
import com.mng.robotest.test80.arq.xmlprogram.TestRunMaker;
import com.mng.robotest.test80.mango.test.data.Constantes;
import com.mng.robotest.test80.mango.test.xmlprogram.CommonMangoData;
import static com.mng.robotest.test80.mango.test.data.BrowserStackCtxDesktop.OSXSafari;
import static com.mng.robotest.test80.mango.test.data.BrowserStackCtxDesktop.Win8Firefox;

public class SmokeTestXML extends SuiteMaker {

    public SmokeTestXML(ParamsBean params) {
    	super(params.getInputDataTestMaker());
    	addTestRuns(params.getSuiteName(), params.getChannel(), params.getBrowser());
    	setParameters(CommonMangoData.getParametersSuiteShop(params));
    	setParallelism(params.getBrowser());
    }
    
    private void addTestRuns(String suiteName, Channel channel, String browser) {
    	if (TypeWebDriver.valueOf(browser)!=TypeWebDriver.browserstack) {
    	//if (TypeWebDriver.valueOf(browser)!=TypeWebDriver.chrome) {
	    	TestRunMaker testRun = TestRunMaker.getNew(suiteName, getClasses());
	    	addTestRun(testRun);
    	} else {
    		TestRunMaker testRunOSX = TestRunMaker.getNew(suiteName + OSXSafari, getClasses());
    		testRunOSX.setBrowserStackDesktop(OSXSafari);
	    	addTestRun(testRunOSX);
    		TestRunMaker testRunWin8 = TestRunMaker.getNew(suiteName + Win8Firefox, getClasses());
    		testRunWin8.setBrowserStackDesktop(Win8Firefox);
	    	addTestRun(testRunWin8);
    	}
    }
    
    private void setParallelism(String browser) {
    	if (TypeWebDriver.valueOf(browser)!=TypeWebDriver.browserstack) {
    	//if (TypeWebDriver.valueOf(browser)!=TypeWebDriver.chrome) {
	    	setParallelMode(ParallelMode.METHODS);
	    	setThreadCount(3);
    	} else {
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
}

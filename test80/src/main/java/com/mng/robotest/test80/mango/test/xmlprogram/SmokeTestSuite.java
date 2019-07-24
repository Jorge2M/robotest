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
import static com.mng.robotest.test80.mango.test.data.BrowserStackCtxDesktop.OSX_Safari11;
import static com.mng.robotest.test80.mango.test.data.BrowserStackCtxDesktop.Win8_Firefox62;
import static com.mng.robotest.test80.mango.test.data.BrowserStackCtxMobil.SamsungGalaxyS8_Android7;
import static com.mng.robotest.test80.mango.test.data.BrowserStackCtxMobil.IPhone8_iOS11;

public class SmokeTestSuite extends SuiteMaker {

    public SmokeTestSuite(ParamsBean params) {
    	super(params.getInputDataTestMaker());
    	addTestRuns(params.getSuiteName(), params.getChannel(), params.getBrowser());
    	setParameters(CommonMangoData.getParametersSuiteShop(params));
    	setParallelism(params.getBrowser());
    }
    
    private void addTestRuns(String suiteName, Channel channel, String browser) {
    	//if (TypeWebDriver.valueOf(browser)!=TypeWebDriver.browserstack) {
    	if (TypeWebDriver.valueOf(browser)!=TypeWebDriver.chrome) {
	    	TestRunMaker testRun = TestRunMaker.getNew(suiteName, getClasses());
	    	addTestRun(testRun);
    	} else {
    		if (channel==Channel.desktop) {
	    		TestRunMaker testRunOSX = TestRunMaker.getNew(suiteName + "_" + OSX_Safari11, getClasses());
	    		testRunOSX.setBrowserStackDesktop(OSX_Safari11);
		    	addTestRun(testRunOSX);
	    		TestRunMaker testRunWin8 = TestRunMaker.getNew(suiteName + Win8_Firefox62, getClasses());
	    		testRunWin8.setBrowserStackDesktop(Win8_Firefox62);
		    	addTestRun(testRunWin8);
    		} else {
	    		TestRunMaker testRunAndroid = TestRunMaker.getNew(suiteName + SamsungGalaxyS8_Android7, getClasses());
	    		testRunAndroid.setBrowserStackMobil(SamsungGalaxyS8_Android7);
		    	addTestRun(testRunAndroid);
	    		TestRunMaker testRuniOS = TestRunMaker.getNew(suiteName + IPhone8_iOS11, getClasses());
	    		testRuniOS.setBrowserStackMobil(IPhone8_iOS11);
		    	addTestRun(testRuniOS);
    		}
    	}
    }
    
    private void setParallelism(String browser) {
    	//if (TypeWebDriver.valueOf(browser)!=TypeWebDriver.browserstack) {
    	if (TypeWebDriver.valueOf(browser)!=TypeWebDriver.chrome) {
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

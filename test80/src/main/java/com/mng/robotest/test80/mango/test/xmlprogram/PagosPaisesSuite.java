package com.mng.robotest.test80.mango.test.xmlprogram;

import static com.mng.robotest.test80.mango.test.xmlprogram.SuiteMakerResources.getParametersSuiteShop;
import static com.mng.robotest.test80.mango.test.xmlprogram.SuiteMakerResources.getTestRunsForBrowserStack;
import static com.mng.robotest.test80.mango.test.xmlprogram.SuiteMakerResources.isBrowserStack;

import java.util.Arrays;
import java.util.List;
import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.robotest.test80.arq.xmlprogram.ParamsBean;
import com.mng.robotest.test80.arq.xmlprogram.SuiteMaker;
import com.mng.robotest.test80.arq.xmlprogram.TestRunMaker;
import com.mng.robotest.test80.mango.test.data.Constantes;

public class PagosPaisesSuite extends SuiteMaker {
    
	public enum VersionPagosSuite {
		V1(false, false, false, false, false),
		V2(true, false, false, false, false),
		V3(true, true, false, false, false),
		V4(true, true, true, false, false),
		V5(false, false, false, true, false),
		V6(true, false, false, true, false),
		V7(true, true, false, true, false),
		V8(true, true, true, true, false),
		V9(true, true, false, false, true);
		
		boolean validaPasarelas;
		boolean validaPagos;
		boolean validaPedidosEnManto;
		boolean isEmpl;
		boolean forceTestMisCompras;
		private VersionPagosSuite(
				boolean validaPasarelas, boolean validaPagos, boolean validaPedidosEnManto, 
				boolean isEmpl, boolean forceTestMisCompras) {
			this.validaPasarelas = validaPasarelas;
			this.validaPagos = validaPagos;
			this.validaPedidosEnManto = validaPedidosEnManto;
			this.isEmpl = isEmpl;
			this.forceTestMisCompras = forceTestMisCompras;
		}
		
		public boolean validaPasarelas() {return validaPasarelas;}
		public boolean validaPagos() {return validaPagos;}
		public boolean validaPedidosEnManto() {return validaPedidosEnManto;}
		public boolean isEmpl() {return isEmpl;}
		public boolean forceTestMisCompras() {return forceTestMisCompras;}
    }
	
    public PagosPaisesSuite(ParamsBean params) {
    	super(params.getInputDataTestMaker());
    	setParameters(getParametersSuiteShop(params));
    	if (!isBrowserStack(params.getBrowser())) {
	    	TestRunMaker testRun = TestRunMaker.getNew(params.getSuiteName(), getClasses());
	    	addTestRun(testRun);
	    	setParallelMode(ParallelMode.METHODS);
	    	setThreadCount(4);
    	} else {
    		addTestRuns(getTestRunsForBrowserStack(params.getSuiteName(), params.getChannel(), getClasses()));
	    	setParallelMode(ParallelMode.TESTS);
	    	setThreadCount(Constantes.BSTACK_PARALLEL);
    	}
    }
    
    private static List<String> getClasses() {
    	return Arrays.asList("com.mng.robotest.test80.mango.test.factoryes.ListPrecompraPaises");
    }
}
package com.mng.robotest.test80.mango.test.suites;

import static com.mng.robotest.test80.mango.test.suites.SuiteMakerResources.getParametersSuiteShop;
import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.testmaker.domain.SuiteMaker;
import com.mng.testmaker.domain.TestRunMaker;
import com.mng.robotest.test80.access.InputParamsMango;
import com.mng.robotest.test80.mango.conftestmaker.StorerErrorDataStepValidationMango;
import com.mng.robotest.test80.mango.test.factoryes.ValesPaises;

public class ValesPaisesSuite extends SuiteMaker {         
	
	public enum VersionValesSuite {
		V1(false, false, false, true),
		V2(true, false, false, true),
		V3(true, true, true, true),
		V4(false, false, false, false),
		V5(true, false, false, false),
		V6(true, true, true, false);
		
		boolean validaPasarelas;
		boolean validaPagos;
		boolean validaPedidosManto;
		boolean filtroCalendario;
		private VersionValesSuite(boolean validaPasarelas, boolean validaPagos, boolean validaPedidosManto, boolean filtroCalendario) {
			this.validaPasarelas = validaPasarelas;
			this.validaPagos = validaPagos;
			this.validaPedidosManto = validaPedidosManto;
			this.filtroCalendario = filtroCalendario;
		}
		
		public boolean validaPasarelas() {return validaPasarelas;}
		public boolean validaPagos() {return validaPagos;}
		public boolean filtroCalendario() {return filtroCalendario;}
    }
	
    public ValesPaisesSuite(InputParamsMango inputParams) {
    	super(inputParams);
    	setParameters(getParametersSuiteShop(inputParams));
    	TestRunMaker testRun = TestRunMaker.from(inputParams.getSuiteName(), ValesPaises.class);
    	testRun.setStorerErrorStep(new StorerErrorDataStepValidationMango());
    	addTestRun(testRun);
    	setParallelMode(ParallelMode.METHODS);
    	setThreadCount(4);
    }
}

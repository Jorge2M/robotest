package com.mng.robotest.test80.mango.test.xmlprogram;

import static com.mng.robotest.test80.mango.test.xmlprogram.SuiteMakerResources.getParametersSuiteShop;

import java.util.Arrays;
import java.util.List;
import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.robotest.test80.arq.xmlprogram.ParamsBean;
import com.mng.robotest.test80.arq.xmlprogram.SuiteMaker;
import com.mng.robotest.test80.arq.xmlprogram.TestRunMaker;

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
	
    public ValesPaisesSuite(ParamsBean params) {
    	super(params.getInputDataTestMaker());
    	setParameters(getParametersSuiteShop(params));
    	TestRunMaker testRun = TestRunMaker.getNew(params.getSuiteName(), getClasses());
    	addTestRun(testRun);
    	setParallelMode(ParallelMode.METHODS);
    	setThreadCount(4);
    }
    
    private static List<String> getClasses() {
    	return Arrays.asList("com.mng.robotest.test80.mango.test.factoryes.ValesPaises");
    }
}

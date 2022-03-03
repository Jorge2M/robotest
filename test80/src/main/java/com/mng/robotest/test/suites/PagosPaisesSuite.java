package com.mng.robotest.test.suites;

import static com.mng.robotest.test.suites.SuiteMakerResources.getParametersSuiteShop;
import static com.mng.robotest.test.suites.SuiteMakerResources.isBrowserStack;

import java.util.Arrays;
import java.util.List;
import org.testng.xml.XmlSuite.ParallelMode;

import com.github.jorge2m.testmaker.domain.SuiteMaker;
import com.github.jorge2m.testmaker.domain.TestRunMaker;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.ErrorStorer;
import com.mng.robotest.test.data.Constantes;
import com.mng.robotest.test.factoryes.ListPrecompraPaises;

public class PagosPaisesSuite extends SuiteMaker {
	
	public enum VersionPagosSuite {
		//TODO eliminar las que ya no son necesrias después de la elminia
		V1(false, false, false, false),
		V2(true, false, false, false),
		V3(true, true, false, false),
		V4(true, true, true, false),
		V5(false, false, false, false),
		V6(true, false, false, false),
		V7(true, true, false, false),
		V8(true, true, true, false),
		V9(true, true, false, true);
		
		boolean validaPasarelas;
		boolean validaPagos;
		boolean validaPedidosEnManto;
		boolean forceTestMisCompras;
		private VersionPagosSuite(
				boolean validaPasarelas, boolean validaPagos, boolean validaPedidosEnManto, 
				boolean forceTestMisCompras) {
			this.validaPasarelas = validaPasarelas;
			this.validaPagos = validaPagos;
			this.validaPedidosEnManto = validaPedidosEnManto;
			this.forceTestMisCompras = forceTestMisCompras;
		}
		
		public boolean validaPasarelas() {return validaPasarelas;}
		public boolean validaPagos() {return validaPagos;}
		public boolean validaPedidosEnManto() {return validaPedidosEnManto;}
		public boolean forceTestMisCompras() {return forceTestMisCompras;}
	}
	
	public PagosPaisesSuite(InputParamsMango inputParams) {
		super(inputParams);
		setParameters(getParametersSuiteShop(inputParams));
		List<Class<?>> listTestClasses = Arrays.asList(ListPrecompraPaises.class);
		TestRunMaker testRun = TestRunMaker.from(
				inputParams.getSuiteName(), 
				listTestClasses);
		testRun.setStorerErrorStep(new ErrorStorer());
		addTestRun(testRun);
		setParallelMode(ParallelMode.METHODS);
		if (!isBrowserStack(inputParams.getDriver())) {
			setThreadCount(4);
		} else {
			setThreadCount(Constantes.BSTACK_PARALLEL);
		}
	}
}
package com.mng.robotest.tests.conf.suites;

import static com.mng.robotest.tests.conf.suites.SuiteMakerResources.getParametersSuiteShop;
import static com.mng.robotest.tests.conf.suites.SuiteMakerResources.isBrowserStack;
import static org.testng.xml.XmlSuite.ParallelMode.*;

import java.util.Arrays;
import java.util.List;

import com.github.jorge2m.testmaker.domain.TestRunMaker;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.tests.conf.factories.ListPrecompraPaises;
import com.mng.robotest.testslegacy.data.Constantes;

public class PagosPaisesSuite extends SuiteMakerMango {
	
	public enum VersionPagosSuite {
		//TODO eliminar las que ya no son necesrias después de la elminia
		V1(false, false, false, false),
		V2(true, false, false, false),
		V3(true, true, false, true),
		V4(true, true, true, true),
		V5(false, false, false, false),
		V6(true, false, false, false),
		V7(true, true, false, false),
		V8(true, true, true, true),
		V9(true, true, false, true),
		V10(true, true, false, false);
		
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
		var testRun = TestRunMaker.from(inputParams.getSuiteName(), listTestClasses);
		addTestRunMango(testRun);
		setParallelMode(METHODS);
		if (!isBrowserStack(inputParams.getDriver())) {
			setThreadCount(4);
		} else {
			setThreadCount(Constantes.BSTACK_PARALLEL);
		}
	}
	
}
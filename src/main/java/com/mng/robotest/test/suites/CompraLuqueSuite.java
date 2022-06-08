package com.mng.robotest.test.suites;

import static com.mng.robotest.test.suites.SuiteMakerResources.getParametersSuiteShop;

import java.util.Arrays;
import java.util.List;
import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.ErrorStorer;
import com.mng.robotest.test.appshop.CompraEgoitz;
import com.mng.robotest.test.appshop.CompraLuque;
import com.github.jorge2m.testmaker.domain.SuiteMaker;
import com.github.jorge2m.testmaker.domain.TestRunMaker;

public class CompraLuqueSuite extends SuiteMaker {

	public CompraLuqueSuite(InputParamsMango inputParams) {
		super(inputParams);
		setParameters(getParametersSuiteShop(inputParams));
		TestRunMaker testRun = TestRunMaker.from(
				inputParams.getSuiteName(), 
				getClasses());
		testRun.setStorerErrorStep(new ErrorStorer());
		addTestRun(testRun);
		setParallelMode(ParallelMode.METHODS);
		setThreadCount(3); 
	}

	private static List<Class<?>> getClasses() {
		return Arrays.asList(
			CompraLuque.class,
			CompraEgoitz.class);
	}
}

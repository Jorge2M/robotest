package com.mng.robotest.test80.mango.test.suites;

import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.robotest.test80.access.InputParamsMango;
import com.mng.robotest.test80.mango.conftestmaker.ErrorStorer;
import com.mng.robotest.test80.mango.test.appmanto.Manto;
import com.github.jorge2m.testmaker.domain.SuiteMaker;
import com.github.jorge2m.testmaker.domain.TestRunMaker;

public class SmokeMantoSuite extends SuiteMaker {
	
	public SmokeMantoSuite(InputParamsMango inputParams) {
		super(inputParams);
		setParameters(SuiteMakerResources.getParametersSuiteShop(inputParams));
		TestRunMaker testRun = TestRunMaker.from(inputParams.getSuiteName(), Manto.class);
		testRun.setStorerErrorStep(new ErrorStorer());
		addTestRun(testRun);
		setParallelMode(ParallelMode.METHODS);
		setThreadCount(3);
	}
}

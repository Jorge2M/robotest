package com.mng.robotest.test.suites;

import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.ErrorStorer;
import com.mng.robotest.test.appmanto.Manto;
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
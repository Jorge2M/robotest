package com.mng.robotest.test80.mango.test.suites;

import static com.mng.robotest.test80.mango.test.suites.SuiteMakerResources.getParametersSuiteShop;

import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.testmaker.domain.SuiteMaker;
import com.mng.testmaker.domain.TestRunMaker;
import com.mng.robotest.test80.access.InputParamsMango;
import com.mng.robotest.test80.mango.test.appshop.ConsolaVotf;

public class ConsolaVotfSuite extends SuiteMaker {

	public ConsolaVotfSuite(InputParamsMango inputParams) {
		super(inputParams);
		setParameters(getParametersSuiteShop(inputParams));
		TestRunMaker testRun = TestRunMaker.from(inputParams.getSuiteName(), ConsolaVotf.class);
		addTestRun(testRun);
		setParallelMode(ParallelMode.METHODS);
		setThreadCount(1);
	}
}

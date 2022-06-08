package com.mng.robotest.test.suites;

import static com.mng.robotest.test.suites.SuiteMakerResources.getParametersSuiteShop;

import org.testng.xml.XmlSuite.ParallelMode;

import com.github.jorge2m.testmaker.domain.SuiteMaker;
import com.github.jorge2m.testmaker.domain.TestRunMaker;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.test.appshop.ConsolaVotf;

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
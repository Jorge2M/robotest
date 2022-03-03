package com.mng.robotest.domains.apiproduct.suite;

import static com.mng.robotest.test.suites.SuiteMakerResources.getParametersSuiteShop;

import org.testng.xml.XmlSuite.ParallelMode;

import com.github.jorge2m.testmaker.domain.SuiteMaker;
import com.github.jorge2m.testmaker.domain.TestRunMaker;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.domains.apiproduct.factory.ListApiCatalogs;

public class ApiProductSuite extends SuiteMaker {

	public ApiProductSuite(InputParamsMango inputParams) {
		super(inputParams);
		setParameters(getParametersSuiteShop(inputParams));
		TestRunMaker testRun = TestRunMaker.from(inputParams.getSuiteName(), ListApiCatalogs.class);
		addTestRun(testRun);
		setParallelMode(ParallelMode.METHODS);
		setThreadCount(3);
	}
}

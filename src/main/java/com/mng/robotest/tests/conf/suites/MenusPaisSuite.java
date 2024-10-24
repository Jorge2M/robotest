package com.mng.robotest.tests.conf.suites;

import static com.mng.robotest.tests.conf.suites.SuiteMakerResources.getParametersSuiteShop;
import static org.testng.xml.XmlSuite.ParallelMode.*;

import com.github.jorge2m.testmaker.domain.TestRunMaker;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.tests.conf.factories.MenusFactory;

public class MenusPaisSuite extends SuiteMakerMango {

	public MenusPaisSuite(InputParamsMango inputParams) {
		super(inputParams);
		setParameters(getParametersSuiteShop(inputParams));
		var testRun = TestRunMaker.from(inputParams.getSuiteName(), MenusFactory.class);
		addTestRunMango(testRun);
		setParallelMode(METHODS);
		setThreadCount(4);
	}
}

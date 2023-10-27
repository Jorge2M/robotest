package com.mng.robotest.tests.conf.suites;

import static org.testng.xml.XmlSuite.ParallelMode.METHODS;

import java.util.Arrays;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.tests.domains.manto.factories.MenusMantoFact;
import com.mng.robotest.tests.domains.manto.tests.Manto;
import com.github.jorge2m.testmaker.domain.TestRunMaker;

public class SmokeMantoSuite extends SuiteMakerMango {
	
	public SmokeMantoSuite(InputParamsMango inputParams) {
		super(inputParams);
		setParameters(SuiteMakerResources.getParametersSuiteShop(inputParams));
		var testRun = TestRunMaker.from(inputParams.getSuiteName(), 
				Arrays.asList(Manto.class, MenusMantoFact.class));
		
		addTestRunMango(testRun);
		setParallelMode(METHODS);
		setThreadCount(3);
	}
}

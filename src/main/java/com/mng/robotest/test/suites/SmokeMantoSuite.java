package com.mng.robotest.test.suites;

import java.util.Arrays;

import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.ErrorStorer;
import com.mng.robotest.domains.manto.factories.MenusMantoFact;
import com.mng.robotest.domains.manto.tests.Manto;
import com.github.jorge2m.testmaker.domain.SuiteMaker;
import com.github.jorge2m.testmaker.domain.TestRunMaker;

public class SmokeMantoSuite extends SuiteMaker {
	
	public SmokeMantoSuite(InputParamsMango inputParams) {
		super(inputParams);
		setParameters(SuiteMakerResources.getParametersSuiteShop(inputParams));
		TestRunMaker testRun = TestRunMaker.from(inputParams.getSuiteName(), 
				Arrays.asList(Manto.class, MenusMantoFact.class));
		
		testRun.setStorerErrorStep(new ErrorStorer());
		addTestRun(testRun);
		setParallelMode(ParallelMode.METHODS);
		setThreadCount(3);
	}
}

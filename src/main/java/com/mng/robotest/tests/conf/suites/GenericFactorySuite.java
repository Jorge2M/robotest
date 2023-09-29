package com.mng.robotest.tests.conf.suites;

import static com.mng.robotest.tests.conf.suites.SuiteMakerResources.getParametersSuiteShop;

import org.testng.xml.XmlSuite.ParallelMode;

import com.github.jorge2m.testmaker.domain.SuiteMaker;
import com.github.jorge2m.testmaker.domain.TestRunMaker;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.tests.conf.ErrorStorer;
import com.mng.robotest.tests.conf.Suites;
import com.mng.robotest.tests.conf.factories.GenericFactory;

public class GenericFactorySuite extends SuiteMaker {

	public GenericFactorySuite(InputParamsMango inputParams) {
		super(inputParams);
		setParameters(getParametersSuiteShop(inputParams));
		var testRun = TestRunMaker.from(inputParams.getSuiteName(), GenericFactory.class);
		testRun.setStorerErrorStep(new ErrorStorer());
		addTestRun(testRun);
		setParallelMode(ParallelMode.METHODS);
		if ((Suites)inputParams.getSuite()==Suites.ListFavoritos) { 
			setThreadCount(1);
		}
		else {
			setThreadCount(5);
		}
	}
}

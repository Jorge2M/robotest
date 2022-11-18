package com.mng.robotest.test.suites;

import static com.mng.robotest.test.suites.SuiteMakerResources.getParametersSuiteShop;

import org.testng.xml.XmlSuite.ParallelMode;

import com.github.jorge2m.testmaker.domain.SuiteMaker;
import com.github.jorge2m.testmaker.domain.TestRunMaker;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.ErrorStorer;
import com.mng.robotest.conftestmaker.Suites;
import com.mng.robotest.test.factoryes.GenericFactory;

public class GenericFactorySuite extends SuiteMaker {

	public GenericFactorySuite(InputParamsMango inputParams) {
		super(inputParams);
		setParameters(getParametersSuiteShop(inputParams));
		TestRunMaker testRun = TestRunMaker.from(inputParams.getSuiteName(), GenericFactory.class);
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

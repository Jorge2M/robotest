package com.mng.robotest.tests.conf.suites;

import com.github.jorge2m.testmaker.domain.SuiteMaker;
import com.github.jorge2m.testmaker.domain.TestRunMaker;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.tests.conf.ErrorStorer;

public class SuiteMakerMango extends SuiteMaker {

	public SuiteMakerMango(InputParamsMango inputParams) {
		super(inputParams);
	}
	protected void addTestRunMango(TestRunMaker testRun)  {
		testRun.setStorerErrorStep(new ErrorStorer());
		addTestRun(testRun);
	}

}

package com.mng.robotest.test.suites;

import static com.mng.robotest.test.suites.SuiteMakerResources.getParametersSuiteShop;

import java.util.ArrayList;
import java.util.List;

import org.testng.xml.XmlSuite.ParallelMode;

import com.github.jorge2m.testmaker.domain.SuiteMaker;
import com.github.jorge2m.testmaker.domain.TestRunMaker;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.ErrorStorer;
import com.mng.robotest.test.factoryes.ListRebajasXPais;

public class RebajasSuite extends SuiteMaker {

	public RebajasSuite(InputParamsMango inputParams) {
		super(inputParams);
		setParameters(getParametersSuiteShop(inputParams));
		TestRunMaker testRun = TestRunMaker.from(inputParams.getSuiteName(), ListRebajasXPais.class);
		testRun.setStorerErrorStep(new ErrorStorer());
		testRun.addGroups(getSpecificGroups());
		addTestRun(testRun);
		setParallelMode(ParallelMode.METHODS);
		setThreadCount(3);
	}
	
	private List<String> getSpecificGroups() {
		List<String> listReturn = new ArrayList<>();
		listReturn.add("SupportsFactoryCountrys");
		return listReturn;
	}
}

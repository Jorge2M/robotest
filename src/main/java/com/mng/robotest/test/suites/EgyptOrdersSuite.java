package com.mng.robotest.test.suites;

import static com.mng.robotest.test.suites.SuiteMakerResources.getParametersSuiteShop;

import java.util.Arrays;
import java.util.List;
import org.testng.xml.XmlSuite.ParallelMode;

import com.github.jorge2m.testmaker.domain.SuiteMaker;
import com.github.jorge2m.testmaker.domain.TestRunMaker;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.ErrorStorer;
import com.mng.robotest.test.factoryes.EgyptOrdersFactory;


public class EgyptOrdersSuite extends SuiteMaker {
	
	public EgyptOrdersSuite(InputParamsMango inputParams) {
		super(inputParams);
		setParameters(getParametersSuiteShop(inputParams));
		
		List<Class<?>> listTestClasses = Arrays.asList(EgyptOrdersFactory.class);
		TestRunMaker testRun = TestRunMaker.from(
				inputParams.getSuiteName(), 
				listTestClasses);
		
		testRun.setStorerErrorStep(new ErrorStorer());
		addTestRun(testRun);
		setParallelMode(ParallelMode.METHODS);
		setThreadCount(4);
	}
}
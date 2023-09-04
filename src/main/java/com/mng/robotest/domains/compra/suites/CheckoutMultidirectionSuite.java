package com.mng.robotest.domains.compra.suites;

import static com.mng.robotest.conf.suites.SuiteMakerResources.getParametersSuiteShop;

import java.util.Arrays;
import org.testng.xml.XmlSuite.ParallelMode;

import com.github.jorge2m.testmaker.domain.SuiteMaker;
import com.github.jorge2m.testmaker.domain.TestRunMaker;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conf.ErrorStorer;
import com.mng.robotest.conf.factories.GenericFactory;

public class CheckoutMultidirectionSuite extends SuiteMaker {
	
	public CheckoutMultidirectionSuite(InputParamsMango inputParams) {
		super(inputParams);
		setParameters(getParametersSuiteShop(inputParams));
		TestRunMaker testRun = TestRunMaker.from(inputParams.getSuiteName(), GenericFactory.class);
		testRun.setStorerErrorStep(new ErrorStorer());
		testRun.addGroups(Arrays.asList("SupportsFactoryCountrys"));
		addTestRun(testRun);
		setParallelMode(ParallelMode.METHODS);
		setThreadCount(5);
	}
}

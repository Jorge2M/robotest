package com.mng.robotest.test80.mango.test.suites;

import static com.mng.robotest.test80.mango.test.suites.SuiteMakerResources.getParametersSuiteShop;
import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.testmaker.domain.SuiteMaker;
import com.mng.testmaker.domain.TestRunMaker;
import com.mng.robotest.test80.access.InputParamsMango;
import com.mng.robotest.test80.mango.test.factoryes.ListMenusManto;

public class MenusMantoSuite extends SuiteMaker {

    public MenusMantoSuite(InputParamsMango inputParams) {
    	super(inputParams);
    	setParameters(getParametersSuiteShop(inputParams));
    	TestRunMaker testRun = TestRunMaker.from(inputParams.getSuiteName(), ListMenusManto.class);
    	addTestRun(testRun);
    	setParallelMode(ParallelMode.METHODS);
    	setThreadCount(4);
    }
}

package com.mng.robotest.test80.mango.test.suites;

import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.robotest.test80.InputParamsMango;
import com.mng.robotest.test80.mango.conftestmaker.StorerErrorDataStepValidationMango;
import com.mng.robotest.test80.mango.test.appmanto.Manto;
import com.mng.testmaker.domain.SuiteMaker;
import com.mng.testmaker.domain.TestRunMaker;

public class SmokeMantoSuite extends SuiteMaker {
    
    public SmokeMantoSuite(InputParamsMango inputParams) {
    	super(inputParams);
    	setParameters(SuiteMakerResources.getParametersSuiteShop(inputParams));
    	TestRunMaker testRun = TestRunMaker.from(inputParams.getSuiteName(), Manto.class);
    	testRun.setStorerErrorStep(new StorerErrorDataStepValidationMango());
    	addTestRun(testRun);
    	setParallelMode(ParallelMode.METHODS);
    	setThreadCount(3);
    }
}

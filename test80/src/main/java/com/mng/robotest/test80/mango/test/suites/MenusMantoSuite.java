package com.mng.robotest.test80.mango.test.suites;

import static com.mng.robotest.test80.mango.test.suites.SuiteMakerResources.getParametersSuiteShop;
import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.robotest.test80.InputParams;
import com.mng.robotest.test80.arq.xmlprogram.SuiteMaker;
import com.mng.robotest.test80.arq.xmlprogram.TestRunMaker;
import com.mng.robotest.test80.mango.test.factoryes.ListMenusManto;

public class MenusMantoSuite extends SuiteMaker {

    public MenusMantoSuite(InputParams inputParams) {
    	super(inputParams);
    	setParameters(getParametersSuiteShop(inputParams));
    	TestRunMaker testRun = TestRunMaker.ofClass(inputParams.getSuiteName(), ListMenusManto.class);
    	addTestRun(testRun);
    	setParallelMode(ParallelMode.METHODS);
    	setThreadCount(4);
    }
}

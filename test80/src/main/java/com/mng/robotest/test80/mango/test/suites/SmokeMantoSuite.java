package com.mng.robotest.test80.mango.test.suites;

import java.util.Arrays;
import java.util.List;
import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.robotest.test80.InputParams;
import com.mng.robotest.test80.arq.xmlprogram.SuiteMaker;
import com.mng.robotest.test80.arq.xmlprogram.TestRunMaker;

public class SmokeMantoSuite extends SuiteMaker {
    
    public SmokeMantoSuite(InputParams inputParams) {
    	super(inputParams);
    	setParameters(SuiteMakerResources.getParametersSuiteShop(inputParams));
    	TestRunMaker testRun = TestRunMaker.getNew(inputParams.getSuiteName(), getClasses());
    	addTestRun(testRun);
    	setParallelMode(ParallelMode.METHODS);
    	setThreadCount(3);
    }

    private static List<String> getClasses() {
    	return (Arrays.asList("com.mng.robotest.test80.mango.test.appmanto.Manto"));
    }
}

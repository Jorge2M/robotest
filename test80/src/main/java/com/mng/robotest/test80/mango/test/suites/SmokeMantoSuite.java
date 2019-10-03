package com.mng.robotest.test80.mango.test.suites;

import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.robotest.test80.InputParams;
import com.mng.robotest.test80.arq.xmlprogram.SuiteMaker;
import com.mng.robotest.test80.arq.xmlprogram.TestRunMaker;
import com.mng.robotest.test80.mango.test.appmanto.Manto;

public class SmokeMantoSuite extends SuiteMaker {
    
    public SmokeMantoSuite(InputParams inputParams) {
    	super(inputParams);
    	setParameters(SuiteMakerResources.getParametersSuiteShop(inputParams));
    	TestRunMaker testRun = TestRunMaker.ofClass(inputParams.getSuiteName(), Manto.class);
    	addTestRun(testRun);
    	setParallelMode(ParallelMode.METHODS);
    	setThreadCount(3);
    }
}

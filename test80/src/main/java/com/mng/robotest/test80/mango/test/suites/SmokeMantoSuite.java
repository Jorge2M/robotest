package com.mng.robotest.test80.mango.test.suites;

import java.util.Arrays;
import java.util.List;
import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.robotest.test80.arq.xmlprogram.ParamsBean;
import com.mng.robotest.test80.arq.xmlprogram.SuiteMaker;
import com.mng.robotest.test80.arq.xmlprogram.TestRunMaker;

public class SmokeMantoSuite extends SuiteMaker {
    
    public SmokeMantoSuite(ParamsBean params) {
    	super(params.getInputDataTestMaker());
    	setParameters(SuiteMakerResources.getParametersSuiteShop(params));
    	TestRunMaker testRun = TestRunMaker.getNew(params.getSuiteName(), getClasses());
    	addTestRun(testRun);
    	setParallelMode(ParallelMode.METHODS);
    	setThreadCount(3);
    }

    private static List<String> getClasses() {
    	return (Arrays.asList("com.mng.robotest.test80.mango.test.appmanto.Manto"));
    }
}

package com.mng.sapfiori.test.suite;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.robotest.test80.arq.access.InputParamsTestMaker;
import com.mng.robotest.test80.arq.xmlprogram.SuiteMaker;
import com.mng.robotest.test80.arq.xmlprogram.TestRunMaker;

public class SmokeTestSuite extends SuiteMaker {

    public SmokeTestSuite(InputParamsTestMaker inputParams) {
    	super(inputParams);
    	setParameters(new HashMap<>());
    	TestRunMaker testRun = TestRunMaker.getNew(inputParams.getSuiteName(), getClasses());
    	addTestRun(testRun);
    	setParallelMode(ParallelMode.METHODS);
    	setThreadCount(3);
    }

    private static List<String> getClasses() {
    	return Arrays.asList(
	        "com.mng.sapfiori.test.testcase.script.Piloto");
    }
}

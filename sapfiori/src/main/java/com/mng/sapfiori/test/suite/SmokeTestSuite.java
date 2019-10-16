package com.mng.sapfiori.test.suite;

import java.util.HashMap;
import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.sapfiori.test.testcase.script.Piloto;
import com.mng.testmaker.domain.InputParamsTestMaker;
import com.mng.testmaker.domain.SuiteMaker;
import com.mng.testmaker.domain.TestRunMaker;

public class SmokeTestSuite extends SuiteMaker {

    public SmokeTestSuite(InputParamsTestMaker iParams) {
    	super(iParams);
    	setParameters(new HashMap<>());
    	TestRunMaker testRun = TestRunMaker.ofClass(iParams.getSuiteName(), Piloto.class);
    	addTestRun(testRun);
    	setParallelMode(ParallelMode.METHODS);
    	setThreadCount(3);
    }
}

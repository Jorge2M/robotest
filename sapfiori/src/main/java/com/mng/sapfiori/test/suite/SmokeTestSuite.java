package com.mng.sapfiori.test.suite;

import java.util.HashMap;
import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.testmaker.access.InputParamsTestMaker;
import com.mng.testmaker.xmlprogram.SuiteMaker;
import com.mng.testmaker.xmlprogram.TestRunMaker;
import com.mng.sapfiori.test.testcase.script.Piloto;

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
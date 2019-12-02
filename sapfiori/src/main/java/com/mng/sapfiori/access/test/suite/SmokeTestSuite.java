package com.mng.sapfiori.access.test.suite;

import java.util.Arrays;
import java.util.HashMap;
import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.sapfiori.access.test.testcase.script.Piloto;
import com.mng.sapfiori.access.test.testcase.script.SolicitudPedido;
import com.mng.testmaker.domain.InputParamsTM;
import com.mng.testmaker.domain.SuiteMaker;
import com.mng.testmaker.domain.TestRunMaker;

public class SmokeTestSuite extends SuiteMaker {

    public SmokeTestSuite(InputParamsTM iParams) {
    	super(iParams);
    	setParameters(new HashMap<>());
    	TestRunMaker testRun = TestRunMaker.from(iParams.getSuiteName(), Arrays.asList(SolicitudPedido.class, Piloto.class));
    	addTestRun(testRun);
    	setParallelMode(ParallelMode.METHODS);
    	setThreadCount(3);
    }
}

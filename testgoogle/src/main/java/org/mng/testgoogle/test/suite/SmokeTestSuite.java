package org.mng.testgoogle.test.suite;

import java.util.Arrays;
import java.util.HashMap;

import org.mng.testgoogle.test.testcase.script.Buscar;
import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.testmaker.domain.InputParamsTM;
import com.mng.testmaker.domain.SuiteMaker;
import com.mng.testmaker.domain.TestRunMaker;

public class SmokeTestSuite extends SuiteMaker {

	public SmokeTestSuite(InputParamsTM iParams) {
		super(iParams);
		setParameters(new HashMap<>());
		TestRunMaker testRun = TestRunMaker.from(iParams.getSuiteName(), Arrays.asList(Buscar.class));
		addTestRun(testRun);
		setParallelMode(ParallelMode.METHODS);
		setThreadCount(3);
	}
}

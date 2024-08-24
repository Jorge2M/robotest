package com.mng.robotest.access;

import com.github.jorge2m.testmaker.boundary.access.CmdLineMaker;
import com.github.jorge2m.testmaker.domain.CreatorSuiteRun;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.conf.Suites;

public class RobotestTestRunner {  
	
	public static void main(String[] args) throws Exception { 
		var inputParamsMango = new InputParamsMango(Suites.class, AppEcom.class);
		var cmdLineAccess = CmdLineMaker.from(args, inputParamsMango);
		if (cmdLineAccess.checkOptionsValue().isOk()) {
			execSuite(inputParamsMango);
		}
	}

	/**
	 * Indirect access from Command Line, direct access from Online
	 */
	public static void execSuite(InputParamsMango inputParams) throws Exception {
		var executor = CreatorSuiteRunMango.getNew(inputParams);
		execSuite(executor);
	}
	public static void execSuite(CreatorSuiteRun executor) throws Exception {
		TestMaker.execSuite(executor, false);
	}
	
}

package com.mng.robotest.access;

import com.github.jorge2m.testmaker.boundary.access.CmdLineMaker;
import com.github.jorge2m.testmaker.domain.CreatorSuiteRun;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.conf.AppEcom;
import com.mng.robotest.conf.Suites;

public class CmdRunTests {  
	
	enum TypeCallbackSchema {http, https}
	public enum TypeCallBackMethod {POST, GET}	

	public static void main(String[] args) throws Exception { 
		InputParamsMango inputParamsMango = new InputParamsMango(Suites.class, AppEcom.class);
		CmdLineMaker cmdLineAccess = CmdLineMaker.from(args, inputParamsMango);
		if (cmdLineAccess.checkOptionsValue().isOk()) {
			execSuite(inputParamsMango);
		}
	}

	/**
	 * Indirect access from Command Line, direct access from Online
	 */
	public static void execSuite(InputParamsMango inputParams) throws Exception {
		CreatorSuiteRun executor = CreatorSuiteRunMango.getNew(inputParams);
		execSuite(executor);
	}
	public static void execSuite(CreatorSuiteRun executor) throws Exception {
		TestMaker.execSuite(executor, false);
	}
}

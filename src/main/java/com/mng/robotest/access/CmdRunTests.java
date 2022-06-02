package com.mng.robotest.access;

import java.util.List;

import com.github.jorge2m.testmaker.boundary.access.CmdLineMaker;
import com.github.jorge2m.testmaker.domain.CreatorSuiteRun;
import com.github.jorge2m.testmaker.domain.testfilter.TestMethod;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.conftestmaker.Suites;
import com.mng.robotest.test.suites.*;

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

	public static List<TestMethod> getDataTestAnnotationsToExec(InputParamsMango inputParams) throws Exception {
		Suites suiteValue = (Suites)inputParams.getSuite();
		switch (suiteValue) {
		case SmokeTest:
			SmokeTestSuite smokeTest = new SmokeTestSuite(inputParams);
			return smokeTest.getListTests();
		case SmokeManto:
			SmokeMantoSuite smokeManto = new SmokeMantoSuite(inputParams);
			return smokeManto.getListTests();
		default:
			return null;
		}
	}
}

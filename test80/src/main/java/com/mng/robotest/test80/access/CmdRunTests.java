package com.mng.robotest.test80.access;

import java.net.HttpURLConnection;
import java.util.List;

import com.github.jorge2m.testmaker.boundary.access.CmdLineMaker;
import com.github.jorge2m.testmaker.domain.CreatorSuiteRun;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteTM;
import com.github.jorge2m.testmaker.domain.testfilter.TestMethod;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.conftestmaker.Suites;
import com.mng.robotest.test80.mango.test.suites.*;

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
		SuiteTM suite = TestMaker.execSuite(executor, false);
		callBackIfNeeded(suite);
	}

	private static void callBackIfNeeded(SuiteTM suite) {
		InputParamsMango inputParams = (InputParamsMango)suite.getInputParams();
		CallBack callBack = inputParams.getCallBack();
		if (callBack!=null) {
			String reportTSuiteURL = suite.getDnsReportHtml();
			callBack.setReportTSuiteURL(reportTSuiteURL);
			try {
				HttpURLConnection urlConnection = callBack.callURL();
				suite.getLogger().error("Called CallbackURL" + urlConnection);
			}
			catch (Exception e) {
				suite.getLogger().error("Problem procesing CallBack", e);
			}
		}
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

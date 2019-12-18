package com.mng.sapfiori.access;

import com.mng.sapfiori.access.datatmaker.Apps;
import com.mng.sapfiori.access.datatmaker.Suites;
import com.mng.testmaker.boundary.access.CmdLineMaker;
import com.mng.testmaker.domain.CreatorSuiteRun;
import com.mng.testmaker.domain.InputParamsBasic;

public class CmdRunTests {

	public static void main(String[] args) throws Exception { 
		InputParamsBasic inputParams = new InputParamsBasic(Suites.class, Apps.class);
		CmdLineMaker cmdLineAccess = CmdLineMaker.from(args, inputParams);
		if (cmdLineAccess.checkOptionsValue().isOk()) {
			CreatorSuiteRun creatorSuiteRun = CreatorSuiteRunSapFiori.getNew(inputParams);
			creatorSuiteRun.execTestSuite();
		}
	}
}

package com.mng.sapfiori;

import com.mng.testmaker.boundary.access.CmdLineMaker;
import com.mng.testmaker.domain.CreatorSuiteRun;
import com.mng.sapfiori.datatmaker.Apps;
import com.mng.sapfiori.datatmaker.Suites;

public class CmdRunTests { 

    public static void main(String[] args) throws Exception { 
    	CmdLineMaker cmdLineAccess = CmdLineMaker.from(args, Suites.class, Apps.class);
    	if (cmdLineAccess.checkOptionsValue().isOk()) {
    		CreatorSuiteRun creatorSuiteRun = CreatorSuiteRunSapFiori.getNew(cmdLineAccess.getInputParamsTM());
            creatorSuiteRun.execTestSuite();
    	}
    }
}

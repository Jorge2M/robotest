package com.mng.sapfiori;

import com.mng.testmaker.boundary.access.CmdLineMaker;
import com.mng.testmaker.domain.CreatorSuiteRun;
import com.mng.testmaker.domain.InputParamsBasic;
import com.mng.testmaker.domain.InputParamsTM;
import com.mng.sapfiori.datatmaker.Apps;
import com.mng.sapfiori.datatmaker.Suites;

public class CmdRunTests {  

    public static void main(String[] args) throws Exception { 
    	InputParamsTM inputParams = new InputParamsBasic(Suites.class, Apps.class);
    	CmdLineMaker cmdLineAccess = CmdLineMaker.from(args, inputParams);
    	if (cmdLineAccess.checkOptionsValue().isOk()) {
    		CreatorSuiteRun creatorSuiteRun = CreatorSuiteRunSapFiori.getNew(inputParams);
            creatorSuiteRun.execTestSuite();
    	}
    }
}

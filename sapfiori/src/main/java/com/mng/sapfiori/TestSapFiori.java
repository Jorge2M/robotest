package com.mng.sapfiori;

import java.util.Arrays;

import com.mng.testmaker.boundary.access.CmdLineMaker;
import com.mng.testmaker.domain.InputParamsTM;
import com.mng.testmaker.domain.SuiteTM;
import com.mng.testmaker.service.TestMaker;
import com.mng.sapfiori.datatmaker.Apps;
import com.mng.sapfiori.datatmaker.Suites;
import com.mng.sapfiori.test.suite.SmokeTestSuite;

public class TestSapFiori { 

    public static void main(String[] args) throws Exception { 
    	CmdLineMaker cmdLineAccess = CmdLineMaker.from(args, Suites.class, Apps.class);
    	if (cmdLineAccess.checkOptionsValue()) {
            execTestSuite(cmdLineAccess.getInputParamsTM());
    	}
    }
    
    private static void execTestSuite(InputParamsTM inputParams) throws Exception {
    	SuiteTM suite = makeSuite(inputParams);
    	TestMaker.run(suite);
    }
    
    private static SuiteTM makeSuite(InputParamsTM inputParams) throws Exception {
        try {
            switch ((Suites)inputParams.getSuite()) {
            case SmokeTest:
                return (new SmokeTestSuite(inputParams)).getSuite();
            case OtherSuite:  
            default:
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println("Suite Name not valid. Posible values: " + Arrays.asList(Suites.values()));
        }
        
        return null;
    }
}

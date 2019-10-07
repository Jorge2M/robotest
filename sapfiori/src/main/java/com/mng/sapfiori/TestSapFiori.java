package com.mng.sapfiori;

import java.util.Arrays;

import com.mng.testmaker.access.CommandLineAccess;
import com.mng.testmaker.domain.InputParamsTestMaker;
import com.mng.testmaker.domain.SuiteMaker;
import com.mng.sapfiori.datatmaker.Apps;
import com.mng.sapfiori.datatmaker.Suites;
import com.mng.sapfiori.test.suite.SmokeTestSuite;

public class TestSapFiori { 

    public static void main(String[] args) throws Exception { 
    	CommandLineAccess cmdLineAccess = CommandLineAccess.from(args, Suites.class, Apps.class);
    	if (cmdLineAccess.checkOptionsValue()) {
            execTestSuite(cmdLineAccess.getInputParamsTestMaker());
    	}
    }
    
    private static void execTestSuite(InputParamsTestMaker inputParams) throws Exception {
    	SuiteMaker suite = makeSuite(inputParams);
    	suite.run();
    }
    
    private static SuiteMaker makeSuite(InputParamsTestMaker inputParams) throws Exception {
        try {
            switch ((Suites)inputParams.getSuite()) {
            case SmokeTest:
                return (new SmokeTestSuite(inputParams));
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

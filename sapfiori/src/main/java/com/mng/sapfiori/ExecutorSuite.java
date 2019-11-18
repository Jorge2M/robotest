package com.mng.sapfiori;

import java.util.Arrays;

import com.mng.sapfiori.datatmaker.Suites;
import com.mng.sapfiori.test.suite.SmokeTestSuite;
import com.mng.testmaker.domain.InputParamsTM;
import com.mng.testmaker.domain.SuiteTM;
import com.mng.testmaker.service.TestMaker;

public class ExecutorSuite {

	private ExecutorSuite() {}
	public static ExecutorSuite getNew() {
		return new ExecutorSuite();
	}
	
    public void execTestSuite(InputParamsTM inputParams) throws Exception {
    	SuiteTM suite = makeSuite(inputParams);
    	TestMaker.run(suite);
    }
    
    private SuiteTM makeSuite(InputParamsTM inputParams) throws Exception {
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

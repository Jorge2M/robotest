package com.mng.sapfiori;

import java.util.Arrays;

import com.mng.sapfiori.datatmaker.Suites;
import com.mng.sapfiori.test.suite.SmokeTestSuite;
import com.mng.testmaker.domain.ExecutorSuite;
import com.mng.testmaker.domain.InputParamsTM;
import com.mng.testmaker.domain.SuiteTM;

public class ExecutorSuiteSapFiori extends ExecutorSuite {

	private ExecutorSuiteSapFiori(InputParamsTM inputParams) throws Exception {
		super(inputParams);
	}
	public static ExecutorSuiteSapFiori getNew(InputParamsTM inputParams) throws Exception {
		return new ExecutorSuiteSapFiori(inputParams);
	}
	
    @Override
    public SuiteTM makeSuite() throws Exception {
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

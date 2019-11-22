package com.mng.sapfiori;

import java.util.Arrays;

import com.mng.sapfiori.datatmaker.Suites;
import com.mng.sapfiori.test.suite.SmokeTestSuite;
import com.mng.testmaker.domain.CreatorSuiteRun;
import com.mng.testmaker.domain.InputParamsTM;
import com.mng.testmaker.domain.SuiteMaker;

public class CreatorSuiteRunSapFiori extends CreatorSuiteRun {

	private CreatorSuiteRunSapFiori(InputParamsTM inputParams) throws Exception {
		super(inputParams);
	}
	public static CreatorSuiteRunSapFiori getNew(InputParamsTM inputParams) throws Exception {
		return new CreatorSuiteRunSapFiori(inputParams);
	}
	
    @Override
    public SuiteMaker getSuiteMaker() throws Exception {
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

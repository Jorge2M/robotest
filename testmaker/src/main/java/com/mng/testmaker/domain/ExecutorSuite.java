package com.mng.testmaker.domain;

import com.mng.testmaker.service.TestMaker;

public abstract class ExecutorSuite {
	
	protected final InputParamsTM inputParams;
	protected final SuiteTM suite;
	
	public abstract SuiteTM makeSuite() throws Exception;
	
	public ExecutorSuite(InputParamsTM inputParams) throws Exception {
		this.inputParams = inputParams;
		this.suite = makeSuite();
	}
	
	public SuiteTM getSuite() {
		return suite;
	}
	
    public SuiteTM execTestSuite() throws Exception {
    	TestMaker.run(suite);
    	return suite;
    }
    
    public SuiteTM execTestSuiteAsync() throws Exception {
    	TestMaker.runAsync(suite);
    	return suite;
    }
}

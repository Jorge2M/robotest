package com.mng.robotest.domains.base;

import com.mng.robotest.domains.base.datatest.DataMantoTest;

public abstract class TestMantoBase extends StepMantoBase {

	public abstract void execute() throws Exception;
	
	public static final ThreadLocal<DataMantoTest> DATA_MANTO_TEST = new ThreadLocal<>();
	
	protected TestMantoBase() {
		dataMantoTest = DataMantoTest.make();
		DATA_MANTO_TEST.set(dataMantoTest);
	}
}

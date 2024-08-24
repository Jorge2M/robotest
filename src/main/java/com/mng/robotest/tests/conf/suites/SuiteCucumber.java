package com.mng.robotest.tests.conf.suites;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.tests.domains.Cucumber;

public class SuiteCucumber extends SuiteMangoMaker {

	public SuiteCucumber(InputParamsMango inputParams) {
		super(inputParams);
	}

	@Override
	List<Class<?>> getClasses() {
		return Arrays.asList(
			Cucumber.class
		);
	}

}

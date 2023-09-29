package com.mng.robotest.tests.conf.suites;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.tests.domains.legal.tests.TextosLegales;

public class TextosLegalesSuite extends SuiteMangoMaker {

	public TextosLegalesSuite(InputParamsMango inputParams) {
		super(inputParams);
	}

	@Override
	List<Class<?>> getClasses() {
		return Arrays.asList(TextosLegales.class);
	}

}

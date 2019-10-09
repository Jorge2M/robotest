package com.mng.testmaker.domain;

import java.util.ArrayList;
import java.util.List;

public class SuitesExecuted {

	private static final List<SuiteTestMaker> suitesExecuted = new ArrayList<>();
	
	public static void add(SuiteTestMaker suite) {
		suitesExecuted.add(suite); 
	}
	
	public static void remove(SuiteTestMaker suite) {
		suitesExecuted.remove(suite);
	}
	
	public static List<SuiteTestMaker> getSuitesExecuted() {
		return suitesExecuted;
	}
	
	public static SuiteTestMaker getSuite(String idExecution) {
		for (SuiteTestMaker suite : suitesExecuted) {
			if (idExecution.compareTo(suite.getIdExecution())==0) {
				return suite;
			}
		}
		return null;
	}
	
//	SuiteTestMaker getSuite(String idSuite) {
//		listSuites.get(1).
//	}
	
}

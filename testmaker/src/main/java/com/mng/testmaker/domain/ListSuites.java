package com.mng.testmaker.domain;

import java.util.ArrayList;
import java.util.List;

public class ListSuites {

	private static final List<SuiteTestMaker> listSuites = new ArrayList<>();
	
	void addSuite(SuiteTestMaker suite) {
		listSuites.add(suite);
	}
	
	void removeSuite(SuiteTestMaker suite) {
		listSuites.remove(suite);
	}
	
	SuiteTestMaker getSuite(String idSuite) {
		listSuites.get(1).
	}
	
}

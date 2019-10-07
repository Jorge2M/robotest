package com.mng.testmaker.domain;

import java.util.ArrayList;
import java.util.List;

import org.testng.xml.XmlSuite;

public class SuiteTestMaker extends XmlSuite {
	
	private static final long serialVersionUID = 1L;
	private final SuiteContextTestMaker testMakerContext;
	private List<TestRunMaker> listTestRuns = new ArrayList<>();
	
	public SuiteTestMaker(SuiteContextTestMaker testMakerContext) {
		this.testMakerContext = testMakerContext;
	}
	
	public SuiteContextTestMaker getTestMakerContext() {
		return this.testMakerContext;
	}
	
	public void setListenersClass(List<Class<?>> listListeners) {
		List<String> listListenersStr = new ArrayList<>();
		for (Class<?> listener : listListeners) {
			listListenersStr.add(listener.getCanonicalName());
		}
		setListeners(listListenersStr);
	}
}

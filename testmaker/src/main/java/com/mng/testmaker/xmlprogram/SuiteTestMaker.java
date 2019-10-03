package com.mng.testmaker.xmlprogram;

import java.util.ArrayList;
import java.util.List;

import org.testng.xml.XmlSuite;

import com.mng.testmaker.data.TestMakerContext;

public class SuiteTestMaker extends XmlSuite {
	
	private static final long serialVersionUID = 1L;
	private final TestMakerContext testMakerContext;
	
	public SuiteTestMaker(TestMakerContext testMakerContext) {
		this.testMakerContext = testMakerContext;
	}
	
	public TestMakerContext getTestMakerContext() {
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

package com.mng.robotest.test80.arq.xmlprogram;

import org.testng.xml.XmlSuite;

import com.mng.robotest.test80.data.TestMakerContext;

public class SuiteTestMaker extends XmlSuite {
	
	private static final long serialVersionUID = 1L;
	private final TestMakerContext testMakerContext;
	
	private SuiteTestMaker(TestMakerContext testMakerContext) {
		this.testMakerContext = testMakerContext;
	}
	
	public static SuiteTestMaker getNew(TestMakerContext testMakerContext) {
		return (new SuiteTestMaker(testMakerContext));
	}
	
	public TestMakerContext getTestMakerContext() {
		return this.testMakerContext;
	}
}
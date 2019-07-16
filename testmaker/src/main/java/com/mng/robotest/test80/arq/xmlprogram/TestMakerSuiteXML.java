package com.mng.robotest.test80.arq.xmlprogram;

import org.testng.xml.XmlSuite;

import com.mng.robotest.test80.TestMaker;
import com.mng.robotest.test80.arq.utils.filter.FilterTestsSuiteXML;

public abstract class TestMakerSuiteXML extends FilterTestsSuiteXML {

	protected final XmlSuite xmlSuite;
	protected final TestMaker testMaker;
    protected final ParamsBean params;

	
	public TestMakerSuiteXML(ParamsBean params, XmlSuite xmlSuite, TestMaker testMaker) {
		super(params);
		this.params = params;
		this.xmlSuite = xmlSuite;
		this.testMaker = testMaker;
	}
	
	public TestMaker getTestMaker(XmlSuite xmlSuite) {
		return this.testMaker;
	}
}

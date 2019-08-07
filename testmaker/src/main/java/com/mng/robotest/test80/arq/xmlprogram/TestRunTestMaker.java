package com.mng.robotest.test80.arq.xmlprogram;

import org.testng.xml.XmlGroups;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.mng.robotest.test80.arq.utils.webdriver.BrowserStackDesktop;
import com.mng.robotest.test80.arq.utils.webdriver.BrowserStackMobil;

public class TestRunTestMaker extends XmlTest {

	private static final long serialVersionUID = -4002416107477209626L;
    public XmlGroups x_xmlGroupsVisible;
	private BrowserStackDesktop browserStackDesktop = null;
	private BrowserStackMobil browserStackMobil = null;
	
    public TestRunTestMaker(XmlSuite suite, int index) {
        super(suite, index);
    }

    public TestRunTestMaker(XmlSuite suite) {
        super(suite);
    }
    
    public XmlGroups getGroups() {
        return this.x_xmlGroupsVisible;
    }
    
    @Override
    public void setGroups(XmlGroups xmlGroups) {
        super.setGroups(xmlGroups);
        this.x_xmlGroupsVisible = xmlGroups;
    }
	
	public void setBrowserStackDesktop(BrowserStackDesktop browserStackDesktop) {
		this.browserStackDesktop = browserStackDesktop;
	}
	
	public void setBrowserStackMobil(BrowserStackMobil browserStackMobil) {
		this.browserStackMobil = browserStackMobil;
	}
	
	public BrowserStackDesktop getBrowserStackDesktop() {
		return this.browserStackDesktop;
	}
	
	public BrowserStackMobil getBrowserStackMobil() {
		return this.browserStackMobil;
	}
}

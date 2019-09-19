package com.mng.robotest.test80.arq.access;

import java.util.List;

import com.mng.robotest.test80.arq.utils.filter.DataFilterTCases;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.utils.webdriver.maker.FactoryWebdriverMaker.TypeWebDriver;

public class InputParamsTestMaker {
	
	public enum ManagementWebdriver {recycle, discard}
	
	private Channel channel;
	private Enum<?> suite;
	private Enum<?> app;
    private String versionSuite;
    private TypeWebDriver typeWebDriver;
    private String urlBase;
    
    private ManagementWebdriver typeManageWebdriver = ManagementWebdriver.discard;
    private boolean netAnalysis = false;  
    private boolean recicleWD = false;
    private String webAppDNS;
    private List<String> testCasesFilter;
    private List<String> groupsFilter;
    private List<String> mails;
    
    public InputParamsTestMaker() {}
    
    public InputParamsTestMaker(Channel channel, Enum<?> suite, Enum<?> app, String urlBase, TypeWebDriver typeWebDriver) {
    	this.channel = channel;
    	this.suite = suite;
    	this.app = app;
    	this.versionSuite = "V1";
    	this.urlBase = urlBase;
    	this.typeWebDriver = typeWebDriver;
    }
//    
//    public InputParamsTestMaker(Channel channel, Enum<?> suite, Enum<?> app, String versionSuite, String urlBase, TypeWebDriver typeWebDriver) {
//    	this.channel = channel;
//    	this.suite = suite;
//    	this.app = app;
//    	this.versionSuite = versionSuite;
//    	this.urlBase = urlBase;
//    	this.typeWebDriver = typeWebDriver;
//    }

    public String getMoreInfo() {
    	return "";
    }
    
	public Channel getChannel() {
		return channel;
	}
	
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
    
	public Enum<?> getSuite() {
		return suite;
	}
	
	public void setSuite(Enum<?> suite) {
		this.suite = suite;
	}
	
	public String getSuiteName() {
		return suite.name();
	}

	public Enum<?> getApp() {
		return app;
	}
	
	public void setApp(Enum<?> app) {
		this.app = app;
	}
    
	public String getVersionSuite() {
		return versionSuite;
	}
	
	public void setVersionSuite(String versionSuite) {
		this.versionSuite = versionSuite;
	}
	
	public TypeWebDriver getTypeWebDriver() {
		return typeWebDriver;
	}
	
	public void setTypeWebDriver(TypeWebDriver typeWebDriver) {
		this.typeWebDriver = typeWebDriver;
	}
	
	public ManagementWebdriver getTypeManageWebdriver() {
		return typeManageWebdriver;
	}

	public void setTypeManageWebdriver(ManagementWebdriver typeManageWebdriver) {
		this.typeManageWebdriver = typeManageWebdriver;
	}
	
	public String getUrlBase() {
		return this.urlBase;
	}
	
	public void setUrlBase(String urlBase) {
		this.urlBase = urlBase;
	}
	
	public boolean isNetAnalysis() {
		return netAnalysis;
	}

	public void setNetAnalysis(boolean netAnalysis) {
		this.netAnalysis = netAnalysis;
	}
	
	public void setNetAnalysis(String netAnalysis) {
		this.netAnalysis = "true".compareTo(netAnalysis) == 0;
	}
	
	public boolean getRecicleWD() {
		return this.recicleWD;
	}
	
	public void setRecicleWD(boolean recicleWD) {
		this.recicleWD = recicleWD;
	}
	
	public String getWebAppDNS() {
		return this.webAppDNS;
	}
	
	public void setWebAppDNS(String webAppDNS) {
		this.webAppDNS = webAppDNS;
	}
	
	public void setRecicleWD(String recicleWD) {
		this.recicleWD = "true".compareTo(recicleWD) == 0;
	}

	public List<String> getTestCasesFilter() {
		return testCasesFilter;
	}

	public void setTestCasesFilter(List<String> testCasesFilter) {
		this.testCasesFilter = testCasesFilter;
	}
	
	public List<String> getGroupsFilter() {
		return groupsFilter;
	}

	public void setGroupsFilter(List<String> groupsFilter) {
		this.groupsFilter = groupsFilter;
	}


	public List<String> getMails() {
		return this.mails;
	}
	
	public void setMails(List<String> mails) {
		this.mails = mails;
	}
	
	public boolean isSendMailInEndSuite() {
		return (getMails()!=null && getMails().size()>0);
	}
	
    public DataFilterTCases getDataFilter() {
    	DataFilterTCases dFilter = new DataFilterTCases(getChannel(), getApp());
    	dFilter.setGroupsFilter(groupsFilter);
    	dFilter.setTestCasesFilter(testCasesFilter);
    	return dFilter;
    }
}

package com.mng.testmaker.domain;

import java.util.List;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.domain.testfilter.DataFilterTCases;
import com.mng.testmaker.service.webdriver.maker.FactoryWebdriverMaker.WebDriverType;

public class InputParamsTestMaker {
	
	public enum ManagementWebdriver {recycle, discard}
	
	private Channel channel;
	private Enum<?> suite;
	private Enum<?> app;
    private String versionSuite;
    private WebDriverType webDriverType;
    private String urlBase;
    
    private ManagementWebdriver typeManageWebdriver = ManagementWebdriver.discard;
    private boolean netAnalysis = false;  
    private boolean recicleWD = false;
    private String webAppDNS;
    private List<String> testCasesFilter;
    private List<String> groupsFilter;
    private List<String> mails;
    
    public InputParamsTestMaker() {}
    
    public InputParamsTestMaker(Channel channel, Enum<?> suite, Enum<?> app, String urlBase, WebDriverType webDriverType) {
    	this.channel = channel;
    	this.suite = suite;
    	this.app = app;
    	this.versionSuite = "V1";
    	this.urlBase = urlBase;
    	this.webDriverType = webDriverType;
    }

    public String getMoreInfo() {
    	return "";
    }
    
	public Channel getChannel() {
		return channel;
	}
	
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	
	public void setChannel(String channel) {
		setChannel(Channel.valueOf(channel));
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
	
	public WebDriverType getWebDriverType() {
		return webDriverType;
	}
	
	public void setWebDriverType(WebDriverType webDriverType) {
		this.webDriverType = webDriverType;
	}
	
	public ManagementWebdriver getTypeManageWebdriver() {
		return typeManageWebdriver;
	}

	public void setTypeManageWebdriver(ManagementWebdriver typeManageWebdriver) {
		this.typeManageWebdriver = typeManageWebdriver;
	}
	
	public void setBrowser(String browser) {
		setWebDriverType(WebDriverType.valueOf(browser));
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
		if (recicleWD!=null) {
			this.recicleWD = "true".compareTo(recicleWD) == 0;
		}
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

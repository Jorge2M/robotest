package com.mng.robotest.test80.arq.xmlprogram;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.listeners.CallBack;
import com.mng.robotest.test80.arq.utils.conf.AppTest;
import com.mng.robotest.test80.arq.utils.filter.DataFilterTCases;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.utils.otras.TypeAccessFmwk;
import com.mng.robotest.test80.arq.utils.webdriver.maker.FactoryWebdriverMaker.TypeWebDriver;

public class InputDataTestMaker {
	
	public enum ManagementWebdriver {recycle, discard}
	
    private final String nameSuite;
    private final String versionSuite;
	private final Channel channel;
    private final AppTest app;
    private final TypeWebDriver typeWebDriver;
    private final String urlBase;
    
    private TypeAccessFmwk typeAccess = TypeAccessFmwk.CommandLine;
    private ManagementWebdriver typeManageWebdriver = ManagementWebdriver.discard;
    private boolean netAnalysis = false;  
    private List<String> testCasesFilter;
    private List<String> groupsFilter;
    private List<String> mails;
    private List<String> countrys = new ArrayList<>();
    private String webAppDNS;
    private CallBack callBack = null;
	
    private InputDataTestMaker(String nameSuite, Channel channel, AppTest app, String urlBase, TypeWebDriver typeWebDriver) {
    	this.nameSuite = nameSuite;
    	this.versionSuite = "V1";
    	this.channel = channel;
    	this.app = app;
    	this.urlBase = urlBase;
    	this.typeWebDriver = typeWebDriver;
    }
    
    private InputDataTestMaker(String nameSuite, String versionSuite, Channel channel, AppTest app, String urlBase, TypeWebDriver typeWebDriver) {
    	this.nameSuite = nameSuite;
    	this.versionSuite = versionSuite;
    	this.channel = channel;
    	this.app = app;
    	this.urlBase = urlBase;
    	this.typeWebDriver = typeWebDriver;
    }
    
    public static InputDataTestMaker getNew(String nameSuite, Channel channel, AppTest app, String urlBase, TypeWebDriver typeWebDriver) {
    	return (new InputDataTestMaker(nameSuite, channel, app, urlBase, typeWebDriver));
    }
    
    public static InputDataTestMaker getNew(String nameSuite, String versionSuite, Channel channel, AppTest app, String urlBase, TypeWebDriver typeWebDriver) {
    	return (new InputDataTestMaker(nameSuite, versionSuite, channel, app, urlBase, typeWebDriver));
    }

	public String getNameSuite() {
		return nameSuite;
	}
	
	public String getVersionSuite() {
		return versionSuite;
	}

	public Channel getChannel() {
		return channel;
	}

	public AppTest getApp() {
		return app;
	}
    
	public TypeWebDriver getTypeWebDriver() {
		return typeWebDriver;
	}
	
	public ManagementWebdriver getTypeManageWebdriver() {
		return typeManageWebdriver;
	}

	public void setTypeManageWebdriver(ManagementWebdriver typeManageWebdriver) {
		this.typeManageWebdriver = typeManageWebdriver;
	}

	public TypeAccessFmwk getTypeAccess() {
		return typeAccess;
	}

	public void setTypeAccess(TypeAccessFmwk typeAccess) {
		this.typeAccess = typeAccess;
	}
	
	public String getUrlBase() {
		return this.urlBase;
	}
	
	public boolean isNetAnalysis() {
		return netAnalysis;
	}

	public void setNetAnalysis(boolean netAnalysis) {
		this.netAnalysis = netAnalysis;
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
	
	public String getWebAppDNS() {
		return this.webAppDNS;
	}
	
	public void setWebAppDNS(String webAppDNS) {
		this.webAppDNS = webAppDNS;
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
	
	public List<String> getCountrys() {
		return this.countrys;
	}
	
	public void setCountrys(List<String> countrys) {
		this.countrys = countrys;
	}

	public CallBack getCallBack() {
		return callBack;
	}

	public void setCallBack(CallBack callBack) {
		this.callBack = callBack;
	}

    public DataFilterTCases getDataFilter() {
    	DataFilterTCases dFilter = new DataFilterTCases(getChannel(), getApp());
    	dFilter.setGroupsFilter(groupsFilter);
    	dFilter.setTestCasesFilter(testCasesFilter);
    	return dFilter;
    }
}

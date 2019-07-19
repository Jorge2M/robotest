package com.mng.robotest.test80.arq.xmlprogram;

import java.util.List;

import com.mng.robotest.test80.arq.listeners.CallBack;
import com.mng.robotest.test80.arq.utils.conf.AppTest;
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
    
    private ManagementWebdriver typeManageWebdriver = ManagementWebdriver.discard;
    private TypeAccessFmwk typeAccess = TypeAccessFmwk.CommandLine;
    private String urlBase;
    private boolean netAnalysis = false;  
    private List<String> testCasesFilter;
    private List<String> groupsFilter;
    private String envioCorreo = null;
    private CallBack callBack = null;
	
    public InputDataTestMaker(String nameSuite, Channel channel, AppTest app, TypeWebDriver typeWebDriver) {
    	this.nameSuite = nameSuite;
    	this.versionSuite = "V1";
    	this.channel = channel;
    	this.app = app;
    	this.typeWebDriver = typeWebDriver;
    }
    
    public InputDataTestMaker(String nameSuite, String versionSuite, Channel channel, AppTest app, TypeWebDriver typeWebDriver) {
    	this.nameSuite = nameSuite;
    	this.versionSuite = versionSuite;
    	this.channel = channel;
    	this.app = app;
    	this.typeWebDriver = typeWebDriver;
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
	
	public void setUrlBase(String urlBase) {
		this.urlBase = urlBase;
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

	public String getEnvioCorreo() {
		return envioCorreo;
	}

	public void setEnvioCorreo(String envioCorreo) {
		this.envioCorreo = envioCorreo;
	}
	
	public boolean isEnvioCorreoGroup() {
		return getEnvioCorreo()!=null && "".compareTo(getEnvioCorreo())!=0;
	}

	public CallBack getCallBack() {
		return callBack;
	}

	public void setCallBack(CallBack callBack) {
		this.callBack = callBack;
	}

    public DataFilter getDataFilter() {
    	return(
    		new DataFilter() {
		        public AppTest getAppE() {
		            return getApp();
		        }
		        public Channel getChannel() {
		            return getChannel();
		        }
		        public List<String> getGroupsFilter() {
		        	return (getGroupsFilter());
		        }
		        public List<String> getTestCasesFilter() {
		        	return (getTestCasesFilter());
		        }
	    	});
    }
}

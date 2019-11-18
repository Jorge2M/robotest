package com.mng.testmaker.domain;

import java.util.Arrays;
import java.util.List;
import javax.ws.rs.FormParam;
import org.apache.commons.cli.CommandLine;

import com.mng.testmaker.boundary.access.CmdLineMaker;
import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.domain.testfilter.DataFilterTCases;
import com.mng.testmaker.service.webdriver.maker.FactoryWebdriverMaker.WebDriverType;

public class InputParamsTM {

    public static final String SuiteNameParam = "suite";
    public static final String GroupsNameParam = "groups";
    public static final String BrowserNameParam = "browser";
    public static final String ChannelNameParam = "channel";
    public static final String AppNameParam = "application";
    public static final String VersionNameParam = "version";
    public static final String URLNameParam = "url";
    public static final String TCaseNameParam = "tcases";
    
    public static final String ServerDNSNameParam = "serverDNS";
    public static final String RecicleWD = "reciclewd";
    public static final String NetAnalysis = "net";
    public static final String Store = "store";
    public static final String Mails = "mails";
    
	public enum ManagementWebdriver {recycle, discard}
	private Class<? extends Enum<?>> suiteEnum;
	private Class<? extends Enum<?>> appEnum;
    
    @FormParam(SuiteNameParam)
    String suite;
    
    @FormParam(GroupsNameParam)
    String[] groups;
    
    @FormParam(BrowserNameParam)
    String browser;
    
    @FormParam(ChannelNameParam)
    String channel;
    
    @FormParam(AppNameParam)
    String application;
    
    @FormParam(VersionNameParam)
    String version;
    
    @FormParam(URLNameParam)
    String url;
    
    @FormParam(TCaseNameParam)
    String[] tcases;
    
    @FormParam(ServerDNSNameParam)
    String serverDNS;
    
    @FormParam(RecicleWD)
    String reciclewd;
    
    @FormParam(NetAnalysis)
    String net;
    
    @FormParam(Store)
    String store;
    
    @FormParam(Mails)
    String[] mails;
    
    public InputParamsTM() {}
    
    public InputParamsTM(CmdLineMaker cmdLineMaker) {
    	this(cmdLineMaker.getComandLineData(), cmdLineMaker.getSuiteEnum(), cmdLineMaker.getAppEnum());
    }
    
	public InputParamsTM(CommandLine cmdLine, Class<? extends Enum<?>> suiteEnum, Class<? extends Enum<?>> appEnum) {
		this(suiteEnum, appEnum);
        channel = cmdLine.getOptionValue(ChannelNameParam);
        suite = cmdLine.getOptionValue(SuiteNameParam);
        application = cmdLine.getOptionValue(AppNameParam);
    	version = cmdLine.getOptionValue(VersionNameParam);
    	url = cmdLine.getOptionValue(URLNameParam);
    	browser = cmdLine.getOptionValue(BrowserNameParam);
    	serverDNS = cmdLine.getOptionValue(ServerDNSNameParam);
    	groups = cmdLine.getOptionValues(GroupsNameParam);
    	tcases = cmdLine.getOptionValues(TCaseNameParam);
    	mails = cmdLine.getOptionValues(Mails);
    	reciclewd = cmdLine.getOptionValue(RecicleWD);
    	net = cmdLine.getOptionValue(NetAnalysis);
    	store = cmdLine.getOptionValue(Store);
	}
	
    public InputParamsTM(Class<? extends Enum<?>> suiteEnum, Class<? extends Enum<?>> appEnum) {
		this.suiteEnum = suiteEnum;
		this.appEnum = appEnum;
    }

    public Channel getChannel() {
    	return Channel.valueOf(channel);
    }
    public void setChannel(String channel) {
    	this.channel = channel;
    }
    public void setChannel(Channel channel) {
    	this.channel = channel.name();
    }
    public Enum<?> getSuite() {
    	return (valueOf(suiteEnum.getEnumConstants(), suite));
    }
	public String getSuiteName() {
		return suite;
	}
	public void setSuite(String suite) {
		this.suite = suite;
	}
	public void setSuite(Enum<?> suite) {
		this.suite = suite.name();
	}
    public Enum<?> getApp() {
    	return (valueOf(appEnum.getEnumConstants(), application));
    }
    public void setApp(String app) {
    	this.application = app;
    }
    public void setApp(Enum<?> app) {
    	this.application = app.name();
    }
    public String getVersion() {
		if (version==null) {
			version = "V1";
		}
		return version;
    }
    public void setVersion(String version) {
    	this.version = version;
    }
    public String getUrlBase() {
    	return url;
    }
    public void setUrlBase(String urlBase) {
    	this.url = urlBase;
    }
    public WebDriverType getWebDriverType() {
    	return (WebDriverType.valueOf(browser));
    }
    public void setWebDriverType(WebDriverType webDriverType) {
    	this.browser = webDriverType.name();
    }
    public void setBrowser(String browser) {
    	this.browser = browser;
    }
    public String getWebAppDNS() {
    	return serverDNS;
    }
    public void setWebAppDNS(String serverDNS) {
    	this.serverDNS = serverDNS;
    }
    public List<String> getGroupsFilter() {
    	if (groups!=null) {
    		return Arrays.asList(groups);
    	}
    	return Arrays.asList();
    }
	public List<String> getTestCasesFilter() {
		if (tcases!=null) {
			return Arrays.asList(tcases);
		}
		return Arrays.asList();
	}
	public void setTestCasesFilter(List<String> listTestCases) {
		tcases = listTestCases.toArray(new String[listTestCases.size()]);
	}
	public List<String> getMails() {
		if (mails!=null) {
			return Arrays.asList(mails);
		}
		return Arrays.asList();
	}
	public void setMails(List<String> mails) {
		this.mails = mails.toArray(new String[mails.size()]);
	}
	public boolean isSendMailInEndSuite() {
		return (getMails()!=null && getMails().size()>0);
	}
	public boolean getRecicleWD() {
		if (reciclewd!=null) {
	    	return ("true".compareTo(reciclewd)==0);
		}
		return false;
	}
	public void setRecicleWD(String reciclewd) {
		this.reciclewd = reciclewd;
	}
	public ManagementWebdriver getTypeManageWebdriver() {
		if (getRecicleWD()) {
			return ManagementWebdriver.recycle;
		}
		return ManagementWebdriver.discard;
	}
	public void setNetAnalysis(String net) {
		this.net = net;
	}
	public boolean isNetAnalysis() {
		if (net!=null) {
			return ("true".compareTo(net)==0);
		}
		return false;
	} 
	public void setStoreResult(boolean store) {
		this.store = String.valueOf(store);
	}
	public boolean isStoreResult() {
		if (store!=null) {
			return ("true".compareTo(store)==0);
		}
		return false;
	}
    public String getMoreInfo() {
    	return "";
    }
	
    private static Enum<?> valueOf(Enum<?>[] enumConstants, String value) throws IllegalArgumentException {
    	for (Enum<?> enumCandidate : enumConstants) {
    		if (enumCandidate.name().equals(value)) {
    			return enumCandidate;
    		}
    	}
    	throw new IllegalArgumentException();
    }
    
    public DataFilterTCases getDataFilter() {
    	DataFilterTCases dFilter = new DataFilterTCases(getChannel(), getApp());
    	dFilter.setGroupsFilter(getGroupsFilter());
    	dFilter.setTestCasesFilter(getTestCasesFilter());
    	return dFilter;
    }

}

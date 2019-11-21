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
	public static final String RecicleWDParam = "reciclewd";
	public static final String NetAnalysisParam = "net";
	public static final String StoreParam = "store";
	public static final String MailsParam = "mails";
	public static final String TypeAccessParam = "typeAccess";
	
	public enum ParamTM {
		Suite(SuiteNameParam),
		Groups(GroupsNameParam),
		Browser(BrowserNameParam),
		Channel(ChannelNameParam),
		Application(AppNameParam),
		Version(VersionNameParam),
		Url(URLNameParam),
		Tcases(TCaseNameParam),
		ServerDNS(ServerDNSNameParam),
		RecicleWD(RecicleWDParam),
		NetAnalysis(NetAnalysisParam),
		Store(StoreParam),
		Mails(MailsParam),
		TypeAccess(TypeAccessParam);
		
		public String nameParam;
		private ParamTM(String nameParam) {
			this.nameParam = nameParam;
		}
	}

	public enum ManagementWebdriver {recycle, discard}
	private Class<? extends Enum<?>> suiteEnum;
	private Class<? extends Enum<?>> appEnum;
	
	public enum TypeAccess {Rest, CmdLine, Bat}

	@FormParam(SuiteNameParam)
	String suite;

	@FormParam(GroupsNameParam)
	String groupsCommaSeparated;

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
	String tcasesCommaSeparated;

	@FormParam(ServerDNSNameParam)
	String serverDNS;

	@FormParam(RecicleWDParam)
	String reciclewd;

	@FormParam(NetAnalysisParam)
	String net;

	@FormParam(StoreParam)
	String store;

	@FormParam(MailsParam)
	String mailsCommaSeparated;
	
	@FormParam(TypeAccessParam)
	String typeAccess = TypeAccess.CmdLine.name();

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
		String[] groups = cmdLine.getOptionValues(GroupsNameParam);
		if (groups!=null) {
			groupsCommaSeparated = String.join(",", groups);
		}
		String[] tcases = cmdLine.getOptionValues(TCaseNameParam);
		if (tcases!=null) {
			tcasesCommaSeparated = String.join(",", tcases);
		}
		String[] mails = cmdLine.getOptionValues(MailsParam);
		if (mails!=null) {
			mailsCommaSeparated = String.join(",", mails);
		}
		reciclewd = cmdLine.getOptionValue(RecicleWDParam);
		net = cmdLine.getOptionValue(NetAnalysisParam);
		store = cmdLine.getOptionValue(StoreParam);
		typeAccess = cmdLine.getOptionValue(TypeAccessParam);
	}
	
	public InputParamsTM(Class<? extends Enum<?>> suiteEnum, Class<? extends Enum<?>> appEnum) {
		this.suiteEnum = suiteEnum;
		this.appEnum = appEnum;
	}

	public Class<? extends Enum<?>> getSuiteEnum() {
		return suiteEnum;
	}
	public void setSuiteEnum(Class<? extends Enum<?>> suiteEnum) {
		this.suiteEnum = suiteEnum;
	}
	public Class<? extends Enum<?>> getAppEnum() {
		return appEnum;
	}
	public void setAppEnum(Class<? extends Enum<?>> appEnum) {
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
    	if (groupsCommaSeparated!=null) {
    		String[] groups = groupsCommaSeparated.split(",");
    		return Arrays.asList(groups);
    	}
    	return Arrays.asList();
    }
	public List<String> getTestCasesFilter() {
		if (tcasesCommaSeparated!=null) {
			String[] tcases = tcasesCommaSeparated.split(",");
			return Arrays.asList(tcases);
		}
		return Arrays.asList();
	}
	public void setTestCasesFilter(List<String> listTestCases) {
		String[] tcases = listTestCases.toArray(new String[listTestCases.size()]);
		tcasesCommaSeparated = String.join(",", tcases);
	}
	public List<String> getMails() {
		if (mailsCommaSeparated!=null) {
			String[] mails = mailsCommaSeparated.split(",");
			return Arrays.asList(mails);
		}
		return Arrays.asList();
	}
	public void setMails(List<String> mailsInput) {
		String[] mails = mailsInput.toArray(new String[mailsInput.size()]);
		mailsCommaSeparated = String.join(",", mails);
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

	public TypeAccess getTypeAccess() {
		if (typeAccess==null || "".compareTo(typeAccess)==0) {
			return TypeAccess.CmdLine;
		}
		return TypeAccess.valueOf(typeAccess);
	}
	public void setTypeAccess(TypeAccess typeAccess) {
		this.typeAccess = typeAccess.name();
	}

	public DataFilterTCases getDataFilter() {
		DataFilterTCases dFilter = new DataFilterTCases(getChannel(), getApp());
		dFilter.setGroupsFilter(getGroupsFilter());
		dFilter.setTestCasesFilter(getTestCasesFilter());
		return dFilter;
	}

	public String getParamValue(ParamTM paramTM) {
		switch (paramTM) {
			case Suite:
				return suite;
			case Groups:
				return groupsCommaSeparated;
			case Browser:
				return browser;
			case Channel:
				return channel;
			case Application:
				return application;
			case Version:
				return version;
			case Url:
				return url;
			case Tcases:
				return tcasesCommaSeparated;
			case ServerDNS:
				return serverDNS;
			case RecicleWD:
				return reciclewd;
			case NetAnalysis:
				return net;
			case Store:
				return store;
			case Mails:
				return mailsCommaSeparated;
			case TypeAccess:
				return typeAccess;
		}
		return "";
	}

}

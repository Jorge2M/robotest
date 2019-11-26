package com.mng.testmaker.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;

import org.apache.commons.cli.CommandLine;

import com.mng.testmaker.boundary.access.OptionTMaker;
import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.domain.testfilter.DataFilterTCases;
import com.mng.testmaker.service.webdriver.maker.FactoryWebdriverMaker.WebDriverType;

public abstract class InputParamsTM {

	abstract public List<OptionTMaker> getSpecificParameters();
	abstract public void setSpecificDataFromCommandLine(CommandLine cmdLine);
	abstract public Map<String,String> getSpecificParamsValues();
	
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


	public InputParamsTM(Class<? extends Enum<?>> suiteEnum, Class<? extends Enum<?>> appEnum) {
		this.suiteEnum = suiteEnum;
		this.appEnum = appEnum;
	}
	
	public List<OptionTMaker> getListAllOptions() {
		List<OptionTMaker> listAllOptions = new ArrayList<>();
		listAllOptions.addAll(getTmParameters());
		List<OptionTMaker> listSpecificParams = getSpecificParameters();
		if (listSpecificParams!=null) {
			listAllOptions.addAll(listSpecificParams);
		}
		return listAllOptions; 
	}
	
	public void setAllDataFromCommandLine(CommandLine cmdLine) {
		setTmDataFromCommandLine(cmdLine);
		setSpecificDataFromCommandLine(cmdLine);
	}
	
	public Map<String, String> getAllParamsValues() {
		Map<String, String> paramsValuesTM = getTmParamsValues();
		paramsValuesTM.putAll(getSpecificParamsValues());
		return paramsValuesTM;
	}
	
	private List<OptionTMaker> getTmParameters() {
		List<OptionTMaker> listOptionsTMaker = new ArrayList<>();
        OptionTMaker suite = OptionTMaker.builder(InputParamsTM.SuiteNameParam)
            .required(true)
            .hasArg()
            .possibleValues(suiteEnum)
            .desc("Test Suite to execute. Possible values: " + Arrays.asList(suiteEnum.getEnumConstants()))
            .build();
        
        OptionTMaker groups = OptionTMaker.builder(InputParamsTM.GroupsNameParam)
            .required(false)
            .hasArg()
            .valueSeparator(',')
            .desc("Groups of tests to include")
            .build();
                 
        OptionTMaker browser = OptionTMaker.builder(InputParamsTM.BrowserNameParam)
            .required(true)
            .hasArg()
            .possibleValues(WebDriverType.class)
            .desc("Browser to launch the Suite of Tests. Possible values: " + Arrays.asList(WebDriverType.values()))
            .build();

        OptionTMaker channel = OptionTMaker.builder(InputParamsTM.ChannelNameParam)
            .required(true)
            .hasArg()
            .possibleValues(Channel.class)
            .desc("Channel on which to run the browser. Possible values: " + Arrays.toString(Channel.values()))
            .build();
           
        OptionTMaker application = OptionTMaker.builder(InputParamsTM.AppNameParam)
            .required(true)
            .hasArg()
            .possibleValues(appEnum)
            .desc("Application Web to test. Possible values: " + Arrays.toString(getNames(appEnum.getEnumConstants())))
            .build();

        String patternUrl = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        OptionTMaker url = OptionTMaker.builder(InputParamsTM.URLNameParam)
            .required(true)
            .hasArg()
            .pattern(patternUrl)
            .desc("Initial URL of the application Web to test")
            .build();
                    
        OptionTMaker tests = OptionTMaker.builder(InputParamsTM.TCaseNameParam)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("List of testcases comma separated (p.e. OTR001,BOR001...)")
            .build();    
        
        OptionTMaker version = OptionTMaker.builder(InputParamsTM.VersionNameParam)
            .required(false)
            .hasArg()
            .desc("Version of the TestSuite")
            .build();
        
        OptionTMaker serverDNS = OptionTMaker.builder(InputParamsTM.ServerDNSNameParam)
            .required(false)
            .hasArgs()
            .desc("Server DNS where are ubicated the HTML reports (p.e. http://robottest.mangodev.net)")
            .build();
        
        OptionTMaker recicleWD = OptionTMaker.builder(InputParamsTM.RecicleWDParam)
            .required(false)
            .hasArgs()
            .possibleValues(Arrays.asList("true", "false"))
            .desc("Gestion mode of webdriver. Possible values: true->reuse across testcases, false->don't reuse)")
            .build();
        
        OptionTMaker netAnalysis = OptionTMaker.builder(InputParamsTM.NetAnalysisParam)
            .required(false)
            .hasArgs()
            .possibleValues(Arrays.asList("true", "false"))
            .desc("Net Analysis (true, false)")
            .build();
        
        OptionTMaker store = OptionTMaker.builder(InputParamsTM.StoreParam)
            .required(false)
            .hasArgs()
            .possibleValues(Arrays.asList("true", "false"))
            .desc("Store result persistentely (true, false)")
            .build();
        
        OptionTMaker mails = OptionTMaker.builder(InputParamsTM.MailsParam)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .pattern("^(.+)@(.+)$")
            .desc("List of mail adresses comma separated")
            .build();
        
    	OptionTMaker typeAccess = OptionTMaker.builder(InputParamsTM.TypeAccessParam)
            .required(false)
            .hasArgs()
            .possibleValues(TypeAccess.class)
            .desc("Type of access. Posible values: " + Arrays.asList(TypeAccess.values()))
            .build();  
                
        listOptionsTMaker.add(suite);
        listOptionsTMaker.add(browser);
        listOptionsTMaker.add(channel);
        listOptionsTMaker.add(application);
        listOptionsTMaker.add(version);
        listOptionsTMaker.add(url);
        listOptionsTMaker.add(groups);     
        listOptionsTMaker.add(tests);
        listOptionsTMaker.add(serverDNS);
        listOptionsTMaker.add(recicleWD);
        listOptionsTMaker.add(netAnalysis);
        listOptionsTMaker.add(store);
        listOptionsTMaker.add(mails);
        listOptionsTMaker.add(typeAccess);
        
        return listOptionsTMaker;
	}
	
	
	private void setTmDataFromCommandLine(CommandLine cmdLine) {
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

	private enum ParamTM {
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
	
	private Map<String,String> getTmParamsValues() {
		Map<String,String> pairsParamValue = new HashMap<>(); 
		for (ParamTM paramTM : ParamTM.values()) {
			String valueParam = getValueParam(paramTM);
			if (valueParam!=null && "".compareTo(valueParam)!=0) {
				pairsParamValue.put(paramTM.nameParam, valueParam);
			}
		}
		return pairsParamValue;
	}
	
	private String getValueParam(ParamTM paramId) {
		switch (paramId) {
		case Suite:
			return this.suite;
		case Groups:
			return this.groupsCommaSeparated;
		case Browser:
			return this.browser;
		case Channel:
			return this.channel;
		case Application:
			return this.application;
		case Version:
			return this.version;
		case Url:
			return this.url;
		case Tcases:
			return this.tcasesCommaSeparated;
		case ServerDNS:
			return this.serverDNS;
		case RecicleWD:
			return this.reciclewd;
		case NetAnalysis:
			return this.net;
		case Store:
			return this.store;
		case Mails:
			return this.mailsCommaSeparated;
		case TypeAccess:
			return this.typeAccess;
		}
		return "";
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


	
    private static String[] getNames(Enum<?>[] enumConstants) {
        return Arrays.stream(enumConstants).map(Enum::name).toArray(String[]::new);
    }

}

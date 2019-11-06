package com.mng.testmaker.boundary.access;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.cli.CommandLine; 
import org.apache.commons.cli.CommandLineParser; 
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.domain.InputParamsTM;
import com.mng.testmaker.service.webdriver.maker.FactoryWebdriverMaker.WebDriverType;

public class CmdLineMaker { 

	private final String[] args;
	private final List<OptionTMaker> listOptions;
	private final Class<? extends Enum<?>> suiteEnum;
	private final Class<? extends Enum<?>> appEnum;
	
	private final CommandLineParser parser = new DefaultParser();
	private final CommandLine cmdLine;
	
    public static String HelpNameParam = "help";
    public static String SuiteNameParam = "suite";
    public static String GroupsNameParam = "groups";
    public static String BrowserNameParam = "browser";
    public static String ChannelNameParam = "channel";
    public static String AppNameParam = "application";
    public static String VersionNameParam = "version";
    public static String URLNameParam = "url";
    public static String TCaseNameParam = "tcases";
    
    public static String ServerDNSNameParam = "serverDNS";
    public static String RecicleWD = "reciclewd";
    public static String NetAnalysis = "net";
    public static String Store = "store";
    public static String Mails = "mails";
	
	private CmdLineMaker(
			String args[], 
			List<OptionTMaker> clientOptions, 
			Class<? extends Enum<?>> suiteEnum, 
			Class<? extends Enum<?>> appEnum) throws ParseException {
		this.args = args;
		this.suiteEnum = suiteEnum;
		this.appEnum = appEnum;
		this.listOptions = getListAllOptions(clientOptions);
		this.cmdLine = getParsedOptions();
	}
	
	public static CmdLineMaker from(			
		String args[], 
		List<OptionTMaker> clientOptions, 
		Class<? extends Enum<?>> suiteEnum, 
		Class<? extends Enum<?>> appEnum) throws ParseException {
		return new CmdLineMaker(args, clientOptions, suiteEnum, appEnum);
	}
	
	public static CmdLineMaker from(
		String args[], 
		Class<? extends Enum<?>> suiteEnum, 
		Class<? extends Enum<?>> appEnum) throws ParseException {
		return new CmdLineMaker(args, null, suiteEnum, appEnum);
	}	
	
	public CommandLine getParsedOptions() throws ParseException {
		CommandLine cmdLineToReturn;
		CommandLine cmdLineHelp = checkHelpParameterCase(args);
		if (cmdLineHelp==null) {
			Options options = getOptions();
			try {
				cmdLineToReturn = parser.parse(options, args);
			}
			catch (ParseException e) {
	            System.out.println(e.getLocalizedMessage());
	            printHelpSyntaxis(options);
	            throw e;
			}
		} else {
			return cmdLineHelp;
		}
		return cmdLineToReturn;
	}
	
    public boolean checkOptionsValue() {
        boolean check=true;
        StringBuffer storedErrors = new StringBuffer();
        if (!checkOptionsValue(storedErrors)) {
        	check=false;
        	System.out.println(storedErrors);
        }
        return (check);
    }
	
	public CommandLine getComandLineData() {
		return this.cmdLine;
	}
	
	public InputParamsTM getInputParamsTM() {
		InputParamsTM inputParams = new InputParamsTM();
		storeDataOptionsTM(inputParams);
		return inputParams;
	}
	
    public void storeDataOptionsTM(InputParamsTM inputParams) {
    	String version = cmdLine.getOptionValue(VersionNameParam);
    	if (version==null) {
    		version = "V1";
    	}
        		
    	inputParams.setChannel(Channel.valueOf(cmdLine.getOptionValue(ChannelNameParam)));
    	inputParams.setSuite(valueOf(suiteEnum.getEnumConstants(), cmdLine.getOptionValue(SuiteNameParam)));
    	inputParams.setApp(valueOf(appEnum.getEnumConstants(), cmdLine.getOptionValue(AppNameParam)));
    	inputParams.setVersionSuite(version);
    	inputParams.setUrlBase(cmdLine.getOptionValue(URLNameParam));
    	inputParams.setWebDriverType(WebDriverType.valueOf(cmdLine.getOptionValue(BrowserNameParam)));
    	inputParams.setWebAppDNS(cmdLine.getOptionValue(ServerDNSNameParam));

    	String[] listGroups = cmdLine.getOptionValues(GroupsNameParam);
    	if (listGroups!=null) {
    		inputParams.setGroupsFilter(Arrays.asList());
    	}
    	String[] listTestCases = cmdLine.getOptionValues(TCaseNameParam);
    	if (listTestCases!=null) {
    		inputParams.setTestCasesFilter(Arrays.asList(listTestCases));
    	}
    	String[] listMails = cmdLine.getOptionValues(Mails);
    	if (listMails!=null) {
    		inputParams.setMails(Arrays.asList());
    	}
    	
    	String recicleWD = cmdLine.getOptionValue(RecicleWD);
    	if (recicleWD!=null) {
        	inputParams.setRecicleWD(recicleWD);
    	}
    	String netAnalysis = cmdLine.getOptionValue(NetAnalysis);
    	if (netAnalysis!=null && "true".compareTo(netAnalysis)==0) {
    		inputParams.setNetAnalysis(true);
    	} 
    	
    	String store = cmdLine.getOptionValue(Store);
    	if (store!=null && "true".compareTo(store)==0) {
    		inputParams.setStoreResult(true);
    	} 
    }
	
	private Options getOptions() {
		Options optionsReturn = new Options();
		for (OptionTMaker optionTMaker : listOptions) {
			optionsReturn.addOption(optionTMaker.getOption());
		}
		return optionsReturn;
	}
	
	private List<OptionTMaker> getListAllOptions(List<OptionTMaker> clientOptions) {
		List<OptionTMaker> listAllOptions = new ArrayList<>();
		listAllOptions.addAll(getTestMakerOptions());
		if (clientOptions!=null) {
			listAllOptions.addAll(clientOptions);
		}
		return listAllOptions; 
	}
	
	private List<OptionTMaker> getTestMakerOptions() {
		List<OptionTMaker> listOptionsTMaker = new ArrayList<>();
        OptionTMaker suite = OptionTMaker.builder(SuiteNameParam)
            .required(true)
            .hasArg()
            .possibleValues(suiteEnum)
            .desc("Test Suite to execute. Possible values: " + Arrays.asList(suiteEnum.getEnumConstants()))
            .build();
        
        OptionTMaker groups = OptionTMaker.builder(GroupsNameParam)
            .required(false)
            .hasArg()
            .valueSeparator(',')
            .desc("Groups of tests to include")
            .build();
                 
        OptionTMaker browser = OptionTMaker.builder(BrowserNameParam)
            .required(true)
            .hasArg()
            .possibleValues(WebDriverType.class)
            .desc("Browser to launch the Suite of Tests. Possible values: " + Arrays.asList(WebDriverType.values()))
            .build();

        OptionTMaker channel = OptionTMaker.builder(ChannelNameParam)
            .required(true)
            .hasArg()
            .possibleValues(Channel.class)
            .desc("Channel on which to run the browser. Possible values: " + Arrays.toString(Channel.values()))
            .build();
           
        OptionTMaker application = OptionTMaker.builder(AppNameParam)
            .required(true)
            .hasArg()
            .possibleValues(appEnum)
            .desc("Application Web to test. Possible values: " + Arrays.toString(getNames(appEnum.getEnumConstants())))
            .build();

        String patternUrl = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        OptionTMaker url = OptionTMaker.builder(URLNameParam)
            .required(true)
            .hasArg()
            .pattern(patternUrl)
            .desc("Initial URL of the application Web to test")
            .build();
                    
        OptionTMaker tests = OptionTMaker.builder(TCaseNameParam)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("List of testcases comma separated (p.e. OTR001,BOR001...)")
            .build();    
        
        OptionTMaker version = OptionTMaker.builder(VersionNameParam)
            .required(false)
            .hasArg()
            .desc("Version of the TestSuite")
            .build();
        
        OptionTMaker serverDNS = OptionTMaker.builder(ServerDNSNameParam)
            .required(false)
            .hasArgs()
            .desc("Server DNS where are ubicated the HTML reports (p.e. http://robottest.mangodev.net)")
            .build();
        
        OptionTMaker recicleWD = OptionTMaker.builder(RecicleWD)
            .required(false)
            .hasArgs()
            .possibleValues(Arrays.asList("true", "false"))
            .desc("Gestion mode of webdriver. Possible values: true->reuse across testcases, false->don't reuse)")
            .build();
        
        OptionTMaker netAnalysis = OptionTMaker.builder(NetAnalysis)
            .required(false)
            .hasArgs()
            .possibleValues(Arrays.asList("true", "false"))
            .desc("Net Analysis (true, false)")
            .build();
        
        OptionTMaker store = OptionTMaker.builder(Store)
            .required(false)
            .hasArgs()
            .possibleValues(Arrays.asList("true", "false"))
            .desc("Store result persistentely (true, false)")
            .build();
        
        OptionTMaker mails = OptionTMaker.builder(Mails)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .pattern("^(.+)@(.+)$")
            .desc("List of mail adresses comma separated")
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
        
        return listOptionsTMaker;
	}
	
    /**
     * Parseo para contemplar el caso concreto del parámetro Help
     */
    private CommandLine checkHelpParameterCase(String[] args) {
        Options options = new Options();
        Option helpOption = Option.builder(HelpNameParam)
            .required(false)
            .desc("shows this message")
            .build();
        
        try {
            options.addOption(helpOption);
            CommandLine cmdLineHelp = parser.parse(options, args);
            if (cmdLineHelp.hasOption(HelpNameParam)) {
                printHelpSyntaxis(options);
                return cmdLineHelp;
            }
        }
        catch (ParseException e) {
            //En caso de cualquier otro parámetro <> a Help saltará el ParseException
            //Es correcto, el parseo definitivo se realiza más adelante
        }
        
        return null;
    }
    
    private void printHelpSyntaxis(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("TestMaker", getOptions());
    }

    
    boolean checkOptionsValue(StringBuffer storedErrors) {
    	if (HelpNameParam.compareTo(cmdLine.getOptions()[0].getOpt())==0) {
    		return false;
    	}
    	
    	boolean check = true;
    	for (OptionTMaker optionTMaker : listOptions) {
    		String nameParam = optionTMaker.getOption().getOpt();
    		String valueOption = cmdLine.getOptionValue(nameParam);
    		if (optionTMaker.getOption().isRequired()) {
    			if (valueOption==null) {
    		    	String saltoLinea = System.getProperty("line.separator");
    				storedErrors.append("Mandatory param " + nameParam + " doesn't exists" + saltoLinea);
    				check = false;
    			}
    		}
    		if (valueOption!=null) {
	    		if (!optionTMaker.getOption().hasValueSeparator()) {
	    			if (!checkOptionValue(optionTMaker, valueOption, storedErrors)) {
	    				check = false;
	    			}
	    		} else {
	    			String[] valuesOption = cmdLine.getOptionValues(nameParam);
	    			if (!checkOptionValues(optionTMaker, valuesOption, storedErrors)) {
	    				check = false;
	    			}
	    		}
    		}
    	}
    	return check;
    }
    
    private boolean checkOptionValues(OptionTMaker optionTMaker, String[] valuesOption, StringBuffer storedErrors) {
		for (String valueOption : valuesOption) {
			if (!checkOptionValue(optionTMaker, valueOption, storedErrors)) {
				return false;
			}
		}
		return true;
    }
    
    private boolean checkOptionValue(OptionTMaker optionTMaker, String value, StringBuffer storedErrors) {
		String nameParam = optionTMaker.getOption().getOpt();
		String stringPattern = optionTMaker.getPattern();
		String saltoLinea = System.getProperty("line.separator");
		if (stringPattern!=null && !checkPatternValue(stringPattern, value)) {
			storedErrors.append("Param " + nameParam + " with value " + value + " that doesn't match pattern " + stringPattern + saltoLinea);
			return false;
		}
		List<String> possibleValues = optionTMaker.possibleValues();
		if (possibleValues!=null && !checkPossibleValues(possibleValues, value)) {
			storedErrors.append("Param " + nameParam + " with value " + value + " is not one of the possible values " + possibleValues + saltoLinea);
			return false;
		}
		return true;
    }
    
    private boolean checkPatternValue(String stringPattern, String value) {
		Pattern pattern = Pattern.compile(stringPattern);
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
    }
    
    private boolean checkPossibleValues(List<String> possibleValues, String value) {
    	for (String possibleValue : possibleValues) {
    		if (possibleValue.compareTo(value)==0) {
    			return true;
    		}
    	}
    	return false;
    }
    
    private static String[] getNames(Enum<?>[] enumConstants) {
        return Arrays.stream(enumConstants).map(Enum::name).toArray(String[]::new);
    }
    
    private static Enum<?> valueOf(Enum<?>[] enumConstants, String value) throws IllegalArgumentException {
    	for (Enum<?> enumCandidate : enumConstants) {
    		if (enumCandidate.name().equals(value)) {
    			return enumCandidate;
    		}
    	}
    	
    	throw new IllegalArgumentException();
    }
}

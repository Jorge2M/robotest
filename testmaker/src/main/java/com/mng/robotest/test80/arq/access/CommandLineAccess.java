package com.mng.robotest.test80.arq.access;

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

import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.utils.webdriver.maker.FactoryWebdriverMaker.TypeWebDriver;

public class CommandLineAccess {

	private final Class<? extends Enum<?>> suiteEnum;
	private final Class<? extends Enum<?>> appEnum;
	
	private final Options options = new Options();
	private final CommandLineParser parser = new DefaultParser();
	private final List<OptionTMaker> specificClientOptions;
	private final CommandLine cmdLine;
	
    private static String HelpNameParam = "help";
    private static String SuiteNameParam = "suite";
    private static String GroupsNameParam = "groups";
    private static String BrowserNameParam = "browser";
    private static String ChannelNameParam = "channel";
    private static String AppNameParam = "application";
    private static String VersionNameParam = "version";
    private static String URLNameParam = "url";
    private static String TCaseNameParam = "tcases";
    
    private static String ServerDNSNameParam = "serverDNS";
    private static String RecicleWD = "reciclewd";
    private static String NetAnalysis = "net";
    private static String Mails = "mails";
	
	public CommandLineAccess(
			String args[], List<OptionTMaker> specificClientOptions, Class<? extends Enum<?>> suiteEnum, Class<? extends Enum<?>> appEnum) 
	throws ParseException {
		this.specificClientOptions = specificClientOptions;
		this.suiteEnum = suiteEnum;
		this.appEnum = appEnum;
		if (!checkHelpParameterCase(args)) {
			initOptions();
			try {
				cmdLine = parser.parse(options, args);
			}
			catch (ParseException e) {
	            System.out.println(e.getLocalizedMessage());
	            printHelpSyntaxis(options);
	            throw e;
			}
		} else {
			cmdLine = null;
		}
	}
	
	public CommandLine getComandLineData() {
		return this.cmdLine;
	}
	
	private void initOptions() {
        //Required
        Option suite = Option.builder(SuiteNameParam)
            .required(true)
            .hasArg()
            .desc("TestSuite Name")
            .build();
        
        Option groups = Option.builder(GroupsNameParam)
            .required(false)
            .hasArg()
            .desc("Groups of tests to include")
            .build();
                 
        Option browser = Option.builder(BrowserNameParam)
            .required(true)
            .hasArg()
            .desc("Browser to launch the Suite of Tests")
            .build();

        Option channel = Option.builder(ChannelNameParam)
            .required(true)
            .hasArg()
            .desc("Channel in that browser is executed")
            .build();
                
        Option application = Option.builder(AppNameParam)
            .required(true)
            .hasArg()
            .desc("Application Web to test")
            .build();

        Option url = Option.builder(URLNameParam)
            .required(true)
            .hasArg()
            .desc("URL initial of the application Web to test")
            .build();
                
        //Optional      
        Option tests = Option.builder(TCaseNameParam)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("List of testcases comma separated (p.e. OTR001,BOR001...)")
            .build();    
        
        Option version = Option.builder(VersionNameParam)
            .required(false)
            .hasArg()
            .desc("Version of te TestSuite")
            .build();
        
        Option serverDNS = Option.builder(ServerDNSNameParam)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("Server DNS where are ubicated the HTML reports (p.e. http://robottest.mangodev.net)")
            .build();
        
        Option recicleWD = Option.builder(RecicleWD)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("Gestion mode of webdriver (true: reuse across testcases, false: don't reuse)")
            .build();        
        
        Option netAnalysis = Option.builder(NetAnalysis)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("Net Analysis (true, false)")
            .build();        
        
        Option mails = Option.builder(Mails)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("List of mail adresses comma separated")
            .build();                       
                
        //Required
        options.addOption(suite);
        options.addOption(browser);
        options.addOption(channel);
        options.addOption(application);
        options.addOption(version);
        options.addOption(url);
        
        //Optional
        options.addOption(groups);     
        options.addOption(tests);
        options.addOption(serverDNS);
        options.addOption(recicleWD);
        options.addOption(netAnalysis);
        options.addOption(mails);
        
		if (specificClientOptions!=null) {
			for (OptionTMaker optionTMaker : specificClientOptions) {
				options.addOption(optionTMaker.getOption());
			}
		}
	}
	
    /**
     * Parseo para contemplar el caso concreto del parámetro Help
     */
    private boolean checkHelpParameterCase(String[] args) {
        Options options = new Options();
        Option helpOption = Option.builder(HelpNameParam)
            .required(false)
            .desc("shows this message")
            .build();
        
        try {
            options.addOption(helpOption);
            CommandLine cmdLineHelp = parser.parse(options, args);
            if (cmdLineHelp.hasOption(HelpNameParam)) {
                //Necesitamos informar todos los parámetros para que el mensaje con la sintaxis que se muestre sea el correto
                initOptions();
                printHelpSyntaxis(options);
                return true;
            }
        }
        catch (ParseException e) {
            //En caso de cualquier otro parámetro <> a Help saltará el ParseException
            //Es correcto, el parseo definitivo se realiza más adelante
        }
        
        return false;
    }
    
    private void printHelpSyntaxis(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(CommandLineAccess.class.getSimpleName(), options);
    }
    
    public boolean checkOptionValues() {
        boolean check=true;
        
        //Mandatory TMaker Params
        try {
        	valueOf(suiteEnum.getEnumConstants(), cmdLine.getOptionValue(SuiteNameParam));
        }
        catch (IllegalArgumentException e) {
            check=false;
            System.out.println("Suite Name not valid. Posible values: " + Arrays.toString(getNames(suiteEnum.getEnumConstants())));
        } 
        
        try {
        	Channel.valueOf(cmdLine.getOptionValue(ChannelNameParam));
        }
        catch (IllegalArgumentException e) {
            check=false;
            System.out.println("Channel not valid. Posible values: " + Arrays.toString(Channel.values()));
        }
        
        try {
        	valueOf(appEnum.getEnumConstants(), cmdLine.getOptionValue(AppNameParam));
        }
        catch (IllegalArgumentException e) {
            check=false;
            System.out.println("App not valid. Posible values: " + Arrays.toString(getNames(appEnum.getEnumConstants())));
        }

        try {
            TypeWebDriver.valueOf(cmdLine.getOptionValue(BrowserNameParam));
        }
        catch (IllegalArgumentException e) {
            check=false;
            System.out.println("Browser Name not valid. Posible values: " + Arrays.toString(getNames(TypeWebDriver.class.getEnumConstants())));
        }
        
        //Not Mandatory TMaker Params
        if (cmdLine.getOptionValue(RecicleWD)!=null) {
            String recicleWDvalue = cmdLine.getOptionValue(RecicleWD);
            if (!recicleWDvalue.contains("true") && !recicleWDvalue.contains("false")) {
                check=false;
                System.out.println("param " + RecicleWD + " not valid. Posible values: true, false");
            }        
        }
        
        if (cmdLine.getOptionValue(NetAnalysis)!=null) {
            String netAnalysisValue = cmdLine.getOptionValue(NetAnalysis);
            if (!netAnalysisValue.contains("true") && !netAnalysisValue.contains("false")) {
                check=false;
                System.out.println("param " + NetAnalysis + " not valid. Posible values: true, false");
            }        
        }        
        
        //Specific client Params
        StringBuffer storedErrors = new StringBuffer();
        if (!checkSpecificClientValues(storedErrors)) {
        	check=false;
        	System.out.println(storedErrors);
        }
        
        return (check);
    }
    
    boolean checkSpecificClientValues(StringBuffer storedErrors) {
    	boolean check = true;
    	for (OptionTMaker optionTMaker : specificClientOptions) {
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
    
    public void storeDataOptionsTestMaker(InputParamsTestMaker inputParams) {
    	String version = cmdLine.getOptionValue(VersionNameParam);
    	if (version==null) {
    		version = "V1";
    	}
        		
    	inputParams.setChannel(Channel.valueOf(cmdLine.getOptionValue(ChannelNameParam)));
    	inputParams.setSuite(valueOf(suiteEnum.getEnumConstants(), cmdLine.getOptionValue(SuiteNameParam)));
    	inputParams.setApp(valueOf(appEnum.getEnumConstants(), cmdLine.getOptionValue(AppNameParam)));
    	inputParams.setVersionSuite(version);
    	inputParams.setUrlBase(cmdLine.getOptionValue(URLNameParam));
    	inputParams.setTypeWebDriver(TypeWebDriver.valueOf(cmdLine.getOptionValue(BrowserNameParam)));
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
    	if (netAnalysis!=null) {
    		inputParams.setNetAnalysis(netAnalysis);
    	} 
    }
}

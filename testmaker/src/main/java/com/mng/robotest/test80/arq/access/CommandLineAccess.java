package com.mng.robotest.test80.arq.access;

import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.mng.robotest.test80.arq.listeners.CallBack;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.utils.otras.TypeAccessFmwk;
import com.mng.robotest.test80.arq.utils.webdriver.maker.FactoryWebdriverMaker.TypeWebDriver;
import com.mng.robotest.test80.arq.xmlprogram.ParamsBean;

public class CommandLineAccess { -> CheckerCommandLineAccess

	private final Options options = new Options();
	private final CommandLineParser parser = new DefaultParser();
	private final OptionGroup moreOptions;
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
	
	private CommandLineAccess(String args[], OptionGroup moreOptions) throws ParseException {
		this.moreOptions = moreOptions;
		if (!checkHelpParameterCase(args)) {
			initOptions();
	        cmdLine = parser.parse(options, args);
		} else {
			cmdLine = null;
		}
	}
	
	public static CommandLineAccess getNew(String args[], OptionGroup moreOptions) throws ParseException {
		return new CommandLineAccess(args, moreOptions);
	}
	
	public static CommandLineAccess getNew(String[] args) {
		return getNew(null);
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
        
		if (moreOptions!=null) {
			options.addOptionGroup(moreOptions);
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
    
    ...se ha de ejecutar el checkparams desde el constructor pero tengo que ver qué hago en caso de KO -> 
    creo que lo mejor es establecer una variable result a false.
    private ParamsBean checkParams(String[] args) {
        ParamsBean params = null;
        if (checkParamValues(cmdLine)) {
            params = storeParamsFromCommandLine(cmdLine);
        }
        return params;
    }
    
    private static boolean checkParamValues(CommandLine cmdLine) {
        boolean check=true;
        
        //Mandatory Params
        try {
            Suites.valueOf(cmdLine.getOptionValue(SuiteNameParam));
        }
        catch (IllegalArgumentException e) {
            check=false;
            System.out.println("Suite Name not valid. Posible values: " + Arrays.toString(getNames(Suites.class)));
        } 
        
        try {
            AppEcom.valueOf(cmdLine.getOptionValue(AppNameParam));
        }
        catch (IllegalArgumentException e) {
            check=false;
            System.out.println("App Name not valid. Posible values: " + Arrays.toString(getNames(AppEcom.class)));
        }
        
        try {
            Channel.valueOf(cmdLine.getOptionValue(ChannelNameParam));
        }
        catch (IllegalArgumentException e) {
            check=false;
            System.out.println("Channel Name not valid. Posible values: " + Arrays.toString(getNames(Channel.class)));
        }
        
        try {
            TypeWebDriver.valueOf(cmdLine.getOptionValue(BrowserNameParam));
        }
        catch (IllegalArgumentException e) {
            check=false;
            System.out.println("Browser Name not valid. Posible values: " + Arrays.toString(getNames(TypeWebDriver.class)));
        }
        
        //Not Mandatory Params
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
        
        if (cmdLine.getOptionValue(CallBackSchema)!=null) {
            try {
                TypeCallbackSchema.valueOf(cmdLine.getOptionValue(CallBackSchema));
            }
            catch (IllegalArgumentException e) {
                check=false;
                System.out.println("CallBack Schema not valid. Posible values: " + Arrays.toString(getNames(TypeCallbackSchema.class)));
            }
            
            try {
                TypeCallBackMethod.valueOf(cmdLine.getOptionValue(CallBackMethod));
            }
            catch (IllegalArgumentException e) {
                check=false;
                System.out.println("CallBack Schema not valid. Posible values: " + Arrays.toString(getNames(TypeCallBackMethod.class)));
            }
        }
        
        return check;
    }
    
    public static ParamsBean storeParamsFromCommandLine(CommandLine cmdLine) {
    	String app = cmdLine.getOptionValue(AppNameParam);
    	String suite = cmdLine.getOptionValue(SuiteNameParam);
        ParamsBean params = new ParamsBean(AppEcom.valueOf(app), Suites.valueOf(suite));
        params.setChannel(cmdLine.getOptionValue(ChannelNameParam));
        params.setGroups(cmdLine.getOptionValues(GroupsNameParam));
        params.setBrowser(cmdLine.getOptionValue(BrowserNameParam));
        params.setVersion(cmdLine.getOptionValue(VersionNameParam));
        params.setURLBase(cmdLine.getOptionValue(URLNameParam));        
        params.setListaPaises(cmdLine.getOptionValues(CountrysNameParam));
        params.setListaLineas(cmdLine.getOptionValues(LineasNameParam));
        params.setListaPayments(cmdLine.getOptionValues(PaymentsNameParam));        
        params.setListaTestCases(cmdLine.getOptionValues(TCaseNameParam));
        params.setUrlManto(cmdLine.getOptionValue(UrlManto));
        params.setRecicleWD(cmdLine.getOptionValue(RecicleWD));
        params.setNetAnalysis(cmdLine.getOptionValue(NetAnalysis));
        params.setMails(cmdLine.getOptionValues(Mails));
        params.setApplicationDNS(cmdLine.getOptionValue(ServerDNSNameParam));
        params.setTypeAccessFromStr(cmdLine.getOptionValue(TypeAccessParam));
        if (cmdLine.getOptionValue(CallBackResource)!=null) {
            CallBack callBack = new CallBack();
            callBack.setCallBackResource(cmdLine.getOptionValue(CallBackResource));
            callBack.setCallBackMethod(cmdLine.getOptionValue(CallBackMethod));
            callBack.setCallBackUser(cmdLine.getOptionValue(CallBackUser));
            callBack.setCallBackPassword(cmdLine.getOptionValue(CallBackPassword));
            callBack.setCallBackSchema(cmdLine.getOptionValue(CallBackSchema));
            callBack.setCallBackParams(cmdLine.getOptionValue(CallBackParams));
            params.setCallBack(callBack);
        }        
        
        return params;
    }
	
}

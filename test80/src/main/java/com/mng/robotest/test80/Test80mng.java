package com.mng.robotest.test80;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.mng.robotest.test80.arq.listeners.CallBack;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.filter.TestMethod;
import com.mng.robotest.test80.arq.utils.otras.TypeAccessFmwk;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.utils.webdriver.maker.FactoryWebdriverMaker.TypeWebDriver;
import com.mng.robotest.test80.arq.xmlprogram.ParamsBean;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.conftestmaker.Suites;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.xmlprogram.*;

public class Test80mng { 

    public static String HelpNameParam = "help";
    public static String SuiteNameParam = "suite";
    public static String GroupsNameParam = "groups";
    public static String BrowserNameParam = "browser";
    public static String ChannelNameParam = "channel";
    public static String AppNameParam = "application";
    public static String VersionNameParam = "version";
    public static String URLNameParam = "url";
    public static String CountrysNameParam = "countrys";
    public static String LineasNameParam = "lineas";
    public static String PaymentsNameParam = "payments";
    public static String TCaseNameParam = "tcases";
    
    public static String ServerDNSNameParam = "serverDNS";
    public static String UrlManto = "urlmanto";
    public static String RecicleWD = "reciclewd";
    public static String NetAnalysis = "net";
    public static String Mails = "mails";
    public static String TypeAccessParam = "typeAccess";    
    public static String CallBackResource = "callbackresource";
    public static String CallBackMethod = "callbackmethod";
    public static String CallBackSchema = "callbackschema";
    public static String CallBackParams = "callbackparams";
    public static String CallBackUser = "callbackuser";
    public static String CallBackPassword = "callbackpassword";
    
    public enum TypeCallbackSchema {http, https}
    public enum TypeCallBackMethod {POST, GET}
    
    /**
     * Direct access from Command Line
     * Parseamos la línea de comandos y ejecutamos la TestSuite correspondiente mediante la XML programática
     */
    public static void main(String[] args) throws Exception {
        if (!checkHelpParameterCase(args)) {
            Options options = setOptionsRelatedCommandLine();
            ParamsBean params = null;
            try {
                params = parseCheckAndStoreParams(options, args);    
            }
            catch (ParseException e) {
                System.out.println(e.getLocalizedMessage());
                printHelpSyntaxis(options);
                return;
            }
            
            if (params!=null) {
                params.setTypeAccessIfNotSetted(TypeAccessFmwk.CommandLine);
                execSuiteByProgramaticXML(params);
            }
        }
    }
    
    private static Options setOptionsRelatedCommandLine() {
        Options options = new Options();
        
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

        Option version = Option.builder(VersionNameParam)
            .required(false)
            .hasArg()
            .desc("Version of te TestSuite")
            .build();

        Option url = Option.builder(URLNameParam)
            .required(true)
            .hasArg()
            .desc("URL initial of the application Web to test")
            .build();
                
        //Optional
        Option countrys = Option.builder(CountrysNameParam)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("List of codes of countrys comma separated")
            .build();
        
        Option lineas = Option.builder(LineasNameParam)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("List of lines comma separated (p.e. she,he,...)")
            .build();        

        Option payments = Option.builder(PaymentsNameParam)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("List of payments comma separated (p.e. VISA,TARJETA MANGO,...)")
            .build();
        
        Option tests = Option.builder(TCaseNameParam)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("List of testcases comma separated (p.e. OTR001,BOR001...)")
            .build();        
        
        Option serverDNS = Option.builder(ServerDNSNameParam)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("Server DNS where are ubicated the HTML reports (p.e. http://robottest.mangodev.net)")
            .build();
        
        Option urlManto = Option.builder(UrlManto)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("URL of the Backoffice of mangoshop (Manto application)")
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
        
        Option bat = Option.builder(TypeAccessParam)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("Type of access. Posible values: " + Arrays.toString(getNames(TypeAccessFmwk.class)))
            .build();               
        
        Option callbackResource = Option.builder(CallBackResource)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("CallBack URL (without schema and params) to invoke in the end of the TestSuite")
            .build();
        
        Option callbackMethod = Option.builder(CallBackMethod)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("Method of the CallBack URL (POST o GET)")
            .build();        
        
        Option callbackSchema = Option.builder(CallBackSchema)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("Schema of the CallBack URL (http or https)")
            .build();        
        
        Option callbackParams = Option.builder(CallBackParams)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("Params of the CallBack URL (in format param1:value1,param2:value2...)")
            .build();        
        
        Option callbackUser = Option.builder(CallBackUser)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("User credential needed to invoke the CallBack URL")
            .build();        
        
        Option callbackPassword = Option.builder(CallBackPassword)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("Password credential needed to invoke the CallBack URL")
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
        options.addOption(countrys);
        options.addOption(lineas);
        options.addOption(payments);        
        options.addOption(tests);
        options.addOption(serverDNS);
        options.addOption(urlManto);
        options.addOption(recicleWD);
        options.addOption(netAnalysis);
        options.addOption(mails);
        options.addOption(bat);
        options.addOption(callbackResource);
        options.addOption(callbackMethod);
        options.addOption(callbackSchema);
        options.addOption(callbackParams);
        options.addOption(callbackUser);
        options.addOption(callbackPassword);
        
        return options;
    }
    
    /**
     * Parseo para contemplar el caso concreto del parámetro Help
     */
    private static boolean checkHelpParameterCase(String[] args) {
        Options options = new Options();
        Option helpOption = Option.builder(HelpNameParam)
            .required(false)
            .desc("shows this message")
            .build();
        
        try {
            options.addOption(helpOption);
            CommandLineParser parser = new DefaultParser();
            CommandLine cmdLineHelp = parser.parse(options, args);
            if (cmdLineHelp.hasOption(HelpNameParam)) {
                //Necesitamos informar todos los parámetros para que el mensaje con la sintaxis que se muestre sea el correto
                options = setOptionsRelatedCommandLine();
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
    
    /**
     * Indirect access from Command Line, direct access from Online
     */
    public static void execSuiteByProgramaticXML(ParamsBean params) throws Exception {
        //idExecSuite setted in direct access from Online, not setted in indirect access from Command Line
        params.setTypeAccessIfNotSetted(TypeAccessFmwk.Online);
        try {
            switch ((Suites)params.getSuite()) {
            case SmokeTest:
                SmokeTestSuite smokeTest = new SmokeTestSuite(params);
                smokeTest.run();         
                break;
            case SmokeManto:
                SmokeMantoSuite smokeManto = new SmokeMantoSuite(params);
                smokeManto.run();        
                break;                
            case PagosPaises:
                PagosPaisesSuite pagosPaises = new PagosPaisesSuite(params);
                pagosPaises.run();            
                break;              
            case ValesPaises:
                ValesPaisesSuite valesPaises = new ValesPaisesSuite(params);
                valesPaises.run();            
                break;                
            case PaisIdiomaBanner:
                PaisIdiomaSuite paisIdiomaBanner = new PaisIdiomaSuite(params);
                paisIdiomaBanner.run();
                break;                
            case Campanas:
//                CampanasXML campanas = new CampanasXML();
//                campanas.testRunner(params);
                break;                
            case MenusPais:
//                MenusPaisXML menusPais = new MenusPaisXML();
//                menusPais.testRunner(params);            
                break;
            case MenusManto:
//                MenusMantoFactoryXML menusManto = new MenusMantoFactoryXML();
//                menusManto.testRunner(params);            
                break;                 
            case Nodos:
//                NodosFactoryXML nodosFactory = new NodosFactoryXML();
//                nodosFactory.testRunner(params);            
                break;                
            case ConsolaVotf:
//                ConsolaVotfXML consolaVotf = new ConsolaVotfXML();
//                consolaVotf.testRunner(params);
                break;                
            case ListFavoritos:
            case ListMiCuenta:
//                GenericFactoryXML genericFactory = new GenericFactoryXML();
//                genericFactory.testRunner(params);
                break;                
            case RegistrosPaises:
//                RegistrosFactoryXML listRegistros = new RegistrosFactoryXML();
//                listRegistros.testRunner(params);
                break;     
            //TODO temporal para pruebas de Loyalty
            case LoyaltyMasivos:
//            	LoyaltyMasivosXML listLoyaltyTcases = new LoyaltyMasivosXML();
//            	listLoyaltyTcases.testRunner(params);
                break;       
            case RebajasPaises:
//                RebajasFactoryXML listRebajas = new RebajasFactoryXML();
//                listRebajas.testRunner(params);
                break;                
            default:
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println("Suite Name not valid. Posible values: " + Arrays.toString(getNames(Suites.class)));
        }
    }

    
    public static String getOutputDirectory(String userDir, String suiteName, String idExecutedSuite) {
        return fmwkTest.getOutputDirectory(userDir, suiteName, idExecutedSuite);
    }
    
    public static String getPathOutputDirectoryFromUserDir(String suiteName, String idExecutedSuite) {
        return (fmwkTest.getPathOutputDirectoryFromUserDir(suiteName, idExecutedSuite));
    }
    
    private static ParamsBean parseCheckAndStoreParams(Options options, String[] args) throws ParseException {
        ParamsBean params = null;
        CommandLineParser parser = new DefaultParser();
        CommandLine cmdLine = parser.parse(options, args);
        if (checkParamValues(cmdLine)) {
            params = storeParamsFromCommandLine(cmdLine);
        }
        return params;
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
    
    private static void printHelpSyntaxis(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(Test80mng.class.getSimpleName(), options);
    }
    
    private static String[] getNames(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }

    public static List<TestMethod> getDataTestAnnotationsToExec(ParamsBean params) throws Exception {
        params.setTypeAccessIfNotSetted(TypeAccessFmwk.Online);
        Suites suiteValue = Suites.valueOf(params.getSuiteName());
        switch (suiteValue) {
        case SmokeTest:
            SmokeTestSuite smokeTest = new SmokeTestSuite(params);
            return smokeTest.getListTests();
        case SmokeManto:
//            SmokeMantoXML smokeManto = new SmokeMantoXML();
//            return smokeManto.getDataTestAnnotationsToExec(params);            
        default:
            return null;
        }
    }
    
    public static TreeSet<String> getListPagoFilterNames(Channel channel, AppEcom appE, boolean isEmpl) 
    throws Exception {
        return (UtilsMangoTest.getListPagoFilterNames(channel, appE, isEmpl));
    }
    
    public static TreeSet<String> getListPagoFilterNames(String codCountrysCommaSeparated, Channel channel, AppEcom appE, boolean isEmpl) 
    throws Exception {
        if ("".compareTo(codCountrysCommaSeparated)==0 || 
        	"*".compareTo(codCountrysCommaSeparated)==0 || 
        	"X".compareTo(codCountrysCommaSeparated)==0) {
            return (getListPagoFilterNames(channel, appE, isEmpl));
        }
            
        String[] listCountrys = codCountrysCommaSeparated.split(",");
        ArrayList<Integer> listCodCountrys = new ArrayList<>();
        for (String codCountry : listCountrys) {
            listCodCountrys.add(Integer.valueOf(codCountry));
        }
        
        return (UtilsMangoTest.getListPagoFilterNames(listCodCountrys, channel, appE, isEmpl));
    }

}

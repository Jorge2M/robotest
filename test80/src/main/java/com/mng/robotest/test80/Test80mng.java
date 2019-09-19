package com.mng.robotest.test80;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mng.robotest.test80.arq.access.CommandLineAccess;
import com.mng.robotest.test80.arq.utils.utils;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.filter.TestMethod;
import com.mng.robotest.test80.arq.utils.otras.TypeAccessFmwk;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.xmlprogram.SuiteMaker;
import com.mng.robotest.test80.arq.xmlprogram.SuiteTestMaker;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.conftestmaker.Suites;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.suites.*;

public class Test80mng { 
	
	static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);

    public static String CountrysNameParam = "countrys";
    public static String LineasNameParam = "lineas";
    public static String PaymentsNameParam = "payments";
    
    public static String UrlManto = "urlmanto";
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
    	OptionGroup optionsTest80 = specificMangoOptions();
    	CommandLineAccess cmdLineAccess = new CommandLineAccess(args, optionsTest80, Suites.class, AppEcom.class);
    	boolean optionsTestMakerOk = cmdLineAccess.checkTestMakerOptionValues();
    	boolean optionsMangoOk = checkMangoOptionsValues(cmdLineAccess.getComandLineData()); //TODO añadir Pattern en la Option para así ahorrarnos este paso
    	if (optionsTestMakerOk && optionsMangoOk) {
    		InputParams inputParams = getInputParamsData(cmdLineAccess);
            inputParams.setTypeAccessIfNotSetted(TypeAccessFmwk.CommandLine);
            execSuite(inputParams);
    	}
    }
    
    private static boolean checkMangoOptionsValues(CommandLine cmdLineData) {
    	boolean check = true;
    	String countrysValue = cmdLineData.getOptionValue(CountrysNameParam);
    	if (countrysValue!=null) {
    		Pattern pattern = Pattern.compile("[0-9]+(,[0-9]+)*");
            Matcher matcher = pattern.matcher(countrysValue);
            if (!matcher.find()) {
            	check = false;
            	System.out.println(CountrysNameParam + "not valid. Is not a list of digit codes separated by commas");
            }
    	}
    	
        if (cmdLineData.getOptionValue(CallBackSchema)!=null) {
            try {
                TypeCallbackSchema.valueOf(cmdLineData.getOptionValue(CallBackSchema));
            }
            catch (IllegalArgumentException e) {
                check=false;
                System.out.println("CallBack Schema not valid. Posible values: " + Arrays.asList(TypeCallbackSchema.values()));
            }
            
            try {
                TypeCallBackMethod.valueOf(cmdLineData.getOptionValue(CallBackMethod));
            }
            catch (IllegalArgumentException e) {
                check=false;
                System.out.println("CallBack Schema not valid. Posible values: " + Arrays.asList(TypeCallBackMethod.values()));
            }
        }
    	
    	return check;
    }
    
    private static OptionGroup specificMangoOptions() {
    	OptionGroup options = new OptionGroup();
             
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
        
        Option urlManto = Option.builder(UrlManto)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("URL of the Backoffice of mangoshop (Manto application)")
            .build();    
        
        Option bat = Option.builder(TypeAccessParam)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("Type of access. Posible values: " + Arrays.asList(TypeAccessFmwk.values()))
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
                
        //Optional
        options.addOption(countrys);
        options.addOption(lineas);
        options.addOption(payments);        
        options.addOption(urlManto);;
        options.addOption(bat);
        options.addOption(callbackResource);
        options.addOption(callbackMethod);
        options.addOption(callbackSchema);
        options.addOption(callbackParams);
        options.addOption(callbackUser);
        options.addOption(callbackPassword);
        
        return options;
    }
    
    private static InputParams getInputParamsData(CommandLineAccess cmdLineAccess) {
    	InputParams inputParams = new InputParams();
    	CommandLine cmdLineData = cmdLineAccess.getComandLineData();
		cmdLineAccess.storeDataOptionsTestMaker(inputParams);
		
		inputParams.setListaPaises(cmdLineData.getOptionValues(CountrysNameParam));
		inputParams.setListaLineas(cmdLineData.getOptionValues(LineasNameParam));
		inputParams.setListaPayments(cmdLineData.getOptionValues(PaymentsNameParam));        
		inputParams.setUrlManto(cmdLineData.getOptionValue(UrlManto));
		inputParams.setTypeAccessFromStr(cmdLineData.getOptionValue(TypeAccessParam));
        if (cmdLineData.getOptionValue(CallBackResource)!=null) {
            CallBack callBack = new CallBack();
            callBack.setCallBackResource(cmdLineData.getOptionValue(CallBackResource));
            callBack.setCallBackMethod(cmdLineData.getOptionValue(CallBackMethod));
            callBack.setCallBackUser(cmdLineData.getOptionValue(CallBackUser));
            callBack.setCallBackPassword(cmdLineData.getOptionValue(CallBackPassword));
            callBack.setCallBackSchema(cmdLineData.getOptionValue(CallBackSchema));
            callBack.setCallBackParams(cmdLineData.getOptionValue(CallBackParams));
            inputParams.setCallBack(callBack);
        }    
        
        return inputParams;
    }
    
    public static SuiteMaker makeSuite(InputParams inputParams) throws Exception {
        inputParams.setTypeAccessIfNotSetted(TypeAccessFmwk.Online);
        try {
            switch ((Suites)inputParams.getSuite()) {
            case SmokeTest:
                return (new SmokeTestSuite(inputParams));
            case SmokeManto:
                return (new SmokeMantoSuite(inputParams));
            case PagosPaises:
                return (new PagosPaisesSuite(inputParams));           
            case ValesPaises:
                return (new ValesPaisesSuite(inputParams));          
            case PaisIdiomaBanner:
                return (new PaisIdiomaSuite(inputParams));                    
            case MenusPais:
                return (new MenusPaisSuite(inputParams));
            case MenusManto:
                return (new MenusMantoSuite(inputParams));            
            case Nodos:
                return (new NodosSuite(inputParams));           
            case ConsolaVotf:
                return (new ConsolaVotfSuite(inputParams));              
            case ListFavoritos:
            case ListMiCuenta:
                return (new GenericFactorySuite(inputParams));               
            case RegistrosPaises:
                return (new RegistrosSuite(inputParams));       
            case RebajasPaises:
                return (new RebajasSuite(inputParams));             
            default:
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println("Suite Name not valid. Posible values: " + Arrays.asList(Suites.values()));
        }
        
        return null;
    }
    
    /**
     * Indirect access from Command Line, direct access from Online
     */
    public static void execSuite(InputParams inputParams) throws Exception {
    	SuiteMaker suite = makeSuite(inputParams);
    	suite.run();
    	callBackIfNeeded(suite.getSuiteTestMaker(), inputParams);
    }
    
    private static void callBackIfNeeded(SuiteTestMaker suite, InputParams inputParams) {
    	CallBack callBack = inputParams.getCallBack();
        if (callBack!=null) {
            String pathFileReport = fmwkTest.getPathReportHTML(fmwkTest.getOutputDirectorySuite(suite));
            String reportTSuiteURL = utils.obtainDNSFromFile(pathFileReport, inputParams.getWebAppDNS()).replace("\\", "/");
            callBack.setReportTSuiteURL(reportTSuiteURL);
            try {
            	HttpURLConnection urlConnection = callBack.callURL();
            	pLogger.error("Called CallbackURL" + urlConnection);
            }
            catch (Exception e) {
                pLogger.error("Problem procesing CallBack", e);
            }
        }
    }

    
    public static List<TestMethod> getDataTestAnnotationsToExec(InputParams inputParams) throws Exception {
        inputParams.setTypeAccessIfNotSetted(TypeAccessFmwk.Online);
        Suites suiteValue = (Suites)inputParams.getSuite();
        switch (suiteValue) {
        case SmokeTest:
            SmokeTestSuite smokeTest = new SmokeTestSuite(inputParams);
            return smokeTest.getListTests();
        case SmokeManto:
            SmokeMantoSuite smokeManto = new SmokeMantoSuite(inputParams);
            return smokeManto.getListTests();            
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

    public static String getOutputDirectory(String userDir, String suiteName, String idExecutedSuite) {
        return fmwkTest.getOutputDirectory(userDir, suiteName, idExecutedSuite);
    }
    
    public static String getPathOutputDirectoryFromUserDir(String suiteName, String idExecutedSuite) {
        return (fmwkTest.getPathOutputDirectoryFromUserDir(suiteName, idExecutedSuite));
    }
}

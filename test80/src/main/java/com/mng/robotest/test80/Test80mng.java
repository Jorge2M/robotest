package com.mng.robotest.test80;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import org.apache.commons.cli.CommandLine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mng.testmaker.boundary.access.CommandLineAccess;
import com.mng.testmaker.boundary.access.OptionTMaker;
import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.conf.Log4jConfig;
import com.mng.testmaker.conf.TypeAccessFmwk;
import com.mng.testmaker.domain.SuiteTestMaker;
import com.mng.testmaker.domain.testfilter.TestMethod;
import com.mng.testmaker.service.TestMaker;
import com.mng.testmaker.utils.utils;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.conftestmaker.Suites;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.suites.*;

public class Test80mng { 
	
	static Logger pLogger = LogManager.getLogger(Log4jConfig.log4jLogger);

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
    
    public static void main(String[] args) throws Exception { 
    	List<OptionTMaker> optionsTest80 = specificMangoOptions();
    	CommandLineAccess cmdLineAccess = CommandLineAccess.from(args, optionsTest80, Suites.class, AppEcom.class);
    	if (cmdLineAccess.checkOptionsValue()) {
        	InputParams inputParams = getInputParamsMango(cmdLineAccess);
    		cmdLineAccess.storeDataOptionsTestMaker(inputParams);
            execSuite(inputParams);
    	}
    }
    
    private static List<OptionTMaker> specificMangoOptions() {
    	List<OptionTMaker> options = new ArrayList<>();
             
    	OptionTMaker countrys = OptionTMaker.builder(CountrysNameParam)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("List of 3-digit codes of countrys comma separated or \'X\' for indicate all countrys")
            .pattern("\\d{3}|X")
            .build();
        
    	OptionTMaker lineas = OptionTMaker.builder(LineasNameParam)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("List of lines comma separated (p.e. she,he,...)")
            .build();        

    	OptionTMaker payments = OptionTMaker.builder(PaymentsNameParam)
            .required(false)
            .hasArgs()
            .valueSeparator(',') 
            .desc("List of payments comma separated (p.e. VISA,TARJETA MANGO,...)")
            .build();
        
    	String patternUrl = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    	OptionTMaker urlManto = OptionTMaker.builder(UrlManto)
            .required(false)
            .hasArgs()
            .pattern(patternUrl)
            .desc("URL of the Backoffice of mangoshop (Manto application)")
            .build();    
        
    	OptionTMaker bat = OptionTMaker.builder(TypeAccessParam)
            .required(false)
            .hasArgs()
            .possibleValues(TypeAccessFmwk.class)
            .desc("Type of access. Posible values: " + Arrays.asList(TypeAccessFmwk.values()))
            .build();               
        
    	OptionTMaker callbackResource = OptionTMaker.builder(CallBackResource)
            .required(false)
            .hasArgs()
            .desc("CallBack URL (without schema and params) to invoke in the end of the TestSuite")
            .build();
        
    	OptionTMaker callbackMethod = OptionTMaker.builder(CallBackMethod)
            .required(false)
            .hasArgs()
            .possibleValues(TypeCallBackMethod.class)
            .desc("Method of the CallBack URL. Possible values: " + Arrays.asList(TypeCallBackMethod.values()))
            .build();        
        
    	OptionTMaker callbackSchema = OptionTMaker.builder(CallBackSchema)
            .required(false)
            .hasArgs()
            .possibleValues(TypeCallbackSchema.class)
            .desc("Schema of the CallBack URL. Possible values: " + Arrays.asList(TypeCallbackSchema.values()))
            .build();        
        
    	OptionTMaker callbackParams = OptionTMaker.builder(CallBackParams)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("Params of the CallBack URL (in format param1:value1,param2:value2...)")
            .build();        
        
    	OptionTMaker callbackUser = OptionTMaker.builder(CallBackUser)
            .required(false)
            .hasArgs()
            .desc("User credential needed to invoke the CallBack URL")
            .build();        

    	OptionTMaker callbackPassword = OptionTMaker.builder(CallBackPassword)
            .required(false)
            .hasArgs()
            .desc("Password credential needed to invoke the CallBack URL")
            .build();        
                
        options.add(countrys);
        options.add(lineas);
        options.add(payments);        
        options.add(urlManto);;
        options.add(bat);
        options.add(callbackResource);
        options.add(callbackMethod);
        options.add(callbackSchema);
        options.add(callbackParams);
        options.add(callbackUser);
        options.add(callbackPassword);
        
        return options;
    }
    
    private static InputParams getInputParamsMango(CommandLineAccess cmdLineAccess) {
    	InputParams inputParams = new InputParams();
    	CommandLine cmdLineData = cmdLineAccess.getComandLineData();
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
    
    /**
     * Indirect access from Command Line, direct access from Online
     */
    public static void execSuite(InputParams inputParams) throws Exception {
    	SuiteTestMaker suite = makeSuite(inputParams);
    	TestMaker.run(suite);
    	callBackIfNeeded(suite, inputParams);
    }
    
    public static SuiteTestMaker makeSuite(InputParams inputParams) throws Exception {
        inputParams.setTypeAccessIfNotSetted(TypeAccessFmwk.Online);
        try {
            switch ((Suites)inputParams.getSuite()) {
            case SmokeTest:
                return (new SmokeTestSuite(inputParams)).getSuite();
            case SmokeManto:
                return (new SmokeMantoSuite(inputParams)).getSuite();
            case PagosPaises:
                return (new PagosPaisesSuite(inputParams)).getSuite();
            case ValesPaises:
                return (new ValesPaisesSuite(inputParams)).getSuite();
            case PaisIdiomaBanner:
                return (new PaisIdiomaSuite(inputParams)).getSuite();
            case MenusPais:
                return (new MenusPaisSuite(inputParams)).getSuite();
            case MenusManto:
                return (new MenusMantoSuite(inputParams)).getSuite();
            case Nodos:
                return (new NodosSuite(inputParams)).getSuite();
            case ConsolaVotf:
                return (new ConsolaVotfSuite(inputParams)).getSuite();
            case ListFavoritos:
            case ListMiCuenta:
                return (new GenericFactorySuite(inputParams)).getSuite();
            case RegistrosPaises:
                return (new RegistrosSuite(inputParams)).getSuite();
            case RebajasPaises:
                return (new RebajasSuite(inputParams)).getSuite();
            default:
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println("Suite Name not valid. Posible values: " + Arrays.asList(Suites.values()));
        }
        
        return null;
    }

    private static void callBackIfNeeded(SuiteTestMaker suite, InputParams inputParams) {
    	CallBack callBack = inputParams.getCallBack();
        if (callBack!=null) {
            String pathFileReport = suite.getPathReportHtml();
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
}
